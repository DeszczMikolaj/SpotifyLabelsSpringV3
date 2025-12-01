package spotify.spotifylabelsspringv3.external.spotify;

import spotify.spotifylabelsspringv3.api.track.dto.TrackDTO;

// This wrapper record is required by Spotify's response structure
public record SavedTrackItem(TrackDTO track) {
}
