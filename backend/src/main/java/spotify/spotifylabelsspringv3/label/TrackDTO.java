package spotify.spotifylabelsspringv3.label;

import spotify.spotifylabelsspringv3.track.Artist;

import java.util.Set;

public record TrackDTO(String trackUri, String name, Set<Artist> artists) {
}
