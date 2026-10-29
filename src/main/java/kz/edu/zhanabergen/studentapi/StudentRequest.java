package kz.edu.zhanabergen.studentapi;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StudentRequest(
    @NotBlank(message = "Full name is required") String fullName,
    @NotBlank(message = "Email is required") @Email(message = "Invalid email") String email,
    @NotBlank(message = "Major is required") String major,
    @NotNull(message = "Course is required")
    @Min(value = 1, message = "Course must be at least 1")
    @Max(value = 6, message = "Course must be at most 6") Integer course
) { }
