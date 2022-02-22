package com.elsys.spring;

public class GearLogNotFoundException extends RuntimeException{
    public GearLogNotFoundException(Long id) {
        super("Could not find this GearLog " + id);
    }
}
