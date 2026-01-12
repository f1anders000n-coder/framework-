package com.example.complexlab.core.dto;

import java.time.Instant;

public record BookDto(Long id, String title, String author, String description, Instant createdAt) {
}
