package com.socialapp.litback.service;

import com.socialapp.litback.dao.FriendDao;
import com.socialapp.litback.model.FriendRequest;
import com.socialapp.litback.model.Friendship;
import com.socialapp.litback.model.UserProfile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {
  private final FriendDao friendDao;

  public FriendService(FriendDao friendDao) {
    this.friendDao = friendDao;
  }

  public List<UserProfile> search(String query) {
    return friendDao.search(query);
  }

  public FriendRequest sendRequest(String from, String to) {
    return friendDao.sendRequest(from, to);
  }

  public FriendRequest respond(String requestId, String status) {
    return friendDao.respond(requestId, status);
  }

  public List<FriendRequest> listPending(String userId) {
    return friendDao.listPending(userId);
  }

  public List<UserProfile> listFriends(String userId) {
    return friendDao.listFriends(userId);
  }

  public Friendship connect(String userId, String friendId) {
    return friendDao.createFriendship(userId, friendId);
  }

  public void disconnect(String userId, String friendId) {
    friendDao.deleteFriendship(userId, friendId);
  }

  public boolean isFriend(String userId, String friendId) {
    return friendDao.existsFriendship(userId, friendId);
  }

  public List<UserProfile> randomProfiles(int limit) {
    return friendDao.randomProfiles(limit);
  }
}
