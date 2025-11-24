package spotify.spotifylabelsspringv3.domain.label;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import spotify.spotifylabelsspringv3.api.label.LabelDTO;
import spotify.spotifylabelsspringv3.domain.track.Track;
import spotify.spotifylabelsspringv3.domain.track.TrackService;

import java.util.List;
import java.util.Set;

@Service
public class LabelService {

    private final LabelRepository labelRepository;
    private final TrackService trackService;


    public LabelService(LabelRepository labelRepository, TrackService trackService) {
        this.labelRepository = labelRepository;
        this.trackService = trackService;
    }

    @Transactional
    public LabelDTO createLabel(String name) {
        Label label = new Label(name);
        labelRepository.save(label);
        return new LabelDTO(label.getId(), label.getName(), label.getColorHex(), label.getTracks().size());
    }

    @Transactional
    public void addLabelToTracks(Long labelId, Set<Long> tracksIds) {
        Label label = labelRepository.findById(labelId).orElseThrow(() -> new IllegalArgumentException("Label not found: " + labelId));

        tracksIds.forEach(trackId-> {
            Track track = trackService.getById(trackId);

            label.addTrack(track);
        });
    }

    @Transactional
    public Label getById(Long id) {
        return labelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Label not found: " + id));
    }

    @Transactional
    public List<LabelDTO> findAll() {
        List<Label>  labels = labelRepository.findAll();
        return labels.stream().map(label -> new LabelDTO(label.getId(), label.getName(), label.getColorHex(), label.getTracks().size())).toList();
    }

    public List<trackDTO> findAllTrackForLabel(Long labelId) {
        Label label = labelRepository.findById(labelId).orElseThrow(() -> new IllegalArgumentException("Label not found: " + labelId));
        Set<Track> tracks = label.getTracks();
        return tracks.stream().map(track -> new trackDTO(track.getSpotifyUri(), track.getName(), track.getArtists())).toList();
    }
}
