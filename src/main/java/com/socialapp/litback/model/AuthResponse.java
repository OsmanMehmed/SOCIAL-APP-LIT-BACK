package com.socialapp.litback.model;

public record AuthResponse(
    String token,
    UserProfile userProfile) {}
