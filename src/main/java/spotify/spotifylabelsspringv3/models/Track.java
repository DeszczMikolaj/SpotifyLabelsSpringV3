package spotify.spotifylabelsspringv3.models;

import lombok.Data;

import java.util.List;

@Data
public class Track {
    private String id;               // Track's Spotify ID
    private String name;             // Track's name (song name)
    private List<Artist> artists;
}