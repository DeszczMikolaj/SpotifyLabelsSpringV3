package spotify.spotifylabelsspringv3.api.label;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import spotify.spotifylabelsspringv3.api.label.dto.LabelDTO;
import spotify.spotifylabelsspringv3.api.label.dto.request.AddLabelToTracksRequest;
import spotify.spotifylabelsspringv3.api.label.dto.request.CreateLabelRequest;
import spotify.spotifylabelsspringv3.api.track.dto.TrackDTO;
import spotify.spotifylabelsspringv3.domain.label.LabelService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/labels")
public class LabelController {

    private final LabelService labelService;


    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping
    public List<LabelDTO> getAllLabels( @AuthenticationPrincipal OAuth2User principal) {
        return labelService.findAllForUser(principal.getAttribute("id"));
    }

    @PostMapping
    public ResponseEntity<LabelDTO> create(@RequestBody CreateLabelRequest request, @AuthenticationPrincipal OAuth2User principal) {
        String spotifyUserId = principal.getAttribute("id");
        LabelDTO label = labelService.createLabel(request.name(), request.colorHex(), spotifyUserId);
        return ResponseEntity.created(URI.create("/labels/" + label.id())).body(label);
    }

    @GetMapping("/labelTracks/{labelId}")
    public List<TrackDTO> getLabeledTracks (@PathVariable Long labelId) {
        return labelService.findAllTrackForLabel(labelId);
    }

    @PostMapping("/labelTracks")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void labelTracks(@RequestBody AddLabelToTracksRequest request) {
        labelService.addLabelToTracks(request.labelId(), request.tracksIds());
    }

}
