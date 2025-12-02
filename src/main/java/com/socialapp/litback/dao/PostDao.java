package com.socialapp.litback.dao;

import com.socialapp.litback.model.Comment;
import com.socialapp.litback.model.Post;
import com.socialapp.litback.model.PostDetails;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PostDao {
  private final JdbcTemplate jdbcTemplate;

  public PostDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private Post mapPost(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
    return new Post(
        rs.getString("id"),
        rs.getString("caption"),
        rs.getString("author_id"),
        rs.getInt("likes"),
        rs.getInt("comments"),
        rs.getInt("saves"),
        rs.getBoolean("banned"));
  }

  private Comment mapComment(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
    return new Comment(
        rs.getString("id"),
        rs.getString("post_id"),
        rs.getString("author_id"),
        rs.getString("text"),
        rs.getTimestamp("created_at").toInstant());
  }

  public List<Post> listAll() {
    String sql =
        "SELECT id, caption, author_id, likes, comments, saves, banned FROM posts ORDER BY updated_at DESC";
    return jdbcTemplate.query(sql, this::mapPost);
  }

  public Optional<Post> findById(String id) {
    String sql = "SELECT id, caption, author_id, likes, comments, saves, banned FROM posts WHERE id = ?";
    var result = jdbcTemplate.query(sql, this::mapPost, id).stream().findFirst();
    if (result.isPresent()) return result;

    // fallback: intentar prefijo post- si el id viene como "1" en lugar de "post-1"
    String altId = id.startsWith("post-") ? id : "post-" + id;
    return jdbcTemplate.query(sql, this::mapPost, altId).stream().findFirst();
  }

  public List<Comment> findComments(String postId) {
    String sql =
        "SELECT id, post_id, author_id, text, created_at FROM comments WHERE post_id = ? ORDER BY created_at ASC";
    var comments = jdbcTemplate.query(sql, this::mapComment, postId);
    if (!comments.isEmpty()) return comments;
    String altId = postId.startsWith("post-") ? postId : "post-" + postId;
    return jdbcTemplate.query(sql, this::mapComment, altId);
  }

  public PostDetails create(Post post) {
    String id = post.id() != null ? post.id() : UUID.randomUUID().toString();
    String finalId = normalizePostId(id);
    jdbcTemplate.update(
        "INSERT INTO posts (id, caption, author_id, likes, comments, saves, banned, updated_at) VALUES (?, ?, ?, 0, 0, 0, ?, CURRENT_TIMESTAMP)",
        finalId, post.caption(), resolveAuthorId(post.authorId()), post.banned());
    jdbcTemplate.update(
        "INSERT INTO post_details (id, caption, author_id, likes, comments, saves, banned, updated_at) VALUES (?, ?, ?, 0, 0, 0, ?, CURRENT_TIMESTAMP)",
        finalId, post.caption(), resolveAuthorId(post.authorId()), post.banned());
    return new PostDetails(finalId, post.caption(), post.authorId(), 0, 0, 0, post.banned(), List.of(), Instant.now());
  }

  public PostDetails update(Post post) {
    jdbcTemplate.update(
        "UPDATE posts SET caption = ?, likes = ?, comments = ?, saves = ?, banned = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?",
        post.caption(), post.likes(), post.comments(), post.saves(), post.banned(), post.id());
    jdbcTemplate.update(
        "UPDATE post_details SET caption = ?, likes = ?, comments = ?, saves = ?, banned = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?",
        post.caption(), post.likes(), post.comments(), post.saves(), post.banned(), post.id());
    return new PostDetails(post.id(), post.caption(), post.authorId(), post.likes(), post.comments(), post.saves(), post.banned(), List.of(), Instant.now());
  }

  public void delete(String id) {
    jdbcTemplate.update("DELETE FROM posts WHERE id = ?", id);
  }

  private String normalizePostId(String id) {
    if (id == null || id.isBlank()) return id;
    return id.startsWith("post-") ? id : "post-" + id;
  }

  private String resolveAuthorId(String rawAuthorId) {
    if (rawAuthorId == null || rawAuthorId.isBlank()) {
      return "user-1";
    }
    String candidate = rawAuthorId.replace("@", "");
    try {
      Integer count =
          jdbcTemplate.queryForObject(
              "SELECT COUNT(*) FROM users WHERE id = ? OR username = ?",
              Integer.class,
              candidate,
              candidate);
      if (count != null && count > 0) {
        return candidate;
      }
    } catch (Exception ignored) {
    }
    return "user-1";
  }

  public Comment addComment(Comment comment) {
    String id = comment.id() != null ? comment.id() : UUID.randomUUID().toString();
    String normalizedPostId = normalizePostId(comment.postId());
    String authorId = resolveAuthorId(comment.authorId());
    Timestamp created = comment.createdAt() != null ? Timestamp.from(comment.createdAt()) : Timestamp.from(Instant.now());
    jdbcTemplate.update(
        "INSERT INTO comments (id, post_id, author_id, text, created_at) VALUES (?, ?, ?, ?, ?)",
        id, normalizedPostId, authorId, comment.text(), created);
    return new Comment(id, normalizedPostId, authorId, comment.text(), created.toInstant());
  }

  public void deleteComment(String commentId) {
    jdbcTemplate.update("DELETE FROM comments WHERE id = ?", commentId);
  }

  public Optional<Post> like(String postId, boolean like) {
    int updated =
        jdbcTemplate.update(
            "UPDATE posts SET likes = likes + ? WHERE id = ?",
            like ? 1 : -1,
            postId);
    if (updated == 0) {
      return Optional.empty();
    }
    return findById(postId);
  }

  public Optional<Post> save(String postId, boolean save) {
    int updated =
        jdbcTemplate.update(
            "UPDATE posts SET saves = saves + ? WHERE id = ?",
            save ? 1 : -1,
            postId);
    if (updated == 0) {
      return Optional.empty();
    }
    return findById(postId);
  }

  public Optional<Post> setBanned(String postId, boolean banned) {
    String normalized = normalizePostId(postId);
    int updatedPosts = jdbcTemplate.update("UPDATE posts SET banned = ? WHERE id = ?", banned, normalized);
    int updatedDetails =
        jdbcTemplate.update("UPDATE post_details SET banned = ? WHERE id = ?", banned, normalized);
    if (updatedPosts == 0 && updatedDetails == 0) {
      return Optional.empty();
    }
    return findById(normalized);
  }
}
