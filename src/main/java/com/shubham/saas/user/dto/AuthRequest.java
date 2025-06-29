package com.shubham.saas.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record AuthRequest(
        @NotEmpty
        @Email(message = "Invalid Email Provided.")
        String email,

        @NotEmpty
        @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters.")
        String password
) {
}
