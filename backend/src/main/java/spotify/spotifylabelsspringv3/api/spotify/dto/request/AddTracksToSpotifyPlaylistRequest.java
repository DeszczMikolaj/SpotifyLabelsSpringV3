package spotify.spotifylabelsspringv3.api.spotify.dto.request;

import java.util.List;

public record AddTracksToSpotifyPlaylistRequest(String playlistSpotifyId, List<String> tracksUris) {
}
