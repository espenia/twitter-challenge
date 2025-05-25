package twitter.challenge.espenia.core.result;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import twitter.challenge.espenia.core.domain.TimelineCache;
import twitter.challenge.espenia.core.domain.Tweet;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Builder
public record TimelineCacheResponse(
    String id,
    String userId,
    List<String> tweetIds,
    Date lastUpdated,
    List<TweetResponse> tweets
) {
    /**
     * Maps a TimelineCache domain object to a TimelineCacheResponse
     *
     * @param timelineCache the TimelineCache domain object
     * @param tweets the list of Tweet objects
     * @return TimelineCacheResponse
     */
    public static TimelineCacheResponse from(TimelineCache timelineCache, List<Tweet> tweets) {
        log.debug("Creating TimelineCacheResponse from TimelineCache for user: {}", timelineCache.getUserId());
        List<TweetResponse> tweetResponses = tweets.stream()
                .map(tweet -> TweetResponse.builder()
                        .id(tweet.getId())
                        .userId(tweet.getUserId())
                        .content(tweet.getContent())                        .createdAt(tweet.getCreatedAt())
                        .likeCount(tweet.getLikeCount())
                        .build())
                .collect(Collectors.toList());
        
        log.debug("Created {} tweet responses for timeline", tweetResponses.size());
        
        TimelineCacheResponse response = TimelineCacheResponse.builder()
                .id(timelineCache.getId())
                .userId(timelineCache.getUserId())
                .tweetIds(timelineCache.getTweetIds())
                .lastUpdated(timelineCache.getLastUpdated())
                .tweets(tweetResponses)
                .build();
        
        log.debug("TimelineCacheResponse created successfully for user: {}", timelineCache.getUserId());
        return response;
    }
}
