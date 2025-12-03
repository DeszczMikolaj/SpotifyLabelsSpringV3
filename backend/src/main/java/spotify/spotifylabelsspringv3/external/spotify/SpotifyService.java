package spotify.spotifylabelsspringv3.external.spotify;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import spotify.spotifylabelsspringv3.external.spotify.dto.SpotifyTrackDTO;
import spotify.spotifylabelsspringv3.domain.playlist.SpotifyPlaylist;
import spotify.spotifylabelsspringv3.external.spotify.dto.SpotifySavedTrackItem;
import spotify.spotifylabelsspringv3.external.spotify.dto.SavedTracksSpotifyResponse;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SpotifyService {

    private final WebClient spotifyWebClient;

    @Autowired
    public SpotifyService(WebClient.Builder webClientBuilder) {
        this.spotifyWebClient = webClientBuilder.baseUrl("https://api.spotify.com").build();
    }

    public Set<SpotifyTrackDTO> getLikedSongs(String accessToken) {
        String nextUrl = "v1/me/tracks?limit=50";
        Set<SpotifyTrackDTO> allSavedTracks = new HashSet<>();

        while (nextUrl != null) {
            SavedTracksSpotifyResponse response = spotifyWebClient
                    .get()
                    .uri(nextUrl)
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(SavedTracksSpotifyResponse.class)
                    .block(); // Synchronously get the result (stringified JSON)

            if (response != null && response.getItems() != null) {
                allSavedTracks.addAll(response.getItems().stream().map(SpotifySavedTrackItem::track).collect(Collectors.toSet()));
            }

            nextUrl = response.getNext();
        }
        return allSavedTracks;
    }

    public List<SpotifyTrackDTO> getTracksDetails(String accessToken, Set<String> trackIds) {
       Set<Set<String>> partitionedTracksIds = Lists.partition(new ArrayList<>(trackIds), 50).stream().map(HashSet::new).collect(Collectors.toSet());
       List<SpotifyTrackDTO> tracks = new ArrayList<>();

       partitionedTracksIds.forEach(trackIdsSets -> {
           String trackIdsAsParameter = String.join(",", trackIdsSets);
           GetTracksSpotifyResponse response = spotifyWebClient
                   .get()
                   .uri(uriBuilder ->
                           uriBuilder.path("v1/tracks")
                                   .queryParam("ids", trackIdsAsParameter)
                                   .build()
                   )
                   .header("Authorization", "Bearer " + accessToken)
                   .retrieve()
                   .bodyToMono(GetTracksSpotifyResponse.class)
                   .block(); // Synchronously get the result (stringified JSON)

           if (response != null && response.getItems() != null) {
               tracks.addAll(response.getItems());
           }
       });
       return tracks;
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
