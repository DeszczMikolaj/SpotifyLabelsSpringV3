package spotify.spotifylabelsspringv3.external.spotify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import spotify.spotifylabelsspringv3.api.track.TrackDTO;
import spotify.spotifylabelsspringv3.domain.playlist.SpotifyPlaylist;

import java.util.*;

@Service
public class SpotifyService {

    private final WebClient spotifyWebClient;

    @Autowired
    public SpotifyService(WebClient.Builder webClientBuilder) {
        this.spotifyWebClient = webClientBuilder.baseUrl("https://api.spotify.com").build();
    }

    public Set<TrackDTO> getLikedSongs(String accessToken) {
        String nextUrl = "v1/me/tracks?limit=50";
        Set<TrackDTO> allSavedTracks = new HashSet<>();

        while (nextUrl != null) {
            SavedTracksSpotifyResponse response = spotifyWebClient
                    .get()
                    .uri(nextUrl)
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(SavedTracksSpotifyResponse.class)
                    .block(); // Synchronously get the result (stringified JSON)

            if (response != null && response.getItems() != null) {
                allSavedTracks.addAll(response.getItems());
            }

            nextUrl = response.getNext();
        }
        return allSavedTracks;
    }

    public SpotifyPlaylist createPlaylist(String userId, String accessToken, String playlistName) {

        Map<String, String> playlistNameBody = Map.of("name", playlistName);

        SpotifyPlaylist response = spotifyWebClient.post().uri(uri -> uri.path("v1/users/{user_id}/playlists").build(userId))
                .header("Authorization", "Bearer " + accessToken)
                .bodyValue(playlistNameBody)
                .retrieve().bodyToMono(SpotifyPlaylist.class).block();

        return response;
    }

    public String addTracksToPlaylist(String accessToken, String playlistId, List<String> trackUris) {

        Map<Object, Object> requestBody = Map.of("uris", trackUris, "position", 0);

        String response = spotifyWebClient.post().uri(uri -> uri.path("v1/playlists/{playlist_id}/tracks").build(playlistId))
                .header("Authorization", "Bearer " + accessToken)
                .bodyValue(requestBody)
                .retrieve().bodyToMono(String.class).block();

        return response;
    }
}
