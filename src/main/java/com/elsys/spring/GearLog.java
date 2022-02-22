package com.elsys.spring;

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
    private int inRpm;
    private int outRpm;
    private int gear;
    private final Timestamp time;

    public GearLog() {
        this.inRpm = 0;
        this.outRpm = 0;
        this.gear = 0;
        this.time = null;
    }

    public GearLog(int inRpm, int outRpm, int gear) {
        this.inRpm = inRpm;
        this.outRpm = outRpm;
        this.gear = gear;
        this.time = Timestamp.from(Instant.now());
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

    public int getGear() {
        return gear;
    }

    public Timestamp getTime() {
        return this.time;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setInRpm(int inRpm) {
        this.inRpm = inRpm;
    }

    public void setOutRpm(int outRpm) {
        this.outRpm = outRpm;
    }

    public void setGear(int gear) {
        this.gear = gear;
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
        return String.format("GearLog: id=%d inRpm=%d outRpm=%d gear=%c time=%s", this.getId(), this.getInRpm(), this.getOutRpm(), (this.getGear() == -1) ? 'R' : (char)('0' + this.getGear()), this.getTime());
    }
}
