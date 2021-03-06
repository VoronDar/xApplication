package com.astery.xapplication.data_source.local.database.converter;

import androidx.room.TypeConverter;

import java.sql.Timestamp;

public class TimeStampConverter {
    @TypeConverter
    public long toDb(Timestamp value) {
        return value.getTime();
    }

    @TypeConverter
    public Timestamp toClass(long data) {
        return new Timestamp(data);
    }
}
