package spotify.spotifylabelsspringv3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import spotify.spotifylabelsspringv3.models.SavedTrackItem;
import spotify.spotifylabelsspringv3.models.SavedTracksResponse;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SpotifyLikedSongController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/mySavedTracks")
    public List<SavedTrackItem> getSavedTracks(
            @RegisteredOAuth2AuthorizedClient("spotify") OAuth2AuthorizedClient authorizedClient) {

        String accessToken = authorizedClient.getAccessToken().getTokenValue();

        String nextUrl = "https://api.spotify.com/v1/me/tracks?limit=50";
        List<SavedTrackItem> allSavedTracks = new ArrayList<>();

        while (nextUrl != null) {
            SavedTracksResponse response = webClientBuilder.build()
                    .get()
                    .uri(nextUrl)
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(SavedTracksResponse.class)
                    .block(); // Synchronously get the result (stringified JSON)

            if (response != null && response.getItems() != null) {
                allSavedTracks.addAll(response.getItems());
            }

            nextUrl = response.getNext();
        }




        // You can return it to front-end, or parse it further as needed
        return allSavedTracks;
    }
}
