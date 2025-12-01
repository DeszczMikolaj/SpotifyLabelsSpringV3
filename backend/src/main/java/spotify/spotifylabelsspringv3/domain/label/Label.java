package spotify.spotifylabelsspringv3.domain.label;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spotify.spotifylabelsspringv3.domain.track.Track;
import spotify.spotifylabelsspringv3.domain.user.User;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "labels")
@NoArgsConstructor
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name ="user_id", nullable = false)
    User user;

    @Column(nullable = false, unique = true)
    @Getter
    String name;

    @Column(nullable = false)
    @Getter
    String colorHex;

    @ManyToMany
    @JoinTable(
            name = "labels_tracks",
            joinColumns = @JoinColumn(name = "label_id"),
            inverseJoinColumns = @JoinColumn(name ="track_id")
    )
    @Getter
    private Set<Track> tracks = new HashSet<>();

    public Label(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public void rename(String newName) {
        this.name = newName;
    }

    public void addTrack(Track track) {
        tracks.add(track);
        track.getLabels().add(this);
    }
}
