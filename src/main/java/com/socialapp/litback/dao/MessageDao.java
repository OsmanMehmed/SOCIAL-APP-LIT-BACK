package com.socialapp.litback.dao;

import com.socialapp.litback.model.Conversation;
import com.socialapp.litback.model.DirectMessage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public class MessageDao {
  private final JdbcTemplate jdbcTemplate;

  public MessageDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private DirectMessage mapMessage(ResultSet rs, int rowNum) throws SQLException {
    return new DirectMessage(
        rs.getString("id"),
        rs.getString("conversation_id"),
        rs.getString("from_user"),
        rs.getString("to_user"),
        rs.getString("text"),
        rs.getTimestamp("sent_at").toInstant());
  }

  private Conversation mapConversation(ResultSet rs, int rowNum) throws SQLException {
    return new Conversation(
        rs.getString("id"),
        rs.getString("participant_a"),
        rs.getString("participant_b"),
        rs.getTimestamp("updated_at").toInstant());
  }

  public List<DirectMessage> getThread(String conversationId) {
    String sql = "SELECT id, conversation_id, from_user, to_user, text, sent_at FROM messages WHERE conversation_id = ? ORDER BY sent_at ASC";
    return jdbcTemplate.query(sql, this::mapMessage, conversationId);
  }

  public DirectMessage sendMessage(String conversationId, String from, String to, String text) {
    String id = UUID.randomUUID().toString();
    Timestamp now = Timestamp.from(Instant.now());
    jdbcTemplate.update(
        "INSERT INTO messages (id, conversation_id, from_user, to_user, text, sent_at) VALUES (?, ?, ?, ?, ?, ?)",
        id, conversationId, from, to, text, now);
    jdbcTemplate.update(
        "UPDATE conversations SET updated_at = ? WHERE id = ?",
        now,
        conversationId);
    return new DirectMessage(id, conversationId, from, to, text, now.toInstant());
  }

  public void deleteMessage(String messageId) {
    jdbcTemplate.update("DELETE FROM messages WHERE id = ?", messageId);
  }

  public Conversation createConversation(String a, String b) {
    Conversation existing = findConversation(a, b);
    if (existing != null) {
      return existing;
    }
    String id = UUID.randomUUID().toString();
    Timestamp now = Timestamp.from(Instant.now());
    jdbcTemplate.update(
        "INSERT INTO conversations (id, participant_a, participant_b, updated_at) VALUES (?, ?, ?, ?)",
        id, a, b, now);
    return new Conversation(id, a, b, now.toInstant());
  }

  public List<Conversation> listConversations(String userId, boolean includeBanned) {
    String sql = "SELECT c.id, c.participant_a, c.participant_b, c.updated_at " +
        "FROM conversations c " +
        "JOIN users ua ON c.participant_a = ua.id " +
        "JOIN users ub ON c.participant_b = ub.id " +
        "WHERE (c.participant_a = ? OR c.participant_b = ?) " +
        "AND (ua.banned = FALSE OR ? = TRUE) " +
        "AND (ub.banned = FALSE OR ? = TRUE) " +
        "ORDER BY c.updated_at DESC";
    return jdbcTemplate.query(sql, this::mapConversation, userId, userId, includeBanned, includeBanned);
  }

  private Conversation findConversation(String a, String b) {
    String sql = "SELECT id, participant_a, participant_b, updated_at FROM conversations WHERE (participant_a = ? AND participant_b = ?) OR (participant_a = ? AND participant_b = ?)";
    List<Conversation> matches = jdbcTemplate.query(sql, this::mapConversation, a, b, b, a);
    return matches.isEmpty() ? null : matches.get(0);
  }

  public void deleteAllConversations(String userId) {
    List<String> conversationIds = jdbcTemplate.query(
        "SELECT id FROM conversations WHERE participant_a = ? OR participant_b = ?",
        (rs, rowNum) -> rs.getString("id"),
        userId, userId);

    for (String convId : conversationIds) {
      jdbcTemplate.update("DELETE FROM messages WHERE conversation_id = ?", convId);
      jdbcTemplate.update("DELETE FROM conversations WHERE id = ?", convId);
    }
  }
}
