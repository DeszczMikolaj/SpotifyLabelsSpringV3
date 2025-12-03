package spotify.spotifylabelsspringv3.external.spotify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import spotify.spotifylabelsspringv3.domain.artist.Artist;

import java.util.Set;

public record SpotifyTrackDTO(String id, String uri, String name, Set<Artist> artists, @JsonProperty("duration_ms") Integer durationMs, SpotifyAlbumDTO album){
}