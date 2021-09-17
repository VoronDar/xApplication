package com.astery.xapplication.data_source.local.database.converter;

import androidx.room.TypeConverter;

import com.astery.xapplication.pojo.Warning;
import com.astery.xapplication.pojo.WarningTemplate;
import com.astery.xapplication.pojo.enums.EventCategory;
import com.astery.xapplication.pojo.enums.WarningCategory;

public class WarningCategoryConverter {
        @TypeConverter
        public int toDb(WarningCategory value) {
            if (value == null)
                return WarningCategory.WARNING.ordinal();
            return value.ordinal();
        }

        @TypeConverter
        public WarningCategory toClass(int data) {
            for (WarningCategory e : WarningCategory.values()) {
                if (e.ordinal() == data)
                    return e;
            }
            throw new RuntimeException("WarningCategoryConverter got invalid enum ordinal = " + data);
        }
}
