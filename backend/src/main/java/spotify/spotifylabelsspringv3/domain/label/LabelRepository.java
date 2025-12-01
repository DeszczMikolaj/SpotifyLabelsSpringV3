package spotify.spotifylabelsspringv3.domain.label;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabelRepository extends JpaRepository<Label, Long> {
    public List<Label> findByUserId(Long userId);
}
