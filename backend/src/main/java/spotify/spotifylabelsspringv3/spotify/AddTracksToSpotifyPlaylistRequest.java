package spotify.spotifylabelsspringv3.spotify;

import java.util.List;

public record AddTracksToSpotifyPlaylistRequest(String playlistSpotifyId, List<String> tracksUris) {
}
