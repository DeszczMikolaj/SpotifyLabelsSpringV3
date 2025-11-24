package spotify.spotifylabelsspringv3.external.oauth;

public record SpotifyAuthenticationResponse(boolean authenticated,
                                            String name,
                                            String email,
                                            String id, String avatarUrl) {
}
