package com.socialapp.litback.controller;

import com.socialapp.litback.model.UserProfile;
import com.socialapp.litback.service.ProfileService;
import com.socialapp.litback.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
  private final ProfileService profileService;
  private final AuthService authService;

  public ProfileController(ProfileService profileService, AuthService authService) {
    this.profileService = profileService;
    this.authService = authService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserProfile> getProfile(
      @PathVariable String id,
      @RequestHeader(value = "X-User-Id", required = false) String currentUserId,
      @RequestHeader(value = "Authorization", required = false) String authToken) {

    if ((id == null || "me".equals(id))
        && (currentUserId == null || "me".equals(currentUserId))
        && authToken != null && !authToken.isBlank()) {
      try {
        var auth = authService.refresh(authToken);
        if (auth != null && auth.userProfile() != null) {
          currentUserId = auth.userProfile().id();
        }
      } catch (Exception ex) {
      }
    }
    return profileService.getProfile(id, currentUserId)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<UserProfile> createProfile(@RequestBody UserProfile profile) {
    return ResponseEntity.ok(profileService.createProfile(profile));
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserProfile> updateProfile(
      @PathVariable String id,
      @RequestBody UserProfile profile,
      @RequestHeader(value = "X-User-Id", required = false) String currentUserId) {

    if (currentUserId != null && !currentUserId.equals(id) && !profileService.isAdmin(currentUserId)) {
      return ResponseEntity.status(403).build();
    }
    return profileService.getProfile(id, currentUserId)
        .map(existing -> {
          UserProfile updated = new UserProfile(
              id,
              profile.username(),
              profile.subtitle(),
              existing.friend(),
              existing.banned(),
              profile.avatarUrl() != null ? profile.avatarUrl() : existing.avatarUrl(),
              profile.url(),
              existing.admin(),
              true);
          return ResponseEntity.ok(profileService.updateProfile(updated));
        })
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProfile(@PathVariable String id) {
    profileService.deleteProfile(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}/vet")
  public ResponseEntity<Void> vetProfile(
      @PathVariable String id,
      @RequestParam(defaultValue = "true") boolean banned,
      @RequestHeader(value = "X-User-Id", required = false) String currentUserId) {

    if (currentUserId != null && currentUserId.equals(id)) {
      return ResponseEntity.status(400).build();
    }
    profileService.vetProfile(id, banned);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}/avatar")
  public ResponseEntity<UserProfile> uploadAvatar(
      @PathVariable String id,
      @RequestParam("file") MultipartFile file,
      @RequestHeader(value = "X-User-Id", required = false) String currentUserId) {

    if (currentUserId != null && !currentUserId.equals(id) && !profileService.isAdmin(currentUserId)) {
      return ResponseEntity.status(403).build();
    }

    try {
      if (file.isEmpty()) {
        return ResponseEntity.badRequest().build();
      }

      Path uploadDir = Paths.get("uploads/avatars");
      if (!Files.exists(uploadDir)) {
        Files.createDirectories(uploadDir);
      }

      String originalFilename = file.getOriginalFilename();
      String filename = UUID.randomUUID().toString() + "-"
          + (originalFilename != null ? originalFilename.replaceAll("[^a-zA-Z0-9.-]", "")
              : "avatar.png");
      Path filePath = uploadDir.resolve(filename);
      Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

      String avatarUrl = "/api/assets/avatars/" + filename;

      return profileService.getProfile(id, currentUserId)
          .map(p -> {
            UserProfile updated = new UserProfile(
                p.id(),
                p.username().replace("@", ""),
                p.subtitle(),
                p.friend(),
                p.banned(),
                avatarUrl,
                p.url(),
                p.admin(),
                true);
            return ResponseEntity.ok(profileService.updateProfile(updated));
          })
          .orElseGet(() -> ResponseEntity.notFound().build());

    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.status(500).build();
    }
  }
}
