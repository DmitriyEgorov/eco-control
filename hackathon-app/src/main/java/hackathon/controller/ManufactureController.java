package hackathon.controller;

import hackathon.model.Manufacture;
import hackathon.processor.ManufactureProcessor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manufacture")
public class ManufactureController {

    private final Environment environment;

    private ManufactureProcessor manufactureProcessor;

    public ManufactureController(
            ManufactureProcessor manufactureProcessor,
            Environment environment) {
        this.manufactureProcessor = manufactureProcessor;
        this.environment = environment;
    }

    @GetMapping("/getList")
    public List<Manufacture> getList(
            @RequestParam(name = "all", required = false) Boolean all,
            @RequestParam(name = "withoutLicence", required = false) Boolean withoutLicence,
            @RequestParam(name = "onlyLicence", required = false) Boolean onlyLicence
    ) {
        return manufactureProcessor.getList(
                BooleanUtils.isTrue(all),
                BooleanUtils.isTrue(withoutLicence),
                BooleanUtils.isTrue(onlyLicence));
    }

    @RequestMapping(value = "/getList/report",
            produces = {"application/octet-stream"},
            method = RequestMethod.GET)
    ResponseEntity<Resource> getReport(
            @RequestParam(name = "all", required = false) Boolean all,
            @RequestParam(name = "withoutLicence", required = false) Boolean withoutLicence,
            @RequestParam(name = "onlyLicence", required = false) Boolean onlyLicence) {
        return ResponseEntity
                .ok()
                .contentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "report.xlsx" + "\"")
                .body(new ByteArrayResource(manufactureProcessor.getReport(
                        BooleanUtils.isTrue(all),
                        BooleanUtils.isTrue(withoutLicence),
                        BooleanUtils.isTrue(onlyLicence))));
    }

    @GetMapping("/setCoordinates")
    public void setCoordinates() {
        manufactureProcessor.setCoordinates(environment.getProperty("yandex.token"));
    }

}
