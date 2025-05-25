package twitter.challenge.espenia.core.usecase.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record TweetRequest(
    @NotBlank(message = "User ID is required")
    String userId,
      @NotBlank(message = "Content is required")
    @Size(min = 1, max = 280, message = "Tweet content must be between 1 and 280 characters")
    String content
) {}
