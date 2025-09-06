package com.social.media.domain.posts.dto;

import java.time.LocalDateTime;

public record ResponsePostDto(String text, String imageUrl, LocalDateTime createdAt, Long userId) {
}
