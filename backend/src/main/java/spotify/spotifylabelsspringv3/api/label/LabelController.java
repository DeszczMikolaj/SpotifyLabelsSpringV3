package spotify.spotifylabelsspringv3.api.label;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public List<LabelDTO> getAllLabels() {
        return labelService.findAll();
    }

    @PostMapping
    public ResponseEntity<LabelDTO> create(@RequestBody CreateLabelRequest request) {
        LabelDTO label = labelService.createLabel(request.name());
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
