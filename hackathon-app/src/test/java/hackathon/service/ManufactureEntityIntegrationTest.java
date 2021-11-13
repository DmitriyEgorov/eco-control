package hackathon.service;

import app.config.TestHackathonApplication;
import hackathon.db.model.ManufactureEntity;
import hackathon.db.repository.ManufactureEntityRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Dmitriy
 * @since 13.11.2021
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestHackathonApplication.class)
public class ManufactureEntityIntegrationTest {

    @Autowired
    private ManufactureEntityRepository manufactureEntityRepository;

    @Autowired
    private ManufactureEntityService manufactureEntityService;

    private static final String INN_INCLUDE_1 = "INN_INCLUDE_1";
    private static final String ADDRESS_INCLUDE_1 = "ADDRESS_INCLUDE_1";

    private static final String INN_INCLUDE_2 = "INN_INCLUDE_2";
    private static final String ADDRESS_INCLUDE_2 = "ADDRESS_INCLUDE_2";



    private static final String ACTIVITY_1 = "ACTIVITY_1";
    private static final String ACTIVITY_2 = "ACTIVITY_2";
    private static final String ACTIVITY_3 = "ACTIVITY_3";
    private static final String ACTIVITY_5 = "ACTIVITY_5";

    private static final ManufactureEntity MANUFACTURE_ENTITY_1 = buildManufactureEntity(
            ACTIVITY_1, ADDRESS_INCLUDE_1, INN_INCLUDE_1
    );

    private static final ManufactureEntity MANUFACTURE_ENTITY_2 = buildManufactureEntity(
            ACTIVITY_2, ADDRESS_INCLUDE_2, INN_INCLUDE_2
    );

    @Before
    public void setUp() {
        manufactureEntityRepository.deleteAll();

        manufactureEntityRepository.save(MANUFACTURE_ENTITY_1);
        manufactureEntityRepository.save(MANUFACTURE_ENTITY_2);

    }

    @Test
    public void test_findSuccess() {
        List<ManufactureEntity> list = manufactureEntityService.findByFilter();

        assertEquals(2, list.size());
        assertTrue(list.contains(MANUFACTURE_ENTITY_1));
        assertTrue(list.contains(MANUFACTURE_ENTITY_2));
    }

    private static ManufactureEntity buildManufactureEntity(
            String activity,
            String address,
            String inn
    ) {
        ManufactureEntity manufactureEntity = new ManufactureEntity();
        manufactureEntity.setActivity(activity);
        manufactureEntity.setAddress(address);
        manufactureEntity.setInn(inn);
        return manufactureEntity;
    }

}
