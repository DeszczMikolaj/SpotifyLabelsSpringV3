package spotify.spotifylabelsspringv3.domain.label;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spotify.spotifylabelsspringv3.domain.user.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LabelRepository extends JpaRepository<Label, Long> {
    public List<Label> findByUserId(Long userId);

    @Query("""
            FROM Label l \s
            WHERE l.id = :labelId AND l.user = :user\s
            """)
    Optional<Label> findByLabelIdAndUserId(@Param("labelId") Long labelId, @Param("user") User user);
}
