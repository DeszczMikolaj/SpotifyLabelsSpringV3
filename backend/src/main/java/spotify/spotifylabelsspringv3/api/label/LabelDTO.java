package spotify.spotifylabelsspringv3.api.label;

public record LabelDTO(Long id, String name, String colorHex, int assignedTracks) {
}
