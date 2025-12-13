package com.socialapp.litback.model;

public record Post(
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
        java.util.List<String> tags) {
}
