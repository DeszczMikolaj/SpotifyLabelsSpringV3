package spotify.spotifylabelsspringv3.models;

import lombok.Data;
import spotify.spotifylabelsspringv3.track.Artist;

import java.util.List;

@Data
public class SpotifyTrack {
    private String uri;               // Track's Spotify ID
    private String name;             // Track's name (song name)
    private List<Artist> artists;
}