package com.astery.xapplication.data_source.local.database.converter;

import androidx.room.TypeConverter;

import com.astery.xapplication.pojo.serialazable.EventDescription;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// TODO: move it to another package later
public class EventDescriptionConverter {

    @TypeConverter
    public String toDb(EventDescription value) {
        StringBuilder properties = new StringBuilder();
        for (String k : value.getProperties().keySet()) {
            properties.append(k).append("|").append(value.getProperties().get(k)).append("//");
        }
        return properties.substring(0, properties.length() - 2);
    }

    @TypeConverter
    public EventDescription toClass(String data) {
        ArrayList<String> properties = new ArrayList<>();
        Collections.addAll(properties, data.split("//"));

        Map<String, String> values = new HashMap<>();
        for (String k : properties) {
            String key = k.substring(0, k.indexOf("|"));
            String value = k.substring(k.indexOf("|"));
            values.put(key, value);
        }
        return new EventDescription(values);
    }
}
