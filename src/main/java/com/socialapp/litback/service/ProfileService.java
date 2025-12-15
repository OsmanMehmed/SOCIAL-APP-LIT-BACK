package com.socialapp.litback.service;

import com.socialapp.litback.dao.ProfileDao;
import com.socialapp.litback.model.UserProfile;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
  private final ProfileDao profileDao;
  private final FriendService friendService;

  public ProfileService(ProfileDao profileDao, FriendService friendService) {
    this.profileDao = profileDao;
    this.friendService = friendService;
  }

  public Optional<UserProfile> getProfile(String id) {
    return profileDao.findById(id);
  }

  public Optional<UserProfile> getProfile(String id, String currentUserId) {
    final String lookupId;
    if ((id == null || id.isBlank() || "me".equals(id)) && currentUserId != null) {
      lookupId = currentUserId;
    } else {
      lookupId = id;
    }
    if (lookupId == null || lookupId.isBlank()) {
      return Optional.empty();
    }
    final String finalLookup = lookupId;
    return profileDao.findById(finalLookup).flatMap(profile -> {
      boolean isOwnProfile = currentUserId != null && currentUserId.equals(profile.id());
      if (profile.banned() && !isAdmin(currentUserId) && !isOwnProfile) {
        return Optional.empty();
      }
      boolean isFriend = !isOwnProfile && friendService.isFriend(currentUserId, profile.id());
      return Optional.of(new UserProfile(
          profile.id(),
          profile.username(),
          profile.subtitle(),
          isFriend,
          profile.banned(),
          profile.avatarUrl(),
          profile.url(),
          profile.admin(),
          isOwnProfile));
    });
  }

  public boolean vetProfile(String id, boolean banned) {
    return profileDao.vetProfile(id, banned);
  }

  public UserProfile createProfile(UserProfile profile) {
    return profileDao.create(profile);
  }

  public UserProfile updateProfile(UserProfile profile) {
    return profileDao.update(profile);
  }

  public void deleteProfile(String id) {
    profileDao.delete(id);
  }

  public boolean isAdmin(String id) {
    return profileDao.isAdmin(id);
  }
}
