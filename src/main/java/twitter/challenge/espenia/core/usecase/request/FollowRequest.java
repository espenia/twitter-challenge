package twitter.challenge.espenia.core.usecase.request;

import jakarta.validation.constraints.NotBlank;

public record FollowRequest(
    @NotBlank(message = "Followed user ID cannot be blank")
    String followedId
) {}
