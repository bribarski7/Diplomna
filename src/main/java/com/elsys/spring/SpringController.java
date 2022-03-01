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

    private void logMessage(String message){
        String line = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
        log.info(line);
        log.info(message);
        log.info(line);
    }

    @GetMapping("/gear/index")
    public String home(){
        logMessage("Visitor visited home page");
        return "home";
    }

    @GetMapping("/gear/all")
    public String index(Model model){
        List<GearLog> gears = gearRepository.findAll();
        model.addAttribute("gears", gears);
        logMessage("Visitor saw database");
        return "full";
    }

    @PostMapping("/gear/get")
    public String get(@RequestBody String id, Model model){
        if(!id.matches("^id=[1-9][0-9]*?$")){
            model.addAttribute("message", "error input");
            logMessage("Error searched parameter");
            return "full";
        }
        Long ids = Long.parseLong(id.substring(3));
        logMessage("Visitor searched for GearLog #" + ids);
        GearLog gearLog;
        List<GearLog> gears = new ArrayList<>();
        try{
            gearLog = gearRepository.findById(ids).orElseThrow(() -> new GearLogNotFoundException(ids));
            gears.add(gearLog);
            model.addAttribute("gear", gearLog);
            model.addAttribute("gears", gears);
        } catch (GearLogNotFoundException e){
            model.addAttribute("gear", "Not found");
            model.addAttribute("message", "Not found #" + ids);
        }
        return "full";
    }
    @PostMapping("/gear/esp")
    public String esp(@RequestBody Map<String, String> data, Model model){
        GearLog gearLog;
        try{
            int i = Integer.parseInt(data.get("inRpm"));
            int o = Integer.parseInt(data.get("outRpm"));
            int g = Integer.parseInt(data.get("gear"));
            if(g < -1 || i < 0 || o < 0){
                throw new GearLogInvalidArgumentException();
            }
            gearLog = new GearLog(i, o, g);
            logMessage("Esp module added GearLog #" + (gearRepository.findAll().size()+1));
            gearRepository.save(gearLog);
            model.addAttribute("gears", gearRepository.findAll());
        } catch (Exception e){
            logMessage(e.getMessage());
        }
        return "espResponse";
    }
}
