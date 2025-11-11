package spotify.spotifylabelsspringv3.label;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import spotify.spotifylabelsspringv3.track.Track;
import spotify.spotifylabelsspringv3.track.TrackService;

@Service
public class LabelService {

    private final LabelRepository labelRepository;
    private final TrackService trackService;


    public LabelService(LabelRepository labelRepository, TrackService trackService) {
        this.labelRepository = labelRepository;
        this.trackService = trackService;
    }

    @Transactional
    public Label createLabel(String name) {
        Label label = new Label(name);
        return labelRepository.save(label);
    }

    @Transactional
    public void addTrackToLabel(Long labelId, Long trackId) {
        Label label = labelRepository.findById(labelId).orElseThrow(() -> new IllegalArgumentException("Label not found: " + labelId));
        Track track = trackService.getById(trackId);

        label.addTrack(track);
    }

    @Transactional
    public Label getById(Long id) {
        return labelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Label not found: " + id));
    }
}
