package hackathon.db.repository;

import hackathon.db.model.ManufactureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author egorov
 * @since 13.11.2021
 */
public interface ManufactureEntityRepository extends JpaRepository<ManufactureEntity, Long> {

    @Query(
            value = "SELECT * from MANUFACTURE ma WHERE "+
                    "ma.activity in (SELECT a.name FROM ACTIVITY a) and " +
                    "ma.INN NOT IN (SELECT l.INN FROM LICENSE l)",
            nativeQuery = true)
    List<ManufactureEntity> findByFilter();

}
