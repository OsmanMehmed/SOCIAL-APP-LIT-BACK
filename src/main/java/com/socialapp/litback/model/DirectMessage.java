package com.socialapp.litback.model;

import java.time.Instant;

public record DirectMessage(
    String id,
    String conversationId,
    String fromUser,
    String toUser,
    String text,
    Instant sentAt) {}
