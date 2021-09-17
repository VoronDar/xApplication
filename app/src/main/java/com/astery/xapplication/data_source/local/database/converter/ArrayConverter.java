package com.astery.xapplication.data_source.local.database.converter;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;

public class ArrayConverter {
    @TypeConverter
    public String toDb(List<String> value) {
        if (value == null)
            return null;
        StringBuilder builder = new StringBuilder();
        for (String l : value) {
            builder.append(l).append(",");
        }
        return builder.substring(0, builder.length() - 2);
    }

    @TypeConverter
    public List<String> toClass(String data) {
        if (data == null)
            return null;
        return Arrays.asList(data.split(","));
    }
}
