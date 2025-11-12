package spotify.spotifylabelsspringv3.label;

import java.util.Set;

public record AddLabelToTracksRequest(Long labelId, Set<Long> tracksIds) {
}
