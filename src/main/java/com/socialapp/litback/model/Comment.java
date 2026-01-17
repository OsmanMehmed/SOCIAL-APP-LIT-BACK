package com.socialapp.litback.model;

import java.time.Instant;

public record Comment(
    String id,
    String postId,
    String authorId,
    String text,
    Instant createdAt) {}
