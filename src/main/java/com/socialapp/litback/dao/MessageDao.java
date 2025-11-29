package com.socialapp.litback.dao;

import com.socialapp.litback.model.Conversation;
import com.socialapp.litback.model.DirectMessage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public class MessageDao {
  private final JdbcTemplate jdbcTemplate;

  public MessageDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<DirectMessage> getThread(String conversationId) {
    String sql = new StringBuilder()
        .append("SELECT id, conversation_id, from_user, to_user, text, sent_at FROM messages ")
        .append("WHERE conversation_id = ? ORDER BY sent_at ASC")
        .toString();
    // return jdbcTemplate.query(sql, mapper, conversationId);
    return List.of(
        new DirectMessage("m1", conversationId, "ana.cocina", "me", "Tip anterior sobre la receta.", Instant.now()),
        new DirectMessage("m2", conversationId, "me", "ana.cocina", "Gracias, salió increíble.", Instant.now())
    );
  }

  public DirectMessage sendMessage(String conversationId, String from, String to, String text) {
    String sql = new StringBuilder()
        .append("INSERT INTO messages (conversation_id, from_user, to_user, text) ")
        .append("VALUES (?, ?, ?, ?)")
        .toString();
    // jdbcTemplate.update(sql, conversationId, from, to, text);
    return new DirectMessage("m-new", conversationId, from, to, text, Instant.now());
  }

  public void deleteMessage(String messageId) {
    String sql = "DELETE FROM messages WHERE id = ?";
    // jdbcTemplate.update(sql, messageId);
  }

  public Conversation createConversation(String a, String b) {
    String sql = "INSERT INTO conversations (participant_a, participant_b) VALUES (?, ?)";
    // jdbcTemplate.update(sql, a, b);
    return new Conversation("conv-new", a, b, Instant.now());
  }

  public List<Conversation> listConversations(String userId) {
    String sql = new StringBuilder()
        .append("SELECT id, participant_a, participant_b, updated_at FROM conversations ")
        .append("WHERE participant_a = ? OR participant_b = ? ORDER BY updated_at DESC")
        .toString();
    // return jdbcTemplate.query(sql, mapper, userId, userId);
    return List.of(new Conversation("conv-1", userId, "ana.cocina", Instant.now()));
  }
}
