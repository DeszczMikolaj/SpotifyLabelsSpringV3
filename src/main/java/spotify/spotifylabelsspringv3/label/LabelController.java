package spotify.spotifylabelsspringv3.label;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spotify.spotifylabelsspringv3.track.CreateTrackResponse;
import spotify.spotifylabelsspringv3.track.Track;
import spotify.spotifylabelsspringv3.track.TrackService;

@RestController
@RequestMapping("/labels")
public class LabelController {

    private final LabelService labelService;


    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @PostMapping
    public ResponseEntity<CreateLabelResponse> create(@RequestBody CreateLabelRequest request) {
        Label label = labelService.createLabel(request.name());
        return ResponseEntity.ok(new CreateLabelResponse(label.getId(), label.getName()));
    }

    @GetMapping
    public String test() {
        return "Test";
    }
}
