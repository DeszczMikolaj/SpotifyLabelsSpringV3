package spotify.spotifylabelsspringv3.domain.user;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(nullable = false)
    @Getter
    private String spotifyId;

    public User(String spotifyId) {
        this.spotifyId = spotifyId;
    }
}
