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

  private UserProfile mapProfile(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
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

  public Optional<UserProfile> findById(String id) {
    final String sqlById =
        "SELECT id, username, subtitle, friend, banned, avatar_url, url, admin FROM users WHERE id = ?";
    try {
      return Optional.ofNullable(jdbcTemplate.queryForObject(sqlById, this::mapProfile, id));
    } catch (EmptyResultDataAccessException ex) {
      
      final String username = id.replace("@", "");
      final String sqlByUsername =
          "SELECT id, username, subtitle, friend, banned, avatar_url, url, admin FROM users WHERE username = ?";
      try {
        return Optional.ofNullable(
            jdbcTemplate.queryForObject(sqlByUsername, this::mapProfile, username));
      } catch (EmptyResultDataAccessException ex2) {
        return Optional.empty();
      }
    }
  }

  public UserProfile create(UserProfile profile) {
    String id = profile.id() != null ? profile.id() : UUID.randomUUID().toString();
    jdbcTemplate.update(
        "INSERT INTO users (id, username, subtitle, friend, banned, avatar_url, url, admin, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
        id,
        profile.username().replace("@", ""),
        profile.subtitle(),
        profile.friend(),
        profile.banned(),
        profile.avatarUrl(),
        profile.url(),
        profile.admin(),
        "password123");
    return new UserProfile(id, profile.username(), profile.subtitle(), profile.friend(), profile.banned(), profile.avatarUrl(), profile.url(), profile.admin(), false);
  }

  public UserProfile update(UserProfile profile) {
    jdbcTemplate.update(
        "UPDATE users SET username = ?, subtitle = ?, avatar_url = ?, url = ?, admin = ? WHERE id = ?",
        profile.username().replace("@", ""),
        profile.subtitle(),
        profile.avatarUrl(),
        profile.url(),
        profile.admin(),
        profile.id());
    return profile;
  }

  public void delete(String id) {
    jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
  }

  public boolean vetProfile(String id, boolean banned) {
    int updated = jdbcTemplate.update("UPDATE users SET banned = ? WHERE id = ?", banned, id);
    if (updated == 0 && id != null) {
      String username = id.replace("@", "");
      updated = jdbcTemplate.update("UPDATE users SET banned = ? WHERE username = ?", banned, username);
    }
    return updated > 0;
  }

  public boolean isAdmin(String id) {
    if (id == null || id.isBlank()) return false;
    final String username = id.replace("@", "");
    Integer flag = jdbcTemplate.queryForObject(
        "SELECT admin FROM users WHERE id = ?",
        Integer.class,
        id);
    if (flag != null) {
      return flag == 1;
    }
    flag = jdbcTemplate.queryForObject(
        "SELECT admin FROM users WHERE username = ?",
        Integer.class,
        username);
    return flag != null && flag == 1;
  }
}
