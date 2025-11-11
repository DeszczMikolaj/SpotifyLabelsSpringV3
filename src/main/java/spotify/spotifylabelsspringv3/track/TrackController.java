package spotify.spotifylabelsspringv3.track;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tracks")
public class TrackController {

    private final TrackService trackService;


    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @PostMapping
    public ResponseEntity<CreateTrackResponse> create(@RequestBody CreateTrackRequest request) {
        Track track = trackService.createTrack(request.spotifyId(), request.name(), request.artistSet());
        return ResponseEntity.ok(new CreateTrackResponse(track.getId(), track.getName()));
    }
}
