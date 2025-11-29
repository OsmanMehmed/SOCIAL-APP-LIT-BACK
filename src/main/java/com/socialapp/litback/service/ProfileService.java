package com.socialapp.litback.service;

import com.socialapp.litback.dao.ProfileDao;
import com.socialapp.litback.model.UserProfile;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
  private final ProfileDao profileDao;

  public ProfileService(ProfileDao profileDao) {
    this.profileDao = profileDao;
  }

  public Optional<UserProfile> getProfile(String id) {
    return profileDao.findById(id);
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
}
