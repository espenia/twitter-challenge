package twitter.challenge.espenia.core.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    
    private String id;
    private String username;
    private String displayName;
    private String email;
    private String bio;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
