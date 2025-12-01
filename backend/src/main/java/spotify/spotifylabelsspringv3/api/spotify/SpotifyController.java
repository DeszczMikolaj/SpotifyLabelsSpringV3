package spotify.spotifylabelsspringv3.api.spotify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import spotify.spotifylabelsspringv3.api.spotify.dto.request.AddTracksToSpotifyPlaylistRequest;
import spotify.spotifylabelsspringv3.api.spotify.dto.request.CreateSpotifyPlaylistRequest;
import spotify.spotifylabelsspringv3.api.track.dto.TrackDTO;
import spotify.spotifylabelsspringv3.api.playlist.dto.PlaylistDTO;
import spotify.spotifylabelsspringv3.domain.playlist.SpotifyPlaylist;
import spotify.spotifylabelsspringv3.external.spotify.SpotifyService;
import spotify.spotifylabelsspringv3.domain.track.TrackService;

import java.util.Set;

@RestController
@RequestMapping("api/spotify")
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
    public PlaylistDTO exportSpotifySavedTracksToDatabase(
            @RegisteredOAuth2AuthorizedClient("spotify") OAuth2AuthorizedClient authorizedClient) {

        Set<TrackDTO> likedSpotifyTracks = spotifyService.getLikedSongs(authorizedClient.getAccessToken().getTokenValue());
        PlaylistDTO likedTracksPlaylist = new PlaylistDTO("Polubione utwory", "https://misc.scdn.co/liked-songs/liked-songs-300.jpg", likedSpotifyTracks, likedSpotifyTracks.size());
        // You can return it to front-end, or parse it further as needed
        return likedTracksPlaylist;
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
