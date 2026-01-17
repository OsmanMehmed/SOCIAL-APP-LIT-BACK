package com.socialapp.litback.model;

import java.time.Instant;

public record DirectMessage(
    String id,
    String conversationId,
    String fromUserId,
    String toUserId,
    String text,
    Instant sentAt) {}
