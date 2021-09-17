package com.astery.xapplication.data_source.local.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import com.astery.xapplication.pojo.Category;
import com.astery.xapplication.pojo.Event;
import com.astery.xapplication.pojo.EventTemplate;
import com.astery.xapplication.pojo.Warning;
import com.astery.xapplication.pojo.WarningTemplate;

import java.util.List;

@Dao
public interface FaqDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCategory(Category category);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCategories(List<Category> category);


    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCategory(Category category);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCategories(List<Category> category);



}
