package spotify.spotifylabelsspringv3.api.playlist.dto.request;

import spotify.spotifylabelsspringv3.domain.label.LabelMode;

import java.util.Set;

public record NewPlaylistWithTracksRequest(Set<Long> labelIds, String playlistName, LabelMode labelMode){
}
