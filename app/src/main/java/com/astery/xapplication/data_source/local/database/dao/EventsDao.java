package com.astery.xapplication.data_source.local.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.astery.xapplication.pojo.Answer;
import com.astery.xapplication.pojo.Desire;
import com.astery.xapplication.pojo.Event;
import com.astery.xapplication.pojo.EventTemplate;
import com.astery.xapplication.pojo.Item;
import com.astery.xapplication.pojo.Question;
import com.astery.xapplication.pojo.Warning;
import com.astery.xapplication.pojo.WarningTemplate;
import com.astery.xapplication.pojo.only_for_db.QuestionEntity;

import java.util.Date;
import java.util.List;

import io.reactivex.Single;

@Dao
public interface EventsDao {

    @Query("SELECT * FROM event WHERE date = :time")
    Single<List<Event>> getEventsByTime(long time);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addEventTemplate(EventTemplate template);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addEventTemplates(List<EventTemplate> templates);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addEvent(Event event);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addEvents(List<Event> events);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addWarningTemplate(WarningTemplate template);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addWarningTemplates(List<WarningTemplate> templates);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addWarning(Warning warning);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addWarnings(List<Warning> warning);


    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateEventTemplate(EventTemplate template);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateEventTemplates(List<EventTemplate> templates);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateEvent(Event event);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateEvents(List<Event> events);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateWarningTemplate(WarningTemplate template);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateWarningTemplates(List<WarningTemplate> templates);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateWarning(Warning warning);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateWarnings(List<Warning> warning);


}
