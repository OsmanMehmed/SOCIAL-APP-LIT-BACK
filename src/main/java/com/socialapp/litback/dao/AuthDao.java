package com.socialapp.litback.dao;

import com.socialapp.litback.model.AuthRequest;
import com.socialapp.litback.model.AuthResponse;
import com.socialapp.litback.model.UserProfile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuthDao {
  private final JdbcTemplate jdbcTemplate;

  public AuthDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public AuthResponse login(AuthRequest request) {
    String sql = new StringBuilder()
        .append("SELECT id, username, subtitle FROM users ")
        .append("WHERE username = ? AND password = ?")
        .toString();
    // jdbcTemplate.queryForObject(sql, rowMapper, request.username(), request.password());
    UserProfile profile = new UserProfile("1", request.username(), "Perfil demo", true, false, null);
    return new AuthResponse("demo-token-123", profile);
  }

  public AuthResponse register(AuthRequest request) {
    String sql = new StringBuilder()
        .append("INSERT INTO users (username, password) VALUES (?, ?)")
        .toString();
    // jdbcTemplate.update(sql, request.username(), request.password());
    UserProfile profile = new UserProfile("2", request.username(), "Nuevo usuario", false, false, null);
    return new AuthResponse("demo-token-456", profile);
  }

  public void logout(String token) {
    String sql = "DELETE FROM sessions WHERE token = ?";
    // jdbcTemplate.update(sql, token);
  }

  public AuthResponse refresh(String token) {
    String sql = "SELECT u.id, u.username, u.subtitle FROM sessions s JOIN users u ON u.id = s.user_id WHERE s.token = ?";
    // jdbcTemplate.queryForObject(sql, mapper, token);
    UserProfile profile = new UserProfile("1", "@refreshed", "Perfil refrescado", true, false, null);
    return new AuthResponse(token, profile);
  }
}
