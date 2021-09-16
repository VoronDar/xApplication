package com.astery.xapplication.data_source.local.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.astery.xapplication.data_source.local.database.dao.ArticleDao;
import com.astery.xapplication.data_source.local.database.dao.QuestionDao;
import com.astery.xapplication.pojo.Advise;
import com.astery.xapplication.pojo.Answer;
import com.astery.xapplication.pojo.Consequence;
import com.astery.xapplication.pojo.Desire;
import com.astery.xapplication.pojo.only_for_db.ArticleEntity;
import com.astery.xapplication.pojo.only_for_db.ItemEntity;
import com.astery.xapplication.pojo.only_for_db.QuestionEntity;

@Database(entities = {ArticleEntity.class, ItemEntity.class, QuestionEntity.class,
        Advise.class, Answer.class, Consequence.class, Desire.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ArticleDao articleDao();
    public abstract QuestionDao questionDao();
}
