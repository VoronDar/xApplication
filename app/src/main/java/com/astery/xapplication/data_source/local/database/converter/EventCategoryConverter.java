package com.astery.xapplication.data_source.local.database.converter;

import androidx.room.TypeConverter;

import com.astery.xapplication.pojo.enums.EventCategory;

public class EventCategoryConverter {
        @TypeConverter
        public int toDb(EventCategory value) {
            return value.ordinal();
        }

        @TypeConverter
        public EventCategory toClass(int data) {
            for (EventCategory e : EventCategory.values()) {
                if (e.ordinal() == data)
                    return e;
            }
            throw new RuntimeException("EventCategoryConverter got invalid enum ordinal = " + data);
        }
}
