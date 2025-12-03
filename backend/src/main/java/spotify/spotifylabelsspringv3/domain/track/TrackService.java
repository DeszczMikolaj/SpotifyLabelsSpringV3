package spotify.spotifylabelsspringv3.domain.track;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import spotify.spotifylabelsspringv3.api.label.dto.LabelDTO;
import spotify.spotifylabelsspringv3.domain.label.Label;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TrackService {
    private final TrackRepository trackRepository;


    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Transactional
    public Track createTrack(String spotifyId, String uri) {
        Track track = new Track(spotifyId, uri);
        return trackRepository.save(track);
    }

    @Transactional()
    public Track getById(Long id) {
        return trackRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Track not found: " + id));
    }

//    public Set<TrackDTO> findAllTracks() {
//        return trackRepository.findAll().stream().map(track -> new  TrackDTO(track.getSpotifyId()).collect(Collectors.toSet());
//    }

    public List<String> findUrisUnionByLabelsIds(Set<Long> labelsIds){
        return trackRepository.findUrisUnionByLabelIds(labelsIds);
    }

    public List<String> findUrisIntersectionByLabelsIds(Set<Long> labelsIds){
        return trackRepository.findUrisIntersectionByLabelIds(labelsIds, labelsIds.size());
    }

    public Set<LabelDTO> getTrackLabels(String spotifyTrackId) {
        Optional<Track> trackResult = trackRepository.findBySpotifyId(spotifyTrackId);
        return trackResult.map(track -> track.getLabels().stream().map(label ->
                        new LabelDTO(label.getId(), label.getName(), label.getColorHex(), label.getTracks().size()))
                .collect(Collectors.toSet())).orElseGet(Set::of);
    }
}
