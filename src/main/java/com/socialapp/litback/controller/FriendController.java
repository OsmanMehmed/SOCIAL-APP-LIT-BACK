package com.socialapp.litback.controller;

import com.socialapp.litback.model.FriendRequest;
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

  @PostMapping("/requests")
  public ResponseEntity<FriendRequest> sendRequest(@RequestParam String from, @RequestParam String to) {
    return ResponseEntity.ok(friendService.sendRequest(from, to));
  }

  @PostMapping("/requests/{id}/respond")
  public ResponseEntity<FriendRequest> respond(@PathVariable String id, @RequestParam String status) {
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
