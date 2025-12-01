package spotify.spotifylabelsspringv3.api.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spotify.spotifylabelsspringv3.domain.user.UserService;
import spotify.spotifylabelsspringv3.external.spotify.oauth.SpotifyAuthenticationResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            // not logged in
            return ResponseEntity.ok(Map.of(
                    "authenticated", false
            ));
        }

        String name  = principal.getAttribute("display_name");
        String email = principal.getAttribute("email");
        String id    = principal.getAttribute("id");
        List<HashMap<String, Object >> imageList = principal.getAttribute("images");
        var avatarUrl = "";
        if(imageList  != null) {
            avatarUrl = imageList.get(0).get("url").toString();

        }

        userService.registerUserIfRequeried(id);

        return ResponseEntity.ok(
                new SpotifyAuthenticationResponse(
                        true,
                        name ,
                        email,
                        id,
                        avatarUrl
                ));
    }
}
