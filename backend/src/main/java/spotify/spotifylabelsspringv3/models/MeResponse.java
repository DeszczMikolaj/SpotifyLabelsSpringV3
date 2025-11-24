package spotify.spotifylabelsspringv3.models;

public record MeResponse(boolean authenticated,
                         String name,
                         String email,
                         String id) {
}
