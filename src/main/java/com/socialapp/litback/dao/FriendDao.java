package com.socialapp.litback.dao;

import com.socialapp.litback.model.FriendRequest;
import com.socialapp.litback.model.UserProfile;
import com.socialapp.litback.shared.Constants;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class FriendDao {
  private final JdbcTemplate jdbcTemplate;

  public FriendDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private UserProfile mapProfile(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
    return new UserProfile(
        rs.getString("id"),
        "@" + rs.getString("username"),
        rs.getString("subtitle"),
        rs.getBoolean("friend"),
        rs.getBoolean("banned"),
        rs.getString("avatar_url"),
        false);
  }

  private FriendRequest mapRequest(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
    return new FriendRequest(
        rs.getString("id"),
        rs.getString("from_user_id"),
        rs.getString("to_user_id"),
        rs.getString("status"));
  }

  public List<UserProfile> search(String query) {
    String sql = "SELECT id, username, subtitle, friend, banned, avatar_url FROM users WHERE username LIKE ?";
    return jdbcTemplate.query(sql, this::mapProfile, "%" + query + "%");
  }

  public FriendRequest sendRequest(String from, String to) {
    String id = UUID.randomUUID().toString();
    jdbcTemplate.update(
        "INSERT INTO friend_requests (id, from_user_id, to_user_id, status) VALUES (?, ?, ?, 'PENDING')",
        id, from, to);
    return new FriendRequest(id, from, to, "PENDING");
  }

  public FriendRequest respond(String requestId, String status) {
    jdbcTemplate.update("UPDATE friend_requests SET status = ? WHERE id = ?", status, requestId);
    String sql = "SELECT id, from_user_id, to_user_id, status FROM friend_requests WHERE id = ?";
    FriendRequest updated = jdbcTemplate.queryForObject(sql, this::mapRequest, requestId);
    if (updated == null) {
      throw new IllegalStateException(String.format(Constants.ERR_FRIEND_REQUEST_NOT_FOUND, requestId));
    }
    if ("ACCEPTED".equalsIgnoreCase(status)) {
      jdbcTemplate.update(
          "INSERT INTO friends (id, user_id, friend_id) VALUES (?, ?, ?)",
          UUID.randomUUID().toString(), updated.fromUserId(), updated.toUserId());
      jdbcTemplate.update(
          "INSERT INTO friends (id, user_id, friend_id) VALUES (?, ?, ?)",
          UUID.randomUUID().toString(), updated.toUserId(), updated.fromUserId());
    }
    return updated;
  }

  public List<FriendRequest> listPending(String userId) {
    String sql = "SELECT id, from_user_id, to_user_id, status FROM friend_requests WHERE to_user_id = ? AND status = 'PENDING'";
    return jdbcTemplate.query(sql, this::mapRequest, userId);
  }

  public List<UserProfile> listFriends(String userId) {
    String sql = new StringBuilder()
        .append("SELECT u.id, u.username, u.subtitle, u.friend, u.banned, u.avatar_url ")
        .append("FROM friends f ")
        .append("JOIN users u ON (u.id = f.friend_id) ")
        .append("WHERE f.user_id = ?")
        .toString();
    return jdbcTemplate.query(sql, this::mapProfile, userId);
  }
}
