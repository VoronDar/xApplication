package com.astery.xapplication.data_source.local.database.converter;

import androidx.room.TypeConverter;

import com.astery.xapplication.pojo.enums.Appeared;
import com.astery.xapplication.pojo.enums.EventCategory;

public class AppearedConverter {
        @TypeConverter
        public int toDb(Appeared value) {
            if (value == null)
                return Appeared.PREPARED.ordinal();
            return value.ordinal();
        }

        @TypeConverter
        public Appeared toClass(int data) {
            for (Appeared e : Appeared.values()) {
                if (e.ordinal() == data)
                    return e;
            }
            throw new RuntimeException("AppearedConverter got invalid enum ordinal = " + data);
        }
}
