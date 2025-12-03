package spotify.spotifylabelsspringv3.external.spotify.dto;

import lombok.Data;

import java.util.List;

@Data
public class SavedTracksSpotifyResponse {
    private List<SpotifySavedTrackItem> items;
    private String next;
}