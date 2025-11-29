package com.socialapp.litback.dao;

import com.socialapp.litback.model.Conversation;
import com.socialapp.litback.model.DirectMessage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

  private DirectMessage mapMessage(java.sql.ResultSet rs) throws java.sql.SQLException {
    return new DirectMessage(
        rs.getString("id"),
        rs.getString("conversation_id"),
        rs.getString("from_user"),
        rs.getString("to_user"),
        rs.getString("text"),
        rs.getTimestamp("sent_at").toInstant());
  }

  private Conversation mapConversation(java.sql.ResultSet rs) throws java.sql.SQLException {
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
    return new DirectMessage(id, conversationId, from, to, text, now.toInstant());
  }

  public void deleteMessage(String messageId) {
    jdbcTemplate.update("DELETE FROM messages WHERE id = ?", messageId);
  }

  public Conversation createConversation(String a, String b) {
    String id = UUID.randomUUID().toString();
    jdbcTemplate.update(
        "INSERT INTO conversations (id, participant_a, participant_b, updated_at) VALUES (?, ?, ?, CURRENT_TIMESTAMP)",
        id, a, b);
    return new Conversation(id, a, b, Instant.now());
  }

  public List<Conversation> listConversations(String userId) {
    String sql = "SELECT id, participant_a, participant_b, updated_at FROM conversations WHERE participant_a = ? OR participant_b = ? ORDER BY updated_at DESC";
    return jdbcTemplate.query(sql, this::mapConversation, userId, userId);
  }
}
