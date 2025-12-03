package spotify.spotifylabelsspringv3.domain.track;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import spotify.spotifylabelsspringv3.domain.label.Label;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tracks")
@Data
public class Track {

    protected Track() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Getter
    private String spotifyId;               // Track's Spotify ID


    @Column(nullable = false, unique = true)
    @Getter
    private String spotifyUri;

    @ManyToMany(mappedBy = "tracks")
    private Set<Label> labels = new HashSet<>();

    public Track(String spotifyId, String spotifyUri) {
        this.spotifyId = spotifyId;
        this.spotifyUri = spotifyUri;
    }

}