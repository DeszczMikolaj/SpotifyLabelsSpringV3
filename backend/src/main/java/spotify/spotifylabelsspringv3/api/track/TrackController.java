package spotify.spotifylabelsspringv3.api.track;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spotify.spotifylabelsspringv3.api.track.dto.TrackDTO;
import spotify.spotifylabelsspringv3.api.track.dto.request.CreateTrackRequest;
import spotify.spotifylabelsspringv3.api.track.dto.response.CreateTrackResponse;
import spotify.spotifylabelsspringv3.domain.track.Track;
import spotify.spotifylabelsspringv3.domain.track.TrackService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/tracks")
public class TrackController {

    private final TrackService trackService;


    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping
    public Set<TrackDTO> getAllTracks() {
        return trackService.findAllTracks();
    }


    @PostMapping
    public ResponseEntity<CreateTrackResponse> create(@RequestBody CreateTrackRequest request) {
        Track track = trackService.createTrack(request.spotifyId(), request.name(), request.artistSet());
        return ResponseEntity.ok(new CreateTrackResponse(track.getId(), track.getName()));
    }
}
