package hackathon.processor;

import hackathon.db.model.ActivityEntity;
import hackathon.db.model.LicenseEntity;
import hackathon.db.model.ManufactureEntity;
import hackathon.db.repository.ActivityEntityRepository;
import hackathon.db.repository.LicenseEntityRepository;
import hackathon.model.Manufacture;
import hackathon.service.ManufactureEntityService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManufactureProcessor {
    private static final String SEPARATORS = ",";

    private ManufactureEntityService manufactureEntityService;
    private ActivityEntityRepository activityEntityRepository;
    private LicenseEntityRepository licenseEntityRepository;

    public ManufactureProcessor(
            ManufactureEntityService manufactureEntityService,
            ActivityEntityRepository activityEntityRepository,
            LicenseEntityRepository licenseEntityRepository) {
        this.manufactureEntityService = manufactureEntityService;
        this.licenseEntityRepository = licenseEntityRepository;
        this.activityEntityRepository = activityEntityRepository;
    }

    public List<Manufacture> getList(Boolean all) {
        List<ManufactureEntity> listFiltered = manufactureEntityService.findByFilter();
        List<ActivityEntity> activityEntities = activityEntityRepository.findAll();
        List<String> activities = activityEntities
                .stream()
                .map(activityEntity -> activityEntity.getName().toLowerCase())
                .collect(Collectors.toList());
        List<String> licenses = licenseEntityRepository.findAll()
                .stream()
                .map(LicenseEntity::getInn)
                .collect(Collectors.toList());

        listFiltered =
                listFiltered.stream()
                        .filter(manufactureEntity -> isMatchedByActivity(manufactureEntity, activities))
                        .collect(Collectors.toList());

        if (!all) {
            listFiltered = listFiltered.stream()
                    .filter(manufactureEntity ->
                            !licenses.contains(manufactureEntity.getInn()))
                    .collect(Collectors.toList());
        }

        return listFiltered
                .stream()
                .map(manufactureEntity -> adopt(manufactureEntity, licenses))
                .collect(Collectors.toList());
    }

    private static Manufacture adopt(
            ManufactureEntity manufactureEntity,
            List<String> licenses) {
        Manufacture manufacture = new Manufacture();
        manufacture.setInn(manufactureEntity.getInn());
        manufacture.setCoords(String.format(
                "%s, %s",
                manufactureEntity.getNorth(),
                manufactureEntity.getWest()));
        manufacture.setAddress(manufactureEntity.getAddress());
        manufacture.setLicence(licenses.contains(manufactureEntity.getInn()));
        manufacture.setId(manufactureEntity.getName());
        manufacture.setType(manufactureEntity.getActivity());
        return manufacture;
    }

    private static boolean isMatchedByActivity(
            ManufactureEntity manufactureEntity,
            List<String> activities) {
        List<String> activitiesList =
                Arrays.stream(manufactureEntity.getActivity().split(SEPARATORS))
                        .map(s -> s.toLowerCase().trim()).collect(Collectors.toList());

        return activitiesList.stream().anyMatch(
                activities::contains
        );
    }


}