package com.elsys.spring;

import com.elsys.spring.GearLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GearRepository extends JpaRepository<GearLog, Long> {
}
