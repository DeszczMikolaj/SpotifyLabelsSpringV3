package spotify.spotifylabelsspringv3.models;

import java.util.Set;

public record NewPlaylistWithTracksRequest(Set<Long> labelIds, String playlistName, LabelMode labelMode){
}
