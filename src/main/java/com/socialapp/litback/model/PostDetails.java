package com.socialapp.litback.model;

import java.time.Instant;
import java.util.List;

public record PostDetails(
    String id,
    String caption,
    String authorId,
    int likes,
    int comments,
    int saves,
    List<Comment> commentsList,
    Instant updatedAt) {}
