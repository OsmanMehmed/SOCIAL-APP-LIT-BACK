package com.socialapp.litback.model;

import java.time.Instant;
import java.util.List;

public record PostDetails(
        String id,
        String title,
        String description,
        String caption,
        String authorId,
        String imageUrl,
        int likes,
        int comments,
        int saves,
        boolean banned,
        Boolean liked,
        List<Comment> commentsList,
        List<String> imageUrls,
        Instant updatedAt,
        List<String> tags) {
}
