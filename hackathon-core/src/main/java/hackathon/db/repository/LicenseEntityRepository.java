package hackathon.db.repository;

import hackathon.db.model.LicenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author egorov
 * @since 13.11.2021
 */
public interface LicenseEntityRepository extends JpaRepository<LicenseEntity, Long> {
}
