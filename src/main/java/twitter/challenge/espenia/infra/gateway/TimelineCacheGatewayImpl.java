package twitter.challenge.espenia.infra.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import twitter.challenge.espenia.core.domain.TimelineCache;
import twitter.challenge.espenia.core.exception.NotFoundException;
import twitter.challenge.espenia.core.gateway.TimelineCacheGateway;
import twitter.challenge.espenia.infra.gateway.mapper.TimelineCacheMapper;
import twitter.challenge.espenia.infra.mongodb.document.TimelineCacheDocument;
import twitter.challenge.espenia.infra.mongodb.repository.TimelineCacheRepository;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class TimelineCacheGatewayImpl extends BaseMongoGateway<TimelineCacheDocument, String, TimelineCacheRepository> implements TimelineCacheGateway {

    private final TimelineCacheMapper timelineCacheMapper;

    public TimelineCacheGatewayImpl(final TimelineCacheRepository repository, final TimelineCacheMapper timelineCacheMapper) {
        super(repository);
        this.timelineCacheMapper = timelineCacheMapper;
    }

    @Override
    public TimelineCache findByUserId(String userId) {
        log.debug("Finding timeline cache for user: {}", userId);
        return repository.findByUserId(userId)
                .map(timelineCacheMapper::toDomain)
                .orElseThrow(() -> {
                    log.warn("Timeline cache not found for user: {}", userId);
                    return new NotFoundException("Timeline cache not found for user: " + userId);
                });
    }

    @Override
    public TimelineCache save(TimelineCache timelineCache) {
        return timelineCacheMapper.toDomain(repository.save(timelineCacheMapper.toEntity(timelineCache)));
    }

    @Override
    public boolean existsByUserId(String userId) {
        return repository.existsByUserId(userId);
    }

    @Override
    public void updateTimeline(String userId, List<String> tweetIds) {
        TimelineCacheDocument timelineCache = repository.findByUserId(userId)
                .orElse(TimelineCacheDocument.builder()
                        .userId(userId)
                        .build());
                        
        timelineCache.setTweetIds(tweetIds);
        
        repository.save(timelineCache);
        log.info("Timeline updated for user: {}", userId);
    }
}
