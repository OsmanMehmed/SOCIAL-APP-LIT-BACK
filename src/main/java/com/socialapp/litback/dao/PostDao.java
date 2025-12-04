package com.socialapp.litback.dao;

import com.socialapp.litback.model.Comment;
import com.socialapp.litback.model.Post;
import com.socialapp.litback.model.PostDetails;
import com.socialapp.litback.shared.Constants;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
    boolean liked = hasColumn(rs, "liked") && rs.getBoolean("liked");
    return new Post(
        rs.getString("id"),
        rs.getString("caption"),
        rs.getString("author_id"),
        rs.getInt("likes"),
        rs.getInt("comments"),
        rs.getInt("saves"),
        rs.getBoolean("banned"),
        liked);
  }

  private Comment mapComment(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
    return new Comment(
        rs.getString("id"),
        rs.getString("post_id"),
        rs.getString("author_id"),
        rs.getString("text"),
        rs.getTimestamp("created_at").toInstant());
  }

  private boolean hasColumn(java.sql.ResultSet rs, String column) throws SQLException {
    ResultSetMetaData meta = rs.getMetaData();
    for (int i = 1; i <= meta.getColumnCount(); i++) {
      if (column.equalsIgnoreCase(meta.getColumnLabel(i))) {
        return true;
      }
    }
    return false;
  }

  public List<Post> listAll(String userId) {
    String sql =
        "SELECT p.id, p.caption, p.author_id, p.likes, p.comments, p.saves, p.banned, "
            + "CASE WHEN pl.user_id IS NULL THEN FALSE ELSE TRUE END AS liked "
            + "FROM posts p "
            + "LEFT JOIN post_likes pl ON pl.post_id = p.id AND pl.user_id = ? "
            + "ORDER BY p.updated_at DESC";
    return jdbcTemplate.query(sql, this::mapPost, userId);
  }

  public List<Post> listByAuthor(String authorId, String userId) {
    String sql =
        "SELECT p.id, p.caption, p.author_id, p.likes, p.comments, p.saves, p.banned, "
            + "CASE WHEN pl.user_id IS NULL THEN FALSE ELSE TRUE END AS liked "
            + "FROM posts p "
            + "LEFT JOIN post_likes pl ON pl.post_id = p.id AND pl.user_id = ? "
            + "WHERE p.author_id = ? "
            + "ORDER BY p.updated_at DESC";
    return jdbcTemplate.query(sql, this::mapPost, userId, authorId);
  }

  public List<Post> searchByCaption(String query, String userId) {
    String like = "%" + query.toLowerCase() + "%";
    String sql =
        "SELECT p.id, p.caption, p.author_id, p.likes, p.comments, p.saves, p.banned, "
            + "CASE WHEN pl.user_id IS NULL THEN FALSE ELSE TRUE END AS liked "
            + "FROM posts p "
            + "LEFT JOIN post_likes pl ON pl.post_id = p.id AND pl.user_id = ? "
            + "WHERE LOWER(p.caption) LIKE ? "
            + "ORDER BY p.updated_at DESC";
    return jdbcTemplate.query(sql, this::mapPost, userId, like);
  }

  public Optional<Post> findById(String id, String userId) {
    String sql =
        "SELECT p.id, p.caption, p.author_id, p.likes, p.comments, p.saves, p.banned, "
            + "CASE WHEN pl.user_id IS NULL THEN FALSE ELSE TRUE END AS liked "
            + "FROM posts p "
            + "LEFT JOIN post_likes pl ON pl.post_id = p.id AND pl.user_id = ? "
            + "WHERE p.id = ?";
    var result = jdbcTemplate.query(sql, this::mapPost, userId, id).stream().findFirst();
    if (result.isPresent()) return result;

    String altId = id.startsWith(Constants.POST_PREFIX) ? id : Constants.POST_PREFIX + id;
    return jdbcTemplate.query(sql, this::mapPost, userId, altId).stream().findFirst();
  }

  public List<Comment> findComments(String postId) {
    String sql =
        "SELECT id, post_id, author_id, text, created_at FROM comments WHERE post_id = ? ORDER BY created_at ASC";
    var comments = jdbcTemplate.query(sql, this::mapComment, postId);
    if (!comments.isEmpty()) return comments;
    String altId = postId.startsWith(Constants.POST_PREFIX) ? postId : Constants.POST_PREFIX + postId;
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
    return new PostDetails(finalId, post.caption(), post.authorId(), 0, 0, 0, post.banned(), false, List.of(), Instant.now());
  }

  public PostDetails update(Post post) {
    jdbcTemplate.update(
        "UPDATE posts SET caption = ?, likes = ?, comments = ?, saves = ?, banned = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?",
        post.caption(), post.likes(), post.comments(), post.saves(), post.banned(), post.id());
    jdbcTemplate.update(
        "UPDATE post_details SET caption = ?, likes = ?, comments = ?, saves = ?, banned = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?",
        post.caption(), post.likes(), post.comments(), post.saves(), post.banned(), post.id());
    return new PostDetails(post.id(), post.caption(), post.authorId(), post.likes(), post.comments(), post.saves(), post.banned(), post.liked(), List.of(), Instant.now());
  }

  public void delete(String id) {
    String normalized = normalizePostId(id);
    jdbcTemplate.update("DELETE FROM post_likes WHERE post_id = ?", normalized);
    jdbcTemplate.update("DELETE FROM comments WHERE post_id = ?", normalized);
    jdbcTemplate.update("DELETE FROM posts WHERE id = ?", normalized);
  }

  private String normalizePostId(String id) {
    if (id == null || id.isBlank()) return id;
    return id.startsWith(Constants.POST_PREFIX) ? id : Constants.POST_PREFIX + id;
  }

  private String resolveAuthorId(String rawAuthorId) {
    if (rawAuthorId == null || rawAuthorId.isBlank()) {
      return Constants.DEFAULT_USER_ID;
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
    return Constants.DEFAULT_USER_ID;
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

  public Optional<Post> like(String postId, String userId, boolean like) {
    String normalized = normalizePostId(postId);
    Optional<Post> existing = findById(normalized, userId);
    if (existing.isEmpty()) {
      return Optional.empty();
    }
    boolean alreadyLiked = isLiked(normalized, userId);

    if (like && !alreadyLiked) {
      jdbcTemplate.update(
          "INSERT INTO post_likes (post_id, user_id, created_at) VALUES (?, ?, CURRENT_TIMESTAMP)",
          normalized,
          userId);
      jdbcTemplate.update(
          "UPDATE posts SET likes = likes + 1 WHERE id = ?",
          normalized);
      jdbcTemplate.update(
          "UPDATE post_details SET likes = likes + 1 WHERE id = ?",
          normalized);
    } else if (!like && alreadyLiked) {
      jdbcTemplate.update("DELETE FROM post_likes WHERE post_id = ? AND user_id = ?", normalized, userId);
      jdbcTemplate.update(
          "UPDATE posts SET likes = CASE WHEN likes > 0 THEN likes - 1 ELSE 0 END WHERE id = ?",
          normalized);
      jdbcTemplate.update(
          "UPDATE post_details SET likes = CASE WHEN likes > 0 THEN likes - 1 ELSE 0 END WHERE id = ?",
          normalized);
    }

    return findById(normalized, userId);
  }

  public Optional<Post> save(String postId, boolean save) {
    String normalized = normalizePostId(postId);
    int updated =
        jdbcTemplate.update(
            "UPDATE posts SET saves = CASE WHEN saves + ? < 0 THEN 0 ELSE saves + ? END WHERE id = ?",
            save ? 1 : -1,
            save ? 1 : -1,
            normalized);
    if (updated == 0) {
      return Optional.empty();
    }
    jdbcTemplate.update(
        "UPDATE post_details SET saves = CASE WHEN saves + ? < 0 THEN 0 ELSE saves + ? END WHERE id = ?",
        save ? 1 : -1,
        save ? 1 : -1,
        normalized);
    return findById(normalized, null);
  }

  public Optional<Post> setBanned(String postId, boolean banned) {
    String normalized = normalizePostId(postId);
    int updatedPosts = jdbcTemplate.update("UPDATE posts SET banned = ? WHERE id = ?", banned, normalized);
    int updatedDetails =
        jdbcTemplate.update("UPDATE post_details SET banned = ? WHERE id = ?", banned, normalized);
    if (updatedPosts == 0 && updatedDetails == 0) {
      return Optional.empty();
    }
    return findById(normalized, null);
  }

  private boolean isLiked(String postId, String userId) {
    if (userId == null || userId.isBlank()) {
      return false;
    }
    Integer count =
        jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM post_likes WHERE post_id = ? AND user_id = ?",
            Integer.class,
            postId,
            userId);
    return count != null && count > 0;
  }

  public List<Post> search(String query, String userId) {
    String q = "%" + query.toLowerCase() + "%";
    String sql =
        "SELECT p.id, p.caption, p.author_id, p.likes, p.comments, p.saves, p.banned, "
            + "CASE WHEN pl.user_id IS NULL THEN FALSE ELSE TRUE END AS liked "
            + "FROM posts p "
            + "LEFT JOIN post_likes pl ON pl.post_id = p.id AND pl.user_id = ? "
            + "WHERE LOWER(p.caption) LIKE ? OR LOWER(p.author_id) LIKE ? "
            + "ORDER BY p.updated_at DESC";
    return jdbcTemplate.query(sql, this::mapPost, userId, q, q);
  }
}
