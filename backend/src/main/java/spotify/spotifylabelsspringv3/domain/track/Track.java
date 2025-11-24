package spotify.spotifylabelsspringv3.domain.track;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import spotify.spotifylabelsspringv3.domain.label.Label;
import spotify.spotifylabelsspringv3.domain.artist.Artist;
import spotify.spotifylabelsspringv3.domain.artist.ArtistConverter;

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
    private String spotifyUri;               // Track's Spotify ID

    @Column(nullable = false)
    @Getter
    private String name;             // Track's name (song name)

    @Convert(converter = ArtistConverter.class)
    @Column
    @Getter
    private Set<Artist> artists;

    @ManyToMany(mappedBy = "tracks")
    private Set<Label> labels = new HashSet<>();

    public Track(String spotifyUri, String name, Set<Artist> artists) {
        this.spotifyUri = spotifyUri;
        this.name = name;
        this.artists = artists;
    }


}