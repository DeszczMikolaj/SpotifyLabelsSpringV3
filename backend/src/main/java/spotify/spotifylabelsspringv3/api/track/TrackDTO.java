package spotify.spotifylabelsspringv3.api.track;

import spotify.spotifylabelsspringv3.domain.artist.Artist;

import java.util.List;

public record TrackDTO (String uri, String name, List<Artist>artists){
}