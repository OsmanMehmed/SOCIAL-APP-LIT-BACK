package com.socialapp.litback.model;

public record UserProfile(
    String id,
    String username,
    String subtitle,
    boolean friend,
    boolean banned,
    String avatarUrl,
    String url,
    boolean admin,
    boolean isOwnProfile) {}
