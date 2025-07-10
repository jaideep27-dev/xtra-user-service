package com.jaideep.xurs.payload;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserPayload {
    private String id;
    @NotBlank(message = "Please provide a username")
    private String name;
    @Email(message = "Please provide a valid email")
    private String email;
    private LocalDate dob;
    @Nullable
    @Pattern(regexp = "^\\d{10}$", message = "Provide a valid phone number")
    private String phoneNumber;
    @Nullable
    private String username;
}
