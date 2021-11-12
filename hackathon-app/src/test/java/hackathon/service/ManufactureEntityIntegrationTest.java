package hackathon.service;

import app.config.TestHackathonApplication;
import hackathon.db.model.ActivityEntity;
import hackathon.db.model.LicenseEntity;
import hackathon.db.model.ManufactureEntity;
import hackathon.db.repository.ActivityEntityRepository;
import hackathon.db.repository.LicenseEntityRepository;
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
    private LicenseEntityRepository licenseEntityRepository;

    @Autowired
    private ActivityEntityRepository activityEntityRepository;

    @Autowired
    private ManufactureEntityService manufactureEntityService;

    private static final String INN_INCLUDE_1 = "INN_INCLUDE_1";
    private static final String ADDRESS_INCLUDE_1 = "ADDRESS_INCLUDE_1";

    private static final String INN_INCLUDE_2 = "INN_INCLUDE_2";
    private static final String ADDRESS_INCLUDE_2 = "ADDRESS_INCLUDE_2";

    private static final String INN_EXCLUDE_1 = "INN_EXCLUDE_1";
    private static final String ADDRESS_EXCLUDE_1 = "ADDRESS_EXCLUDE_1";

    private static final String INN_EXCLUDE_2 = "INN_EXCLUDE_2";
    private static final String ADDRESS_EXCLUDE_2 = "ADDRESS_EXCLUDE_2";

    private static final String INN_EXCLUDE_3 = "INN_EXCLUDE_3";

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
        activityEntityRepository.deleteAll();
        licenseEntityRepository.deleteAll();
        manufactureEntityRepository.deleteAll();

        activityEntityRepository.save(buildActivityEntity(ACTIVITY_1));
        activityEntityRepository.save(buildActivityEntity(ACTIVITY_2));
        activityEntityRepository.save(buildActivityEntity(ACTIVITY_3));

        licenseEntityRepository.save(buildLicenseEntity(INN_EXCLUDE_1));
        licenseEntityRepository.save(buildLicenseEntity(INN_EXCLUDE_2));
        licenseEntityRepository.save(buildLicenseEntity(INN_EXCLUDE_3));

        manufactureEntityRepository.save(MANUFACTURE_ENTITY_1);
        manufactureEntityRepository.save(MANUFACTURE_ENTITY_2);

        manufactureEntityRepository.save(buildManufactureEntity(
                ACTIVITY_1, ADDRESS_EXCLUDE_1, INN_EXCLUDE_1
        ));
        manufactureEntityRepository.save(buildManufactureEntity(
                ACTIVITY_5, ADDRESS_EXCLUDE_2, INN_EXCLUDE_2
        ));

    }

    @Test
    public void test_findSuccess() {
        List<ManufactureEntity> list = manufactureEntityService.findByFilter();

        assertEquals(2, list.size());
        assertTrue(list.contains(MANUFACTURE_ENTITY_1));
        assertTrue(list.contains(MANUFACTURE_ENTITY_2));
    }

    private static ActivityEntity buildActivityEntity(String name) {
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setName(name);
        return activityEntity;
    }

    private static LicenseEntity buildLicenseEntity(String inn) {
        LicenseEntity licenseEntity = new LicenseEntity();
        licenseEntity.setInn(inn);
        return licenseEntity;
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
