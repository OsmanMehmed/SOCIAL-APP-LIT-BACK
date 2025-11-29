package com.socialapp.litback.model;

import java.time.Instant;

public record Conversation(String id, String participantA, String participantB, Instant updatedAt) {}
