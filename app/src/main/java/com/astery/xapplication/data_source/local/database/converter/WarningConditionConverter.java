package com.astery.xapplication.data_source.local.database.converter;

import androidx.room.TypeConverter;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.astery.xapplication.pojo.WarningTemplate;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class WarningConditionConverter {
    @TypeConverter
    public String toDb(WarningTemplate.WarningCondition value) {
        if (value == null)
            return null;
        return new Gson().toJson(value);

    }
    @TypeConverter
    public WarningTemplate.WarningCondition toClass(String data) {
        if (data == null)
            return null;
        try {
            JSONObject obj = new JSONObject(data);
            return new Gson().fromJson(obj.toString(), WarningTemplate.WarningCondition.class);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("WarningCondition converting error " + e.getMessage());
        }
    }

}

