package spotify.spotifylabelsspringv3.label;

import spotify.spotifylabelsspringv3.track.Artist;

import java.util.Set;

public record TrackDTO(Long trackId, String name, Set<Artist> artists) {
}
