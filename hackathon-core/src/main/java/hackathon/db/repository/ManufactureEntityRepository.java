package hackathon.db.repository;

import hackathon.db.model.ManufactureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author egorov
 * @since 13.11.2021
 */
public interface ManufactureEntityRepository extends JpaRepository<ManufactureEntity, Long> {

    List<ManufactureEntity> findByNorthIsNullOrWestIsNull();
}
