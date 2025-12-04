package com.socialapp.litback.model;

/**
 * Represents a unidirectional friendship (follow) relationship between two users.
 */
public record Friendship(String id, String userId, String friendId) {}
