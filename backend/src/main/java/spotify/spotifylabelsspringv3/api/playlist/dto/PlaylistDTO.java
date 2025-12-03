package spotify.spotifylabelsspringv3.api.playlist.dto;


import spotify.spotifylabelsspringv3.api.track.dto.TrackDTO;
import java.util.Set;

public record PlaylistDTO(String name, String imageUrl, Set<TrackDTO> tracks, int tracksCount)  {}
