package com.socialapp.litback.controller;

import com.socialapp.litback.model.Conversation;
import com.socialapp.litback.model.DirectMessage;
import com.socialapp.litback.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
  private final MessageService messageService;

  public MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  @GetMapping("/conversations")
  public ResponseEntity<List<Conversation>> listConversations(@RequestParam String userId) {
    return ResponseEntity.ok(messageService.listConversations(userId));
  }

  @PostMapping("/conversations")
  public ResponseEntity<Conversation> createConversation(
      @RequestParam String a,
      @RequestParam String b,
      @RequestHeader(value = "X-User-Id", required = false) String currentUserId) {
    // Validar que no se pueda crear conversación consigo mismo
    if (a.equals(b)) {
      return ResponseEntity.status(400).build();
    }
    // Validar que el usuario autenticado sea uno de los participantes
    if (currentUserId != null && !currentUserId.equals(a) && !currentUserId.equals(b)) {
      return ResponseEntity.status(403).build();
    }
    return ResponseEntity.ok(messageService.createConversation(a, b));
  }

  @GetMapping("/thread/{conversationId}")
  public ResponseEntity<List<DirectMessage>> getThread(@PathVariable String conversationId) {
    return ResponseEntity.ok(messageService.getThread(conversationId));
  }

  public record SendMessageRequest(String fromUserId, String toUserId, String text) {}

  @PostMapping("/thread/{conversationId}")
  public ResponseEntity<DirectMessage> sendMessage(
      @PathVariable String conversationId,
      @RequestBody SendMessageRequest payload,
      @RequestHeader(value = "X-User-Id", required = false) String currentUserId) {
    // Validar que el usuario autenticado sea quien envía el mensaje
    if (currentUserId != null && !currentUserId.equals(payload.fromUserId())) {
      return ResponseEntity.status(403).build();
    }
    return ResponseEntity.ok(
        messageService.send(conversationId, payload.fromUserId(), payload.toUserId(), payload.text()));
  }

  @DeleteMapping("/messages/{messageId}")
  public ResponseEntity<Void> deleteMessage(
      @PathVariable String messageId,
      @RequestHeader(value = "X-User-Id", required = false) String currentUserId) {
    messageService.deleteMessage(messageId);
    return ResponseEntity.noContent().build();
  }
}
