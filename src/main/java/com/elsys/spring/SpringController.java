package com.elsys.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
class SpringController {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private final GearRepository gearRepository;

    public SpringController(GearRepository gearRepository) {
        this.gearRepository = gearRepository;
    }

    @GetMapping("/gear/index")
    public String home(Model model){
        return "home";
    }

    @GetMapping("/gear/all")
    public String index(Model model){
        List<GearLog> gears = gearRepository.findAll();
        model.addAttribute("gears", gears);
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.info("Got all GearLogs");
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        return "full";
    }

    @PostMapping("/gear/get")
    public String get(@RequestBody String id, Model model){
        Long ids = Long.parseLong(id.substring(3));
        int idi = Integer.parseInt(id.substring(3));
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.info("Got GearLog #" + ids);
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        GearLog gearLog;
        List<GearLog> gears = new ArrayList<>();
        try{
            gearLog = gearRepository.findById(ids).orElseThrow(() -> new GearLogNotFoundException(ids));
            gears.add(gearLog);
            model.addAttribute("gear", gearLog);
            model.addAttribute("gears", gears);
        }catch (GearLogNotFoundException e){
            model.addAttribute("gear", "Not found");
        }
        return "full";
    }
    @PostMapping("/gear/esp")
    public String esp(@RequestBody Map<String, String> gear, Model model){
        int i = Integer.parseInt(gear.get("inRpm"));
        int o = Integer.parseInt(gear.get("outRpm"));
        int g = Integer.parseInt(gear.get("gear"));
        GearLog gearLog = new GearLog(i, o, g);
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.info("add GearLog #" + gearRepository.findAll().size()+1);
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        gearRepository.save(gearLog);
        model.addAttribute("gears", gearRepository.findAll());
        return "full";
    }
}
