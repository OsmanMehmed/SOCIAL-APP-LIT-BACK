package com.socialapp.litback.controller;

import com.socialapp.litback.model.UserProfile;
import com.socialapp.litback.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
  private final ProfileService profileService;

  public ProfileController(ProfileService profileService) {
    this.profileService = profileService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserProfile> getProfile(@PathVariable String id) {
    return profileService.getProfile(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<UserProfile> createProfile(@RequestBody UserProfile profile) {
    return ResponseEntity.ok(profileService.createProfile(profile));
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserProfile> updateProfile(@PathVariable String id, @RequestBody UserProfile profile) {
    UserProfile updated = profileService.updateProfile(new UserProfile(id, profile.username(), profile.subtitle(), profile.friend(), profile.banned(), profile.avatarUrl()));
    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProfile(@PathVariable String id) {
    profileService.deleteProfile(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}/vet")
  public ResponseEntity<Void> vetProfile(
      @PathVariable String id,
      @RequestParam(defaultValue = "true") boolean banned) {
    profileService.vetProfile(id, banned);
    return ResponseEntity.noContent().build();
  }
}
