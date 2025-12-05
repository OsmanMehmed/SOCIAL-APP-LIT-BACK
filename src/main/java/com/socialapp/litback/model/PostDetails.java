package com.socialapp.litback.model;

import java.time.Instant;
import java.util.List;

public record PostDetails(
    String id,
    String caption,
    String authorId,
    String imageUrl,
    int likes,
    int comments,
    int saves,
    boolean banned,
    Boolean liked,
    List<Comment> commentsList,
    Instant updatedAt) {}
