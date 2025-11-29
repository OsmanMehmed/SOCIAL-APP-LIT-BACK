package com.socialapp.litback.dao;

import com.socialapp.litback.model.Comment;
import com.socialapp.litback.model.Post;
import com.socialapp.litback.model.PostDetails;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public class PostDao {
  private final JdbcTemplate jdbcTemplate;

  public PostDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public Optional<Post> findById(String id) {
    String sql = new StringBuilder()
        .append("SELECT id, caption, author_id, likes, comments, saves FROM posts WHERE id = ?")
        .toString();
    // return jdbcTemplate.query(sql, mapper, id).stream().findFirst();
    return Optional.of(new Post(id, "Pasta fresca con salsa", "ana.cocina", 120, 18, 15));
  }

  public List<Comment> findComments(String postId) {
    String sql = new StringBuilder()
        .append("SELECT id, post_id, author_id, text, created_at FROM comments WHERE post_id = ?")
        .append(" ORDER BY created_at ASC")
        .toString();
    // return jdbcTemplate.query(sql, mapper, postId);
    return List.of(
        new Comment("c1", postId, "osman.chef", "Tip anterior sobre la receta.", Instant.now())
    );
  }

  public PostDetails create(Post post) {
    String sql = new StringBuilder()
        .append("INSERT INTO posts (caption, author_id) VALUES (?, ?)")
        .toString();
    // jdbcTemplate.update(sql, post.caption(), post.authorId());
    return new PostDetails("p-new", post.caption(), post.authorId(), 0, 0, 0, List.of(), Instant.now());
  }

  public PostDetails update(Post post) {
    String sql = new StringBuilder()
        .append("UPDATE posts SET caption = ? WHERE id = ?")
        .toString();
    // jdbcTemplate.update(sql, post.caption(), post.id());
    return new PostDetails(post.id(), post.caption(), post.authorId(), post.likes(), post.comments(), post.saves(), List.of(), Instant.now());
  }

  public void delete(String id) {
    String sql = "DELETE FROM posts WHERE id = ?";
    // jdbcTemplate.update(sql, id);
  }

  public Comment addComment(Comment comment) {
    String sql = "INSERT INTO comments (post_id, author_id, text) VALUES (?, ?, ?)";
    // jdbcTemplate.update(sql, comment.postId(), comment.authorId(), comment.text());
    return comment;
  }

  public void deleteComment(String commentId) {
    String sql = "DELETE FROM comments WHERE id = ?";
    // jdbcTemplate.update(sql, commentId);
  }

  public Post like(String postId, boolean like) {
    String sql = new StringBuilder()
        .append("UPDATE posts SET likes = likes + ")
        .append(like ? "1" : "-1")
        .append(" WHERE id = ?")
        .toString();
    // jdbcTemplate.update(sql, postId);
    return new Post(postId, "Pasta fresca con salsa", "ana.cocina", like ? 121 : 119, 18, 15);
  }

  public Post save(String postId, boolean save) {
    String sql = new StringBuilder()
        .append("UPDATE posts SET saves = saves + ")
        .append(save ? "1" : "-1")
        .append(" WHERE id = ?")
        .toString();
    // jdbcTemplate.update(sql, postId);
    return new Post(postId, "Pasta fresca con salsa", "ana.cocina", 120, 18, save ? 16 : 14);
  }
}
