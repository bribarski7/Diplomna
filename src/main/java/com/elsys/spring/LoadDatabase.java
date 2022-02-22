package com.elsys.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    private final GearRepository gearRepository;

    @Autowired
    public LoadDatabase(GearRepository gearRepository){
        this.gearRepository = gearRepository;
    }

    @Bean
    CommandLineRunner initDatabase(GearRepository gearRepository){
        return args -> {
            log.info("Preloading database");
        };
    }
}
