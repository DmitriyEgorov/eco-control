package hackathon.controller;

import hackathon.model.Manufacture;
import hackathon.processor.ManufactureProcessor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/manufacture")
public class ManufactureController {

    private ManufactureProcessor manufactureProcessor;

    public ManufactureController(ManufactureProcessor manufactureProcessor) {
        this.manufactureProcessor = manufactureProcessor;
    }

    @GetMapping("/getList")
    public List<Manufacture> getList(@RequestParam(name = "all", required = false) Boolean all) {
        return manufactureProcessor.getList(BooleanUtils.isTrue(all));
    }

}
