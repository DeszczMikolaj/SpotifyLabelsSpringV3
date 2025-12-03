package spotify.spotifylabelsspringv3.external.spotify.dto;

// This wrapper record is required by Spotify's response structure
public record SpotifySavedTrackItem(SpotifyTrackDTO track) {
}
