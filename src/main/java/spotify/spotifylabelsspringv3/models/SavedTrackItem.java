package spotify.spotifylabelsspringv3.models;


import lombok.Data;
import spotify.spotifylabelsspringv3.track.Track;

@Data
public class SavedTrackItem {
    private SpotifyTrack track;
}