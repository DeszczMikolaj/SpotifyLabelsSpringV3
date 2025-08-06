package spotify.spotifylabelsspringv3.models;

import lombok.Data;

import java.util.List;

@Data
public class SavedTracksResponse {
    private List<SavedTrackItem> items;
    private String next;
}