package spotify.spotifylabelsspringv3.external.spotify;

import lombok.Data;
import spotify.spotifylabelsspringv3.api.track.dto.TrackDTO;

import java.util.List;

@Data
public class SavedTracksSpotifyResponse {
    private List<SavedTrackItem> items;
    private String next;
}