package com.shubham.saas.user.dto;

public record AuthRequest(
        String email,
        String password
) {
}
