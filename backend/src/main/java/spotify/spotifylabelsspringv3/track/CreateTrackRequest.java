package spotify.spotifylabelsspringv3.track;

import java.util.Set;

public record CreateTrackRequest(String spotifyId, String name, Set<Artist> artistSet) {
}
