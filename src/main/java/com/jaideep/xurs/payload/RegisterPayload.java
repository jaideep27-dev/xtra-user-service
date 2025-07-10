package com.jaideep.xurs.payload;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RegisterPayload(@NotBlank(message = "Please provide a username") String name,
                              @Email(message = "Please provide a valid email") String email,
                              @Nullable @Size(min = 8, message = "Password must be at least 8 characters long") String password) {
}
