package com.elsys.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class GearLog {
    @Id
    @GeneratedValue
    public Long id;
    private final int inRpm;
    private final int outRpm;
    private final int gear;
    private final Timestamp time;

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public GearLog() {
        this.inRpm = 0;
        this.outRpm = 0;
        this.gear = 0;
        this.time = Timestamp.from(Instant.now());
        log.info("New " + this.toString());
    }

    public GearLog(int inRpm, int outRpm, int gear) {
        this.inRpm = inRpm;
        this.outRpm = outRpm;
        this.gear = gear;
        this.time = Timestamp.from(Instant.now());
        log.info("New " + this.toString());
    }

    public Long getId() {
        return id;
    }

    public int getInRpm() {
        return inRpm;
    }

    public int getOutRpm() {
        return outRpm;
    }

    public char getGear() {
        return (this.gear <= -1) ? 'R' : (char)('0' + this.gear);
    }

    public String getTime() {
        if(this.time == null){
            return "2000-01-01 00:00:00";
        }
        return this.time.toString().substring(0, 19);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getInRpm(), this.getOutRpm(), this.getGear(), this.getTime());
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)return true;
        else if(!(obj instanceof GearLog gearLog)){
            return false;
        }
        else {
            return Objects.equals(this.getId(), gearLog.getId()) &&
                   Objects.equals(this.getInRpm(), gearLog.getInRpm()) &&
                   Objects.equals(this.getOutRpm(), gearLog.getOutRpm()) &&
                    Objects.equals(this.getGear(), gearLog.getGear()) &&
                    Objects.equals(this.getTime(), gearLog.getTime());
        }
    }

    @Override
    public String toString() {
        return String.format("GearLog: id=%d inRpm=%d outRpm=%d gear=%c time=%s", this.getId(), this.getInRpm(), this.getOutRpm(), this.getGear(), this.getTime());
    }
}
