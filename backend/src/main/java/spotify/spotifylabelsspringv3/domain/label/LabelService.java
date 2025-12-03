package spotify.spotifylabelsspringv3.domain.label;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import spotify.spotifylabelsspringv3.api.label.dto.LabelDTO;
import spotify.spotifylabelsspringv3.external.spotify.dto.SpotifyTrackDTO;
import spotify.spotifylabelsspringv3.domain.track.Track;
import spotify.spotifylabelsspringv3.domain.track.TrackService;
import spotify.spotifylabelsspringv3.domain.user.User;
import spotify.spotifylabelsspringv3.domain.user.UserService;
import spotify.spotifylabelsspringv3.external.spotify.SpotifyService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LabelService {

    private final LabelRepository labelRepository;
    private final TrackService trackService;
    private final UserService userService;
    private final SpotifyService spotifyService;


    public LabelService(LabelRepository labelRepository, TrackService trackService, UserService userService, SpotifyService spotifyService) {
        this.labelRepository = labelRepository;
        this.trackService = trackService;
        this.userService = userService;
        this.spotifyService = spotifyService;
    }

    @Transactional
    public LabelDTO createLabel(String name, String colorHex, String spotifyId) {
        User user = userService.getUser(spotifyId);
        Label label = new Label(name, colorHex, user);
        labelRepository.save(label);
        return new LabelDTO(label.getId(), label.getName(), label.getColorHex(), label.getTracks().size());
    }

    @Transactional
    public void addLabelToTracks(Long labelId, Set<Long> tracksIds, String spotifyId) {
        User user = userService.getUser(spotifyId);
        Label label = labelRepository.findByLabelIdAndUserId(labelId, user).orElseThrow(() -> new IllegalArgumentException("Label not found: " + labelId));

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

    public List<SpotifyTrackDTO> findAllTrackForLabel(String accessToken, Long labelId) {
        Label label = labelRepository.findById(labelId).orElseThrow(() -> new IllegalArgumentException("Label not found: " + labelId));
        Set<Track> tracks = label.getTracks();
        Set<String> tracksIds = tracks.stream().map(Track::getSpotifyId).collect(Collectors.toSet());
        return spotifyService.getTracksDetails(accessToken, tracksIds);
    }
}
