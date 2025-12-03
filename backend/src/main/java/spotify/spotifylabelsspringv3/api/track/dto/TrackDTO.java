package spotify.spotifylabelsspringv3.api.track.dto;

import spotify.spotifylabelsspringv3.domain.artist.Artist;
import spotify.spotifylabelsspringv3.external.spotify.dto.SpotifyAlbumDTO;

import java.util.List;
import java.util.Set;

public record TrackDTO(String id, String uri, String name, String artists, String duration, String albumName, String albumImageUrl, Set<Long> labelsIds) {
}
