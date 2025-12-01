package spotify.spotifylabelsspringv3.domain.user;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void registerUserIfRequeried(String spotifyId) {
        Optional<User> registeredUser = userRepository.findBySpotifyId(spotifyId);
        registeredUser.orElseGet(() -> {
            User newUser = new User(spotifyId);
            userRepository.save(newUser);
            return newUser;
        });
    }

    public User getUser(String spotifyId) {
        Optional<User> userResult = userRepository.findBySpotifyId(spotifyId);
        if(userResult.isEmpty()) {
            throw new IllegalStateException("Unregistered user action:" + spotifyId);
        }
        return userResult.get();
    }


}
