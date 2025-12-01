package spotify.spotifylabelsspringv3.api.track.dto.request;

import spotify.spotifylabelsspringv3.domain.artist.Artist;

import java.util.Set;

public record CreateTrackRequest(String spotifyId, String name, Set<Artist> artistSet) {
}
