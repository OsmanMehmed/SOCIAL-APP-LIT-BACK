package com.socialapp.litback.dao;

import com.socialapp.litback.model.AuthRequest;
import com.socialapp.litback.model.AuthResponse;
import com.socialapp.litback.model.UserProfile;
import com.socialapp.litback.shared.Constants;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.UUID;

@Repository
public class AuthDao {
  private final JdbcTemplate jdbcTemplate;

  public AuthDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private UserProfile mapUserProfile(String id, String username, String subtitle, boolean friend, boolean banned,
      String avatarUrl, String url, boolean admin) {
    return new UserProfile(id, username, subtitle, friend, banned, avatarUrl, url, admin, false);
  }

  public AuthResponse login(AuthRequest request) {
    String sql = new StringBuilder()
        .append("SELECT id, username, subtitle, friend, banned, avatar_url, url, admin ")
        .append("FROM users WHERE username = ? AND password = ?")
        .toString();
    try {

      if (sql == null) {
        throw new IllegalArgumentException("sql is null");
      }

      UserProfile profile = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> mapUserProfile(
          rs.getString("id"),
          "@" + rs.getString("username"),
          rs.getString("subtitle"),
          rs.getBoolean("friend"),
          rs.getBoolean("banned"),
          rs.getString("avatar_url"),
          rs.getString("url"),
          rs.getBoolean("admin")), request.username(), request.password());
      profile = Objects.requireNonNull(profile, "Profile must not be null");
      String token = UUID.randomUUID().toString();
      jdbcTemplate.update(
          "INSERT INTO sessions (token, user_id, expires_at) VALUES (?, ?, {fn TIMESTAMPADD(SQL_TSI_HOUR, 24, CURRENT_TIMESTAMP)})",
          token, profile.id());
      return new AuthResponse(token, profile);
    } catch (EmptyResultDataAccessException ex) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Constants.ERR_CREDENTIALS_INVALIDAS);
    }
  }

  public AuthResponse register(AuthRequest request) {
    String userId = UUID.randomUUID().toString();
    try {
      jdbcTemplate.update(
          "INSERT INTO users (id, username, password, subtitle, url, admin) VALUES (?, ?, ?, ?, ?, ?)",
          userId, request.username(), request.password(), Constants.DEFAULT_USER_SUBTITLE, null, false);
    } catch (DuplicateKeyException e) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, Constants.ERR_USER_ALREADY_EXISTS);
    }
    UserProfile profile = new UserProfile(userId, "@" + request.username(), Constants.DEFAULT_USER_SUBTITLE, false,
        false, null, null, false, true);
    String token = UUID.randomUUID().toString();
    jdbcTemplate.update(
        "INSERT INTO sessions (token, user_id, expires_at) VALUES (?, ?, {fn TIMESTAMPADD(SQL_TSI_HOUR, 24, CURRENT_TIMESTAMP)})",
        token, userId);
    return new AuthResponse(token, profile);
  }

  public void logout(String token) {
    jdbcTemplate.update("DELETE FROM sessions WHERE token = ?", token);
  }

  public AuthResponse refresh(String token) {
    String sql = new StringBuilder()
        .append("SELECT u.id, u.username, u.subtitle, u.friend, u.banned, u.avatar_url ")
        .append(", u.url, u.admin ")
        .append("FROM sessions s JOIN users u ON u.id = s.user_id WHERE s.token = ?")
        .toString();
    try {
      if (sql == null) {
        throw new IllegalArgumentException("sql is null");
      }

      UserProfile profile = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> mapUserProfile(
          rs.getString("id"),
          "@" + rs.getString("username"),
          rs.getString("subtitle"),
          rs.getBoolean("friend"),
          rs.getBoolean("banned"),
          rs.getString("avatar_url"),
          rs.getString("url"),
          rs.getBoolean("admin")), token);
      profile = Objects.requireNonNull(profile, "Profile must not be null");
      return new AuthResponse(token, profile);
    } catch (EmptyResultDataAccessException ex) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Constants.ERR_SESION_NO_VALIDA);
    }
  }
}
