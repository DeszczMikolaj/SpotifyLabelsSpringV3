package spotify.spotifylabelsspringv3.domain.label;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import spotify.spotifylabelsspringv3.api.label.dto.LabelDTO;
import spotify.spotifylabelsspringv3.api.track.dto.TrackDTO;
import spotify.spotifylabelsspringv3.domain.track.Track;
import spotify.spotifylabelsspringv3.domain.track.TrackService;
import spotify.spotifylabelsspringv3.domain.user.User;
import spotify.spotifylabelsspringv3.domain.user.UserService;

import java.util.List;
import java.util.Set;

@Service
public class LabelService {

    private final LabelRepository labelRepository;
    private final TrackService trackService;
    private final UserService userService;


    public LabelService(LabelRepository labelRepository, TrackService trackService, UserService userService) {
        this.labelRepository = labelRepository;
        this.trackService = trackService;
        this.userService = userService;
    }

    @Transactional
    public LabelDTO createLabel(String name, String spotifyId) {
        User user = userService.getUser(spotifyId);
        Label label = new Label(name, user);
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
    public List<LabelDTO> findAllForUser(String spotifyId) {
        User user = userService.getUser(spotifyId);
        List<Label>  labels = labelRepository.findByUserId(user.getId());
        return labels.stream().map(label -> new LabelDTO(label.getId(), label.getName(), label.getColorHex(), label.getTracks().size())).toList();
    }

    public List<TrackDTO> findAllTrackForLabel(Long labelId) {
        Label label = labelRepository.findById(labelId).orElseThrow(() -> new IllegalArgumentException("Label not found: " + labelId));
        Set<Track> tracks = label.getTracks();
        return tracks.stream().map(track -> new TrackDTO(track.getSpotifyUri(), track.getName(), track.getArtists())).toList();
    }
}
