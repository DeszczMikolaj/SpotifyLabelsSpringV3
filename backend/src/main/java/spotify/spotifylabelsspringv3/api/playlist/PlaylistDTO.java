package spotify.spotifylabelsspringv3.api.playlist;


import spotify.spotifylabelsspringv3.api.track.TrackDTO;

import java.util.Set;

public record PlaylistDTO(String name, String imageUrl, Set<TrackDTO> tracks, int tracksCount)  {}
