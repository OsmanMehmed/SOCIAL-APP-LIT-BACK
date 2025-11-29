package com.socialapp.litback.model;

public record Post(
    String id,
    String caption,
    String authorId,
    int likes,
    int comments,
    int saves) {}
