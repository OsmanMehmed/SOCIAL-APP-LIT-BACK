package com.socialapp.litback.model;

import java.util.List;

public record Post(
        String id,
        String title,
        String description,

        String authorId,
        String imageUrl,
        int likes,
        int comments,
        int saves,
        boolean banned,
        Boolean liked,
        List<String> tags) {
}
