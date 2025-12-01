package spotify.spotifylabelsspringv3.api.track.dto;

import spotify.spotifylabelsspringv3.domain.artist.Artist;

import java.util.Set;

public record TrackDTO (String uri, String name, Set<Artist> artists){
}