package spotify.spotifylabelsspringv3.external.spotify;

import lombok.Data;
import spotify.spotifylabelsspringv3.api.track.TrackDTO;

import java.util.List;

@Data
public class SavedTracksSpotifyResponse {
    private List<TrackDTO> items;
    private String next;
}