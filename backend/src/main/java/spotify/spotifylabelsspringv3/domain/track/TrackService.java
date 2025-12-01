package spotify.spotifylabelsspringv3.domain.track;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import spotify.spotifylabelsspringv3.api.track.dto.TrackDTO;
import spotify.spotifylabelsspringv3.domain.artist.Artist;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TrackService {
    private final TrackRepository trackRepository;


    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Transactional
    public Track createTrack(String uri, String title, Set<Artist> artists) {
        Track track = new Track(uri, title, artists);
        return trackRepository.save(track);
    }

    @Transactional()
    public Track getById(Long id) {
        return trackRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Track not found: " + id));
    }

    public Set<TrackDTO> findAllTracks() {
        return trackRepository.findAll().stream().map(track -> new TrackDTO(track.getSpotifyUri(), track.getName(),track.getArtists())).collect(Collectors.toSet());
    }

    public List<String> findUrisUnionByLabelsIds(Set<Long> labelsIds){
        return trackRepository.findUnionByLabelIds(labelsIds);
    }

    public List<String> findUrisIntersectionByLabelsIds(Set<Long> labelsIds){
        return trackRepository.findIntersectionByLabelIds(labelsIds, labelsIds.size());
    }
}
