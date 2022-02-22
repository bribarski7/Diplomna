package com.elsys.spring;

import java.util.List;

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
        model.addAttribute("title", "Home");
        return "home";
    }

    @GetMapping("/gear/all")
    public String index(Model model){
        List<GearLog> gears = gearRepository.findAll();
        model.addAttribute("gears", gears);
        model.addAttribute("test", new GearLog());
        model.addAttribute("title", "All Logs");
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.info("Got all GearLogs");
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        return "all";
    }

    @GetMapping("/gear/search")
    public String search(Model model) {
        model.addAttribute("title", "Search Log");
        return "search";
    }

    @PostMapping("/gear/get")
    public String get(@RequestBody String id, Model model){
        Long ids = Long.parseLong(id.substring(3));
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.info("Got GearLog #" + ids);
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        GearLog gearLog = new GearLog();
        try{
            gearLog = gearRepository.findById(ids).orElseThrow(() -> new GearLogNotFoundException(ids));
            model.addAttribute("gear", gearLog);
        }catch (GearLogNotFoundException e){
            model.addAttribute("gear", "Not found");
        }
        model.addAttribute("title", "Searched Log");
        return "result";
    }

    @GetMapping("/gear/generate")
    public String generate(Model model){
        model.addAttribute("title", "Generate Log");
        return "generate";
    }

    @PostMapping("/gear/post")
    public String indexGet(@RequestBody String in, @RequestBody String out, @RequestBody String gear, Model model){
        GearLog gearLog = new GearLog(
                Integer.parseInt(in.split("&")[0].substring("in=".length())),
                Integer.parseInt(out.split("&")[1].substring("out=".length())),
                Integer.parseInt(gear.split("&")[2].substring("gear=".length())));
        model.addAttribute("gear", gearLog);
        model.addAttribute("title", "Generated Log");
        gearLog = gearRepository.save(gearLog);
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.info("Got GearLog #" + gearLog.getId());
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        model.addAttribute("gear", gearLog);
        return "result";
    }

    @PostMapping("/gear")
    public String addGearLog(@RequestBody GearLog newGearLog, Model model){
        GearLog gearLog = gearRepository.save(newGearLog);
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.info("Added new GearLog #" + gearLog.getId());
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        model.addAttribute("gear",gearLog);
        return "post";
    }

    @PutMapping("/gear/{id}")
    public String replaceGearLoge(@RequestBody GearLog newGearLog, @PathVariable Long id, Model model){
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.info("Replaced GearLogs #" + id);
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        GearLog gearLog = gearRepository.findById(id)
                .map(log -> {
                    log.setInRpm(newGearLog.getInRpm());
                    log.setOutRpm(newGearLog.getOutRpm());
                    log.setGear(newGearLog.getGear());
                    return gearRepository.save(log);
                }).orElseThrow(() -> new GearLogNotFoundException(id));
        model.addAttribute("gear", gearLog);
        return "put";
    }

    @DeleteMapping("/gear/{id}")
    public String deleteGearLog(@PathVariable Long id, Model model){
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.info("Deleted GearLog #" + id);
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        GearLog gearLog = gearRepository.findById(id).orElseThrow(() -> new GearLogNotFoundException(id));
        model.addAttribute("gear", gearLog);
        gearRepository.deleteById(id);
        return "delete";
    }

    @GetMapping("/gear/settings")
    public String settings() {
        return "settings";
    }
}
