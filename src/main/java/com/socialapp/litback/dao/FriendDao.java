package com.socialapp.litback.dao;

import com.socialapp.litback.model.FriendRequest;
import com.socialapp.litback.model.UserProfile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FriendDao {
  private final JdbcTemplate jdbcTemplate;

  public FriendDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<UserProfile> search(String query) {
    String sql = new StringBuilder()
        .append("SELECT id, username, subtitle FROM profiles WHERE username ILIKE ?")
        .toString();
    // return jdbcTemplate.query(sql, mapper, "%" + query + "%");
    return List.of(new UserProfile("ana.cocina", "@ana.cocina", "Panes artesanos", true, false, null));
  }

  public FriendRequest sendRequest(String from, String to) {
    String sql = new StringBuilder()
        .append("INSERT INTO friend_requests (from_user, to_user, status) VALUES (?, ?, 'PENDING')")
        .toString();
    // jdbcTemplate.update(sql, from, to);
    return new FriendRequest("fr-1", from, to, "PENDING");
  }

  public FriendRequest respond(String requestId, String status) {
    String sql = new StringBuilder()
        .append("UPDATE friend_requests SET status = ? WHERE id = ?")
        .toString();
    // jdbcTemplate.update(sql, status, requestId);
    return new FriendRequest(requestId, "from-demo", "to-demo", status);
  }

  public List<FriendRequest> listPending(String userId) {
    String sql = new StringBuilder()
        .append("SELECT id, from_user, to_user, status FROM friend_requests WHERE to_user = ? AND status = 'PENDING'")
        .toString();
    // return jdbcTemplate.query(sql, mapper, userId);
    return List.of(new FriendRequest("fr-1", "ana.cocina", userId, "PENDING"));
  }

  public List<UserProfile> listFriends(String userId) {
    String sql = new StringBuilder()
        .append("SELECT p.id, p.username, p.subtitle, p.banned FROM profiles p ")
        .append("JOIN friendships f ON (f.user_id = p.id OR f.friend_id = p.id) ")
        .append("WHERE (f.user_id = ? OR f.friend_id = ?) AND f.status = 'ACCEPTED'")
        .toString();
    // return jdbcTemplate.query(sql, mapper, userId, userId);
    return List.of(new UserProfile("osman.chef", "@osman.chef", "Reto semanal", true, false, null));
  }
}
