package spotify.spotifylabelsspringv3.external.spotify;

import lombok.Data;
import spotify.spotifylabelsspringv3.external.spotify.dto.SpotifyTrackDTO;

import java.util.List;

@Data
public class GetTracksSpotifyResponse {
    private List<SpotifyTrackDTO> items;
}