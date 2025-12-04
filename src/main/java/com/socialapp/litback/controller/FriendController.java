package com.socialapp.litback.controller;

import com.socialapp.litback.model.FriendRequest;
import com.socialapp.litback.model.Friendship;
import com.socialapp.litback.model.UserProfile;
import com.socialapp.litback.service.FriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendController {
  private final FriendService friendService;

  public FriendController(FriendService friendService) {
    this.friendService = friendService;
  }

  @GetMapping("/search")
  public ResponseEntity<List<UserProfile>> search(@RequestParam String q) {
    return ResponseEntity.ok(friendService.search(q));
  }

  @GetMapping("/random")
  public ResponseEntity<List<UserProfile>> random(
      @RequestParam(defaultValue = "5") int limit) {
    return ResponseEntity.ok(friendService.randomProfiles(limit));
  }

  @GetMapping("/status")
  public ResponseEntity<Boolean> status(
      @RequestParam String friendId,
      @RequestHeader(value = "X-User-Id", required = false) String currentUserId) {
    if (currentUserId == null || currentUserId.isBlank()) {
      return ResponseEntity.status(401).build();
    }
    if (currentUserId.equals(friendId)) {
      return ResponseEntity.ok(true);
    }
    return ResponseEntity.ok(friendService.isFriend(currentUserId, friendId));
  }

  @PostMapping
  public ResponseEntity<Friendship> connect(
      @RequestParam String friendId,
      @RequestHeader(value = "X-User-Id", required = false) String currentUserId) {
    if (currentUserId == null || currentUserId.isBlank()) {
      return ResponseEntity.status(401).build();
    }
    if (currentUserId.equals(friendId)) {
      return ResponseEntity.status(400).build();
    }
    return ResponseEntity.ok(friendService.connect(currentUserId, friendId));
  }

  @DeleteMapping
  public ResponseEntity<Void> disconnect(
      @RequestParam String friendId,
      @RequestHeader(value = "X-User-Id", required = false) String currentUserId) {
    if (currentUserId == null || currentUserId.isBlank()) {
      return ResponseEntity.status(401).build();
    }
    friendService.disconnect(currentUserId, friendId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/requests")
  public ResponseEntity<FriendRequest> sendRequest(
      @RequestParam String from,
      @RequestParam String to,
      @RequestHeader(value = "X-User-Id", required = false) String currentUserId) {
        
    if (from.equals(to)) {
      return ResponseEntity.status(400).build();
    }

    if (currentUserId != null && !currentUserId.equals(from)) {
      return ResponseEntity.status(403).build();
    }
    return ResponseEntity.ok(friendService.sendRequest(from, to));
  }

  @PostMapping("/requests/{id}/respond")
  public ResponseEntity<FriendRequest> respond(
      @PathVariable String id,
      @RequestParam String status,
      @RequestHeader(value = "X-User-Id", required = false) String currentUserId) {
    return ResponseEntity.ok(friendService.respond(id, status));
  }

  @GetMapping("/requests")
  public ResponseEntity<List<FriendRequest>> pending(@RequestParam String userId) {
    return ResponseEntity.ok(friendService.listPending(userId));
  }

  @GetMapping
  public ResponseEntity<List<UserProfile>> friends(@RequestParam String userId) {
    return ResponseEntity.ok(friendService.listFriends(userId));
  }
}
