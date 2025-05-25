package twitter.challenge.espenia.entrypoint.exception.response.dto;

import java.util.Set;

public class UnproccesableEntityApiError extends ApiError {

    private final Set<UnproccesableEntityApiError.Validation> validations;
    private final String entityName;

    public UnproccesableEntityApiError(final String entityName,
                                       final Set<UnproccesableEntityApiError.Validation> validations) {
        super("validation_error", String.format("invalid entity %s", entityName), 422);
        this.validations = validations;
        this.entityName = entityName;
        this.setCause(validations.toString());
    }

    public record Validation(String field, String message) {
    }

}
