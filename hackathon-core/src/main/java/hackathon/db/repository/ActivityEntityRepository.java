package hackathon.db.repository;

import hackathon.db.model.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author egorov
 * @since 13.11.2021
 */
public interface ActivityEntityRepository extends JpaRepository<ActivityEntity, Long> {
}
