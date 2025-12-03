package spotify.spotifylabelsspringv3.api.track;

import spotify.spotifylabelsspringv3.api.track.dto.TrackDTO;
import spotify.spotifylabelsspringv3.domain.artist.Artist;
import spotify.spotifylabelsspringv3.external.spotify.dto.SpotifyTrackDTO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TrackPresentationMapper {

    public static TrackDTO fromSpotify(SpotifyTrackDTO spotifyTrackDTO, Set<Long> labelIds) {
        String albumUrl = spotifyTrackDTO.album().images().get(0).url();
        String duration = constructDuration(spotifyTrackDTO.durationMs());
        String artists = constructArtists(spotifyTrackDTO.artists());
        return new TrackDTO(spotifyTrackDTO.id(), spotifyTrackDTO.uri(), spotifyTrackDTO.name(), artists, duration, spotifyTrackDTO.album().name(), albumUrl ,labelIds);
    }

    private static String constructArtists(Set<Artist> artists) {
        if (artists == null || artists.isEmpty()) return null;
        return artists.stream().map(Artist::name).collect(Collectors.joining(","));
    }

    private static String constructDuration(Integer durationInMilliseconds) {
        int durationInSeconds = durationInMilliseconds / 1000;
        int minutesPart = durationInSeconds / 60;
        int secondsPart = durationInSeconds % 60;
        String secondPartString = secondsPart < 10 ? "0" + secondsPart : String.valueOf(secondsPart);
        return minutesPart + ":" + secondPartString;
    }

}
