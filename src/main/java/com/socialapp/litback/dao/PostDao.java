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

  private Post mapPost(java.sql.ResultSet rs) throws java.sql.SQLException {
    return new Post(
        rs.getString("id"),
        rs.getString("caption"),
        rs.getString("author_id"),
        rs.getInt("likes"),
        rs.getInt("comments"),
        rs.getInt("saves"));
  }

  private Comment mapComment(java.sql.ResultSet rs) throws java.sql.SQLException {
    return new Comment(
        rs.getString("id"),
        rs.getString("post_id"),
        rs.getString("author_id"),
        rs.getString("text"),
        rs.getTimestamp("created_at").toInstant());
  }

  public Optional<Post> findById(String id) {
    String sql = "SELECT id, caption, author_id, likes, comments, saves FROM posts WHERE id = ?";
    return jdbcTemplate.query(sql, this::mapPost, id).stream().findFirst();
  }

  public List<Comment> findComments(String postId) {
    String sql = "SELECT id, post_id, author_id, text, created_at FROM comments WHERE post_id = ? ORDER BY created_at ASC";
    return jdbcTemplate.query(sql, this::mapComment, postId);
  }

  public PostDetails create(Post post) {
    String id = post.id() != null ? post.id() : UUID.randomUUID().toString();
    jdbcTemplate.update(
        "INSERT INTO posts (id, caption, author_id, likes, comments, saves, updated_at) VALUES (?, ?, ?, 0, 0, 0, CURRENT_TIMESTAMP)",
        id, post.caption(), post.authorId());
    return new PostDetails(id, post.caption(), post.authorId(), 0, 0, 0, List.of(), Instant.now());
  }

  public PostDetails update(Post post) {
    jdbcTemplate.update(
        "UPDATE posts SET caption = ?, likes = ?, comments = ?, saves = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?",
        post.caption(), post.likes(), post.comments(), post.saves(), post.id());
    return new PostDetails(post.id(), post.caption(), post.authorId(), post.likes(), post.comments(), post.saves(), List.of(), Instant.now());
  }

  public void delete(String id) {
    jdbcTemplate.update("DELETE FROM posts WHERE id = ?", id);
  }

  public Comment addComment(Comment comment) {
    String id = comment.id() != null ? comment.id() : UUID.randomUUID().toString();
    Timestamp created = comment.createdAt() != null ? Timestamp.from(comment.createdAt()) : Timestamp.from(Instant.now());
    jdbcTemplate.update(
        "INSERT INTO comments (id, post_id, author_id, text, created_at) VALUES (?, ?, ?, ?, ?)",
        id, comment.postId(), comment.authorId(), comment.text(), created);
    return new Comment(id, comment.postId(), comment.authorId(), comment.text(), created.toInstant());
  }

  public void deleteComment(String commentId) {
    jdbcTemplate.update("DELETE FROM comments WHERE id = ?", commentId);
  }

  public Post like(String postId, boolean like) {
    jdbcTemplate.update(
        "UPDATE posts SET likes = likes + ? WHERE id = ?",
        like ? 1 : -1,
        postId);
    return findById(postId).orElse(new Post(postId, "dato-mockeado", "dato-mockeado", 0, 0, 0));
  }

  public Post save(String postId, boolean save) {
    jdbcTemplate.update(
        "UPDATE posts SET saves = saves + ? WHERE id = ?",
        save ? 1 : -1,
        postId);
    return findById(postId).orElse(new Post(postId, "dato-mockeado", "dato-mockeado", 0, 0, 0));
  }
}
