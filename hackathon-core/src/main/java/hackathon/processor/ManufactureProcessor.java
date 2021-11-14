package hackathon.processor;

import hackathon.db.model.ActivityEntity;
import hackathon.db.model.LicenseEntity;
import hackathon.db.model.ManufactureEntity;
import hackathon.db.repository.ActivityEntityRepository;
import hackathon.db.repository.LicenseEntityRepository;
import hackathon.model.Manufacture;
import hackathon.service.ManufactureEntityService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManufactureProcessor {
    private static final String SEPARATORS = ",";
    private static final String URL_GEO = "https://geocode-maps.yandex.ru/1.x/?apikey=%s&geocode=%s";
    private static final String COORDINATE_TAG_NAME = "pos";

    private ManufactureEntityService manufactureEntityService;
    private ActivityEntityRepository activityEntityRepository;
    private LicenseEntityRepository licenseEntityRepository;
    private ReportProcessor reportProcessor;

    public ManufactureProcessor(
            ManufactureEntityService manufactureEntityService,
            ActivityEntityRepository activityEntityRepository,
            LicenseEntityRepository licenseEntityRepository,
            ReportProcessor reportProcessor) {
        this.manufactureEntityService = manufactureEntityService;
        this.licenseEntityRepository = licenseEntityRepository;
        this.activityEntityRepository = activityEntityRepository;
        this.reportProcessor = reportProcessor;
    }

    public byte[] getReport(boolean all, boolean withoutLicence, boolean onlyLicence) {
        List<Manufacture> manufactures = getList(all, withoutLicence, onlyLicence);
        SXSSFWorkbook wbDisposable = null;
        try (SXSSFWorkbook wb = new SXSSFWorkbook()) {
            wbDisposable = wb;
            reportProcessor.buildSheet(wb, manufactures);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            wb.write(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            return new byte[0];
        } finally {
            //Удаление временных файлов
            if (wbDisposable != null) {
                wbDisposable.dispose();
            }
        }
    }

    public List<Manufacture> getList(
            boolean all,
            boolean withoutLicence,
            boolean onlyLicence) {
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

        if(onlyLicence) {
            listFiltered = listFiltered.stream()
                    .filter(manufactureEntity ->
                            licenses.contains(manufactureEntity.getInn()))
                    .collect(Collectors.toList());
            return listFiltered
                    .stream()
                    .map(manufactureEntity -> adopt(manufactureEntity, licenses))
                    .collect(Collectors.toList());
        }

        if(!all) {
            listFiltered =
                    listFiltered.stream()
                            .filter(manufactureEntity -> isMatchedByActivity(manufactureEntity, activities))
                            .collect(Collectors.toList());
        }

        if (withoutLicence) {
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

    public void setCoordinates(String token) {
        List<ManufactureEntity> list = manufactureEntityService.findByNorthIsNullOrWestIsNull();
        for (ManufactureEntity manufacture : list) {
            try {
                byte[] bytes = manufacture.getAddress().getBytes(StandardCharsets.UTF_8);
                String utf8EncodedString =
                        new String(bytes, StandardCharsets.UTF_8)
                                .replaceAll(" ", "%20");
                HttpGet request = new HttpGet(String.format(
                        URL_GEO,
                        token,
                        utf8EncodedString));
                CloseableHttpClient client = HttpClients.createDefault();
                CloseableHttpResponse response = client.execute(request);
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                Document doc = convertStringToXMLDocument(result);
                NodeList graphNodes = doc.getElementsByTagName(COORDINATE_TAG_NAME);
                String[] coordinates =
                        graphNodes.item(0).getFirstChild().getNodeValue().split("\\s+");
                manufacture.setWest(new BigDecimal(coordinates[0]));
                manufacture.setNorth(new BigDecimal(coordinates[1]));
            } catch (Exception e) {
                System.out.println(e);

            }
        }
        manufactureEntityService.saveAll(list);
    }

    private static Document convertStringToXMLDocument(String xmlString) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xmlString)));
    }

}
