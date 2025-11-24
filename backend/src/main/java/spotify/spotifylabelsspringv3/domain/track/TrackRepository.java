package spotify.spotifylabelsspringv3.domain.track;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
    List<Track> findBySpotifyUri(String spotifyUri);

    @Query("""
            SELECT DISTINCT t.spotifyUri FROM Track t\s
            JOIN t.labels l\s
            WHERE l.id in :labelIds\s
            """)
    List<String> findUnionByLabelIds(@Param("labelIds") Set<Long> labelIds);

    @Query("""
            SELECT t.spotifyUri FROM Track t\s
            JOIN t.labels l\s
            WHERE l.id in :labelIds\s
            GROUP BY t.id\s
            HAVING COUNT(DISTINCT l.id) = :labelCount\s
            """)
    List<String> findIntersectionByLabelIds(@Param("labelIds") Set<Long> labelIds, @Param("labelCount") int labelCount);


}
