package twitter.challenge.espenia.core.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import twitter.challenge.espenia.core.domain.Follow;
import twitter.challenge.espenia.core.domain.TimelineCache;
import twitter.challenge.espenia.core.domain.Tweet;
import twitter.challenge.espenia.core.gateway.FollowGateway;
import twitter.challenge.espenia.core.gateway.TimelineCacheGateway;
import twitter.challenge.espenia.core.gateway.TweetGateway;
import twitter.challenge.espenia.core.gateway.UserGateway;
import twitter.challenge.espenia.core.result.TimelineCacheResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetTimelineUseCase {

    // TODO move to a configuration service
    private static final int DEFAULT_TIMELINE_LIMIT = 20;
    private static final long FIVE_MINUTES_IN_MS = 5 * 60 * 1000;


    private final TweetGateway tweetGateway;
    private final UserGateway userGateway;
    private final FollowGateway followGateway;
    private final TimelineCacheGateway timelineCacheGateway;
    
    /**
     * Gets the timeline for a user.
     * If there's a cache hit and the cache is fresh, return the cached timeline.
     * Otherwise, generate a new timeline, cache it, and return it.
     *
     * @param userId the ID of the user
     * @return the timeline with tweets
     */
    public TimelineCacheResponse execute(final String userId) {
        log.info("Getting timeline for user: {}", userId);
        
        // Verify user exists
        userGateway.findById(userId);
        
        try {
            // Try to get from cache first
            if (timelineCacheGateway.existsByUserId(userId)) {
                TimelineCache cache = timelineCacheGateway.findByUserId(userId);
                
                // If cache is recent (less than 5 minutes old), use it
                if (isCacheFresh(cache.getLastUpdated())) {
                    List<Tweet> tweets = tweetGateway.findByIds(cache.getTweetIds());
                    log.info("Returning {} cached tweets for user: {}", tweets.size(), userId);
                    return TimelineCacheResponse.from(cache, tweets);
                }
            }
        } catch (Exception e) {
            // If any error occurs with cache, proceed with generating a new timeline
            log.warn("Error accessing timeline cache for user: {}: {}", userId, e.getMessage());
        }
        
        // Generate a new timeline
        return generateAndCacheTimeline(userId);
    }
    
    private static boolean isCacheFresh(final Date lastUpdated) {
        // Cache is fresh if it's less than 5 minutes old
        boolean isFresh = (new Date().getTime() - lastUpdated.getTime()) < FIVE_MINUTES_IN_MS;
        log.debug("Cache freshness check: {} (last updated: {})", isFresh ? "fresh" : "stale", lastUpdated);
        return isFresh;
    }

    private TimelineCacheResponse generateAndCacheTimeline(final String userId) {
        log.debug("Generating new timeline for user: {}", userId);

        List<Tweet> allTweets = buildTweetTimeline(userId);

        allTweets = limitTimelineTweets(allTweets);

        final List<String> tweetIds = updateCachedTimeLine(userId, allTweets);

        // Build response
        log.debug("Building timeline response for user: {}", userId);
        TimelineCache timelineCache = TimelineCache.builder()
                .userId(userId)
                .tweetIds(tweetIds)
                .lastUpdated(new Date())
                .build();

        TimelineCacheResponse response = TimelineCacheResponse.from(timelineCache, allTweets);
        log.info("Successfully generated new timeline for user: {} with {} tweets", userId, allTweets.size());
        return response;
    }

    private List<Tweet> buildTweetTimeline(final String userId) {
        // Get user's own tweets
        final List<Tweet> userTweets = tweetGateway.findByUserIdOrderByCreatedAtDesc(userId, DEFAULT_TIMELINE_LIMIT);

        // Get tweets from users the user is following
        log.debug("Fetching users that user {} is following", userId);
        final List<String> followingIds = followGateway.getFollowing(userId).stream()
                .map(Follow::getFollowedId)
                .toList();

        List<Tweet> followingTweets = new ArrayList<>();
        if (!followingIds.isEmpty()) {
            followingTweets = tweetGateway.findByUserIdsOrderByCreatedAtDesc(followingIds, DEFAULT_TIMELINE_LIMIT);
            log.debug("Found {} tweets from followed users", followingTweets.size());
        }

        // Combine and sort tweets by created date (most recent first)
        List<Tweet> allTweets = new ArrayList<>();
        allTweets.addAll(userTweets);
        allTweets.addAll(followingTweets);
        log.debug("Combined {} total tweets before sorting and limiting", allTweets.size());
        return allTweets;
    }

    private static List<Tweet> limitTimelineTweets(List<Tweet> allTweets) {
        allTweets.sort((t1, t2) -> t2.getCreatedAt().compareTo(t1.getCreatedAt()));
        // Limit to 20 most recent tweets
        if (allTweets.size() > DEFAULT_TIMELINE_LIMIT) {
            log.debug("Limiting {} tweets to {} most recent", allTweets.size(), DEFAULT_TIMELINE_LIMIT);
            allTweets = allTweets.subList(0, DEFAULT_TIMELINE_LIMIT);
        }
        return allTweets;
    }

    private List<String> updateCachedTimeLine(final String userId, final List<Tweet> allTweets) {
        // Save to cache
        final List<String> tweetIds = allTweets.stream()
                .map(Tweet::getId)
                .toList();

        log.debug("Updating timeline cache for user: {} with {} tweets", userId, tweetIds.size());
        timelineCacheGateway.updateTimeline(userId, tweetIds);
        return tweetIds;
    }
}
