package com.socialapp.litback.dao;

import com.socialapp.litback.model.UserProfile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ProfileDao {
  private final JdbcTemplate jdbcTemplate;

  public ProfileDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private UserProfile mapProfile(java.sql.ResultSet rs) throws java.sql.SQLException {
    return new UserProfile(
        rs.getString("id"),
        "@" + rs.getString("username"),
        rs.getString("subtitle"),
        rs.getBoolean("friend"),
        rs.getBoolean("banned"),
        rs.getString("avatar_url"));
  }

  public Optional<UserProfile> findById(String id) {
    String sql = "SELECT id, username, subtitle, friend, banned, avatar_url FROM users WHERE id = ?";
    try {
      return Optional.ofNullable(jdbcTemplate.queryForObject(sql, this::mapProfile, id));
    } catch (EmptyResultDataAccessException ex) {
      return Optional.empty();
    }
  }

  public UserProfile create(UserProfile profile) {
    String id = profile.id() != null ? profile.id() : UUID.randomUUID().toString();
    jdbcTemplate.update(
        "INSERT INTO users (id, username, subtitle, friend, banned, avatar_url, password) VALUES (?, ?, ?, ?, ?, ?, ?)",
        id,
        profile.username().replace("@", ""),
        profile.subtitle(),
        profile.friend(),
        profile.banned(),
        profile.avatarUrl(),
        "password123");
    return new UserProfile(id, profile.username(), profile.subtitle(), profile.friend(), profile.banned(), profile.avatarUrl());
  }

  public UserProfile update(UserProfile profile) {
    jdbcTemplate.update(
        "UPDATE users SET subtitle = ?, friend = ?, banned = ?, avatar_url = ? WHERE id = ?",
        profile.subtitle(),
        profile.friend(),
        profile.banned(),
        profile.avatarUrl(),
        profile.id());
    return profile;
  }

  public void delete(String id) {
    jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
  }

  public boolean vetProfile(String id, boolean banned) {
    int updated = jdbcTemplate.update("UPDATE users SET banned = ? WHERE id = ?", banned, id);
    return updated > 0;
  }
}
