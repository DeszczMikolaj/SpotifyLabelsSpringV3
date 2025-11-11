package spotify.spotifylabelsspringv3.track;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TrackService {
    private final TrackRepository trackRepository;


    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Transactional
    public Track createTrack(String spotifyId, String title, Set<Artist> artists) {
        Track track = new Track(spotifyId, title, artists);
        return trackRepository.save(track);
    }

    @Transactional()
    public Track getById(Long id) {
        return trackRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Track not found: " + id));
    }
}
