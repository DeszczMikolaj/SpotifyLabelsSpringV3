package spotify.spotifylabelsspringv3.api.label.dto.request;

import java.util.Set;

public record AddLabelToTracksRequest(Long labelId, Set<Long> tracksIds) {
}
