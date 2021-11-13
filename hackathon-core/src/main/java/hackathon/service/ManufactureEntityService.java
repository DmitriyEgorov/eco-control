package hackathon.service;

import hackathon.db.model.ManufactureEntity;
import hackathon.db.model.TestDataEntity;
import hackathon.db.repository.ManufactureEntityRepository;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;

/**
 * @author egorov
 * @since 03.06.2019
 */
@Service
public class ManufactureEntityService {

    private final ManufactureEntityRepository manufactureEntityRepository;

    public ManufactureEntityService(ManufactureEntityRepository manufactureEntityRepository) {
        this.manufactureEntityRepository = manufactureEntityRepository;
    }

    public List<ManufactureEntity> findByFilter() {
        return manufactureEntityRepository.findAll();
    }

}
