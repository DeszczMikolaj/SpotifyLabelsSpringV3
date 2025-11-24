package spotify.spotifylabelsspringv3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import spotify.spotifylabelsspringv3.models.SavedTrackItem;
import spotify.spotifylabelsspringv3.models.SpotifyPlaylist;
import spotify.spotifylabelsspringv3.spotify.AddTracksToSpotifyPlaylistRequest;
import spotify.spotifylabelsspringv3.spotify.CreateSpotifyPlaylistRequest;
import spotify.spotifylabelsspringv3.spotify.SpotifyService;
import spotify.spotifylabelsspringv3.track.TrackService;

import java.util.HashSet;
import java.util.Set;

@RestController
public class SpotifyController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    private final TrackService trackService;

    private final SpotifyService spotifyService;

    public SpotifyController(TrackService trackService, SpotifyService spotifyService) {
        this.trackService = trackService;
        this.spotifyService = spotifyService;
    }

    @GetMapping("/mySavedTracks")
    public Set<SavedTrackItem> exportSpotifySavedTracksToDatabase(
            @RegisteredOAuth2AuthorizedClient("spotify") OAuth2AuthorizedClient authorizedClient) {

        Set<SavedTrackItem> likedSpotifyTracks = spotifyService.getLikedSongs(authorizedClient.getAccessToken().getTokenValue());

        likedSpotifyTracks.forEach(savedTrackItem -> trackService.createTrack(savedTrackItem.getTrack().getUri(),
                savedTrackItem.getTrack().getName(), new HashSet<>(savedTrackItem.getTrack().getArtists()))
        );

        // You can return it to front-end, or parse it further as needed
        return likedSpotifyTracks;
    }

    @PostMapping("/createNewPlaylist")
    public SpotifyPlaylist newPlaylist(@RegisteredOAuth2AuthorizedClient("spotify") OAuth2AuthorizedClient authorizedClient, @RequestBody CreateSpotifyPlaylistRequest request) {
       return  spotifyService.createPlaylist(authorizedClient.getPrincipalName(), authorizedClient.getAccessToken().getTokenValue(), request.name());
    }

    @PostMapping("/addTracksToPlaylist")
    public String addTracksToPlaylist(@RegisteredOAuth2AuthorizedClient("spotify") OAuth2AuthorizedClient authorizedClient, @RequestBody AddTracksToSpotifyPlaylistRequest request) {
        return spotifyService.addTracksToPlaylist(authorizedClient.getAccessToken().getTokenValue(), request.playlistSpotifyId(), request.tracksUris());
    }
}
