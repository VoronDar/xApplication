package com.astery.xapplication.data_source.local.database.converter;

import androidx.room.TypeConverter;

import java.util.Date;


public class DateConverter {
    @TypeConverter
    public long toDb(Date value) {
        return value.getTime();
    }

    @TypeConverter
    public Date toClass(long data) {
        return new Date(data);
    }
}
