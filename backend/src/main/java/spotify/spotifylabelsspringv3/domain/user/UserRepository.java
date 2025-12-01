package spotify.spotifylabelsspringv3.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findBySpotifyId(String spotifyId);
}
