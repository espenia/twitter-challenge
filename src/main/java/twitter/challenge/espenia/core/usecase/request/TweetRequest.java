package twitter.challenge.espenia.core.usecase.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TweetRequest(
    @NotBlank(message = "User ID is required")
    String userId,
    
    @NotBlank(message = "Content is required")
    @Size(max = 280, message = "Tweet content must be between 1 and 280 characters")
    String content
) {}
