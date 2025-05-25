package twitter.challenge.espenia.core.result;

import lombok.Builder;
import twitter.challenge.espenia.core.domain.Follow;

import java.util.Date;
import java.util.List;

@Builder
public record FollowResponse(
    String id,
    String followerId,
    String followedId,
    Date createdAt
) {
    /**
     * Maps a Follow domain object to a FollowResponse
     *
     * @param follow the Follow domain object
     * @return FollowResponse
     */
    public static FollowResponse from(Follow follow) {
        return FollowResponse.builder()
                .id(follow.getId())
                .followerId(follow.getFollowerId())
                .followedId(follow.getFollowedId())
                .createdAt(follow.getCreatedAt())
                .build();
    }
    
    /**
     * Maps a list of Follow domain objects to a list of FollowResponse objects
     *
     * @param follows list of Follow domain objects
     * @return List of FollowResponse objects
     */
    public static List<FollowResponse> from(List<Follow> follows) {
        return follows.stream()
                .map(FollowResponse::from)
                .toList();
    }
}
