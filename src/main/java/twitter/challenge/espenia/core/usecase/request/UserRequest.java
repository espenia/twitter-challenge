package twitter.challenge.espenia.core.usecase.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(

        @NotBlank
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    String username,
        @NotBlank
    @Size(max = 100, message = "Display name cannot exceed 100 characters")
    String displayName,
        @NotBlank
    @Email(message = "Email should be valid")
    String email,
        @NotBlank
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    String password,
        @NotBlank
    @Size(max = 500, message = "Bio cannot exceed 500 characters")
    String bio
) {}
