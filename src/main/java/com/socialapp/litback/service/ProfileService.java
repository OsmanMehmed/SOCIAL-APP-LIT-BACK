package com.socialapp.litback.service;

import com.socialapp.litback.dao.FriendDao;
import com.socialapp.litback.dao.MessageDao;
import com.socialapp.litback.dao.PostDao;
import com.socialapp.litback.dao.ProfileDao;
import com.socialapp.litback.model.UserProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProfileService {
  private final ProfileDao profileDao;
  private final FriendService friendService;
  private final FriendDao friendDao;
  private final PostDao postDao;
  private final MessageDao messageDao;
  private final com.socialapp.litback.dao.AuthDao authDao;

  public ProfileService(ProfileDao profileDao, FriendService friendService, FriendDao friendDao, PostDao postDao,
      MessageDao messageDao, com.socialapp.litback.dao.AuthDao authDao) {
    this.profileDao = profileDao;
    this.friendService = friendService;
    this.friendDao = friendDao;
    this.postDao = postDao;
    this.messageDao = messageDao;
    this.authDao = authDao;
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

  @Transactional
  public void deleteProfile(String id) {
    postDao.deleteLikesByUserId(id);
    postDao.deleteCommentsByAuthorId(id);
    postDao.deleteByAuthorId(id);
    friendDao.deleteAllFriendships(id);
    friendDao.deleteAllFriendRequests(id);
    messageDao.deleteAllConversations(id);
    authDao.deleteSessionsByUserId(id);
    profileDao.delete(id);
  }

  public boolean isAdmin(String id) {
    return profileDao.isAdmin(id);
  }
}
