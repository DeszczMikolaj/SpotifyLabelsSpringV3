package spotify.spotifylabelsspringv3.api.track;

import org.springframework.web.bind.annotation.*;
import spotify.spotifylabelsspringv3.domain.track.TrackService;

@RestController
@RequestMapping("/tracks")
public class TrackController {

    private final TrackService trackService;


    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

//    @GetMapping
//    public Set<TrackDTO> getAllTracks() {
//        return trackService.findAllTracks();
//    }


//    @PostMapping
//    public ResponseEntity<CreateTrackResponse> create(@RequestBody CreateTrackRequest request) {
//        Track track = trackService.createTrack(request.spotifyId());
//        return ResponseEntity.ok(new CreateTrackResponse(track.getId()));
//    }
}
