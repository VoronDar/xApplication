package com.astery.xapplication.pojo.serialazable;

import java.io.Serializable;
import java.util.Map;

public class EventDescription implements Serializable {
    private Map<String, String> properties;

    public EventDescription(Map<String, String> properties) {
        this.properties = properties;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}