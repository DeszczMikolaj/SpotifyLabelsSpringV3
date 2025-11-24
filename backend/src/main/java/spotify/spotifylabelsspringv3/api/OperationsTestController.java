package spotify.spotifylabelsspringv3.api;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import spotify.spotifylabelsspringv3.api.playlist.NewPlaylistWithTracksRequest;
import spotify.spotifylabelsspringv3.domain.label.LabelService;
import trackDTO;
import spotify.spotifylabelsspringv3.domain.label.LabelMode;
import spotify.spotifylabelsspringv3.domain.playlist.SpotifyPlaylist;
import spotify.spotifylabelsspringv3.external.spotify.SpotifyService;
import spotify.spotifylabelsspringv3.domain.track.TrackService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class OperationsTestController {

    private final LabelService labelService;
    private final TrackService trackService;
    private final SpotifyService spotifyService;


    public OperationsTestController(LabelService labelService, TrackService trackService, SpotifyService spotifyService) {
        this.labelService = labelService;
        this.trackService = trackService;
        this.spotifyService = spotifyService;
    }


    @PostMapping("operation/createPlaylistWithLabeledTracks")
    public void createPlaylistWithLabeledTracks (@RegisteredOAuth2AuthorizedClient("spotify") OAuth2AuthorizedClient auth2AuthorizedClient, @RequestBody NewPlaylistWithTracksRequest request){
        Set<Long> labelsIds = request.labelIds();
        List<String> trackUris = new ArrayList<>();
        if(request.labelMode() == LabelMode.UNION) {
            trackUris = trackService.findUrisUnionByLabelsIds(labelsIds);
        }
        else if(request.labelMode() == LabelMode.INTERSECTION) {
            trackUris = trackService.findUrisIntersectionByLabelsIds(labelsIds);
        }

        SpotifyPlaylist spotifyPlaylist = spotifyService.createPlaylist(auth2AuthorizedClient.getPrincipalName(), auth2AuthorizedClient.getAccessToken().getTokenValue(), request.playlistName());
        spotifyService.addTracksToPlaylist(auth2AuthorizedClient.getAccessToken().getTokenValue(), spotifyPlaylist.id(),trackUris);
    }
}
