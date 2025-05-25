package twitter.challenge.espenia.core.usecase.request;


import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserUpdateRequest(
        @Size(max = 100, message = "Display name cannot exceed 100 characters")
        String displayName,
        @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
        String password,
        @Size(max = 500, message = "Bio cannot exceed 500 characters")
        String bio
) {}
