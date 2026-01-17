package com.socialapp.litback.dao;

import com.socialapp.litback.model.FriendRequest;
import com.socialapp.litback.model.Friendship;
import com.socialapp.litback.model.UserProfile;
import com.socialapp.litback.shared.Constants;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Repository
public class FriendDao {
  private final JdbcTemplate jdbcTemplate;

  public FriendDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private UserProfile mapProfile(ResultSet rs, int rowNum) throws SQLException {
    return new UserProfile(
        rs.getString("id"),
        "@" + rs.getString("username"),
        rs.getString("subtitle"),
        rs.getBoolean("friend"),
        rs.getBoolean("banned"),
        rs.getString("avatar_url"),
        rs.getString("url"),
        rs.getBoolean("admin"),
        false);
  }

  private FriendRequest mapRequest(ResultSet rs, int rowNum) throws SQLException {
    return new FriendRequest(
        rs.getString("id"),
        rs.getString("from_user_id"),
        rs.getString("to_user_id"),
        rs.getString("status"));
  }

  private Friendship mapFriendship(ResultSet rs, int rowNum) throws SQLException {
    return new Friendship(rs.getString("id"), rs.getString("user_id"), rs.getString("friend_id"));
  }

  public List<UserProfile> search(String query, boolean includeBanned) {
    String sql = "SELECT id, username, subtitle, friend, banned, avatar_url, url, admin FROM users WHERE LOWER(username) LIKE ? AND (banned = FALSE OR ? = TRUE)";
    return jdbcTemplate.query(sql, this::mapProfile, "%" + query.toLowerCase() + "%", includeBanned);
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
      createFriendship(updated.fromUserId(), updated.toUserId());
    }
    return updated;
  }

  public List<FriendRequest> listPending(String userId) {
    String sql = "SELECT id, from_user_id, to_user_id, status FROM friend_requests WHERE to_user_id = ? AND status = 'PENDING'";
    return jdbcTemplate.query(sql, this::mapRequest, userId);
  }

  public List<UserProfile> listFriends(String userId, boolean includeBanned) {
    String sql = new StringBuilder()
        .append("SELECT u.id, u.username, u.subtitle, TRUE AS friend, u.banned, u.avatar_url, u.url, u.admin ")
        .append("FROM friends f ")
        .append("JOIN users u ON (u.id = f.friend_id) ")
        .append("WHERE f.user_id = ? ")
        .append("AND (u.banned = FALSE OR ? = TRUE)")
        .toString();

    if (sql == null) {
      throw new IllegalArgumentException("sql is null");
    }

    return jdbcTemplate.query(sql, this::mapProfile, userId, includeBanned);
  }

  private Friendship findFriendship(String userId, String friendId) {
    String sql = "SELECT id, user_id, friend_id FROM friends WHERE user_id = ? AND friend_id = ?";
    List<Friendship> matches = jdbcTemplate.query(sql, this::mapFriendship, userId, friendId);
    return matches.isEmpty() ? null : matches.get(0);
  }

  public Friendship createFriendship(String userId, String friendId) {
    if (userId == null || friendId == null) {
      throw new IllegalArgumentException("User ids must be provided");
    }
    if (userId.equals(friendId)) {
      throw new IllegalArgumentException(Constants.ERR_FRIEND_SELF_RELATION);
    }
    Friendship existing = findFriendship(userId, friendId);
    if (existing != null) {
      return existing;
    }
    String id = UUID.randomUUID().toString();
    jdbcTemplate.update(
        "INSERT INTO friends (id, user_id, friend_id) VALUES (?, ?, ?)", id, userId, friendId);
    return new Friendship(id, userId, friendId);
  }

  public void deleteFriendship(String userId, String friendId) {
    jdbcTemplate.update("DELETE FROM friends WHERE user_id = ? AND friend_id = ?", userId, friendId);
  }

  public boolean existsFriendship(String userId, String friendId) {
    if (userId == null || friendId == null) {
      return false;
    }
    Integer count = jdbcTemplate.queryForObject(
        "SELECT COUNT(1) FROM friends WHERE user_id = ? AND friend_id = ?",
        Integer.class,
        userId,
        friendId);
    return count != null && count > 0;
  }

  public List<UserProfile> randomProfiles(int limit, boolean includeBanned) {
    int safeLimit = Math.max(1, Math.min(limit, 20));
    String sql = "SELECT id, username, subtitle, friend, banned, avatar_url, url, admin FROM users WHERE (banned = FALSE OR ? = TRUE)";
    List<UserProfile> entries = jdbcTemplate.query(sql, this::mapProfile, includeBanned);
    if (entries.isEmpty()) {
      return entries;
    }
    Collections.shuffle(entries);
    return entries.subList(0, Math.min(safeLimit, entries.size()));
  }

  public void deleteAllFriendships(String userId) {
    jdbcTemplate.update("DELETE FROM friends WHERE user_id = ? OR friend_id = ?", userId, userId);
  }

  public void deleteAllFriendRequests(String userId) {
    jdbcTemplate.update("DELETE FROM friend_requests WHERE from_user_id = ? OR to_user_id = ?", userId, userId);
  }
}
