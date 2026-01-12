package com.example.complexlab.core.dto;

import java.time.Instant;

public record CommentDto(Long id, Long bookId, String authorUsername, String text, Instant createdAt) {
}
