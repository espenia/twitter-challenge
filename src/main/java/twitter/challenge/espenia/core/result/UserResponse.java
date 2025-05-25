package twitter.challenge.espenia.core.result;

import lombok.Builder;

@Builder
public record UserResponse(
    String id,
    String username,
    String displayName,
    String email,
    String bio
) {}
