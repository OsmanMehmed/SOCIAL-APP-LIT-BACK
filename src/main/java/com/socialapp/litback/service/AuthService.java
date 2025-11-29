package com.socialapp.litback.service;

import com.socialapp.litback.dao.AuthDao;
import com.socialapp.litback.model.AuthRequest;
import com.socialapp.litback.model.AuthResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private final AuthDao authDao;

  public AuthService(AuthDao authDao) {
    this.authDao = authDao;
  }

  public AuthResponse login(AuthRequest request) {
    return authDao.login(request);
  }

  public AuthResponse register(AuthRequest request) {
    return authDao.register(request);
  }

  public void logout(String token) {
    authDao.logout(token);
  }

  public AuthResponse refresh(String token) {
    return authDao.refresh(token);
  }
}
