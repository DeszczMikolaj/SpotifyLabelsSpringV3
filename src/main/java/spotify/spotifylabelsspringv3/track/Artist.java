package spotify.spotifylabelsspringv3.track;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Artist {
    String name;
}
