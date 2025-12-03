package spotify.spotifylabelsspringv3.external.spotify.dto;

import java.util.List;

public record SpotifyAlbumDTO(String name, List<SpotifyImageDTO> images) {
}
