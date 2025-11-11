package spotify.spotifylabelsspringv3.track;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import spotify.spotifylabelsspringv3.label.Label;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tracks")
@Data
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Getter
    private String spotifyId;               // Track's Spotify ID

    @Column(nullable = false)
    @Getter
    private String name;             // Track's name (song name)

    @Convert(converter = ArtistConverter.class)
    @Getter
    private Set<Artist> artists;

    @ManyToMany(mappedBy = "tracks")
    private Set<Label> labels = new HashSet<>();

    public Track(String spotifyId, String name, Set<Artist> artists) {
        this.spotifyId = spotifyId;
        this.name = name;
        this.artists = artists;
    }
}