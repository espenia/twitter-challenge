package twitter.challenge.espenia.entrypoint;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import twitter.challenge.espenia.core.usecase.UserService;
import twitter.challenge.espenia.core.usecase.request.UserRequest;
import twitter.challenge.espenia.core.result.UserResponse;
import twitter.challenge.espenia.core.usecase.request.UserUpdateRequest;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * Create a new user
     * 
     * @param userRequest The user data
     * @return The created user
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody final UserRequest userRequest) {
        UserResponse createdUser = userService.create(userRequest);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Get a user by ID
     * 
     * @param id The user ID
     * @return The user data
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable final String id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    
    /**
     * Get a user by username
     * 
     * @param username The username
     * @return The user data
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable final String username) {
        UserResponse user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    /**
     * Update a user
     * 
     * @param id The user ID
     * @param updateRequest The update data
     * @return The updated user
     */
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable final String id,
            @Valid @RequestBody final UserUpdateRequest updateRequest) {
        UserResponse updatedUser = userService.updateUser(id, updateRequest);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Delete a user
     * 
     * @param id The user ID
     * @return No content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable final String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
