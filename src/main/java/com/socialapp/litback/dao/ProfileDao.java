package com.socialapp.litback.dao;

import com.socialapp.litback.model.UserProfile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProfileDao {
  private final JdbcTemplate jdbcTemplate;

  public ProfileDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public Optional<UserProfile> findById(String id) {
    String sql = new StringBuilder()
        .append("SELECT id, username, subtitle, banned FROM profiles WHERE id = ?")
        .toString();
    // return jdbcTemplate.query(sql, rs -> { ... });
    return Optional.of(new UserProfile(id, "@" + id, "Perfil de demo", true, false, null));
  }

  public UserProfile create(UserProfile profile) {
    String sql = "INSERT INTO profiles (id, username, subtitle) VALUES (?, ?, ?)";
    // jdbcTemplate.update(sql, profile.id(), profile.username(), profile.subtitle());
    return profile;
  }

  public UserProfile update(UserProfile profile) {
    String sql = "UPDATE profiles SET subtitle = ?, banned = ? WHERE id = ?";
    // jdbcTemplate.update(sql, profile.subtitle(), profile.banned(), profile.id());
    return profile;
  }

  public void delete(String id) {
    String sql = "DELETE FROM profiles WHERE id = ?";
    // jdbcTemplate.update(sql, id);
  }

  public boolean vetProfile(String id, boolean banned) {
    String sql = new StringBuilder()
        .append("UPDATE profiles SET banned = ? WHERE id = ?")
        .toString();
    // int updated = jdbcTemplate.update(sql, banned, id);
    return true;
  }
}
