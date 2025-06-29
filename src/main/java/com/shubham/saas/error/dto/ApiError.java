package com.shubham.saas.error.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiError(
        int status,
        String error,
        String message,
        Map<String, String> details,
        LocalDateTime timestamp
) {
}
