package spotify.spotifylabelsspringv3.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spotify.spotifylabelsspringv3.models.MeResponse;

import java.util.Map;

@RestController
public class UserController {

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


        return ResponseEntity.ok(
                new MeResponse(
                        true,
                        name ,
                        email,
                        id
        ));
    }
}
