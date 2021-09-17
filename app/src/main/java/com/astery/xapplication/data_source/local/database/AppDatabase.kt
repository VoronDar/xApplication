package com.astery.xapplication.data_source.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.astery.xapplication.data_source.local.database.dao.ArticleDao
import com.astery.xapplication.data_source.local.database.dao.EventsDao
import com.astery.xapplication.data_source.local.database.dao.FaqDao
import com.astery.xapplication.data_source.local.database.dao.QuestionDao
import com.astery.xapplication.pojo.*
import com.astery.xapplication.pojo.only_for_db.ArticleEntity
import com.astery.xapplication.pojo.only_for_db.ItemEntity
import com.astery.xapplication.pojo.only_for_db.QuestionEntity

@Database(
    entities = [ArticleEntity::class, ItemEntity::class, QuestionEntity::class, Advise::class, Answer::class,
        EventTemplate::class, Desire::class, Event::class, Category::class, Warning::class, WarningTemplate::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao
    abstract fun questionDao(): QuestionDao
    abstract fun faqDao(): FaqDao
    abstract fun eventDao(): EventsDao



    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null


        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE ArticleEntity");
                database.execSQL("DROP TABLE ItemEntity");
                database.execSQL("DROP TABLE QuestionEntity");
                database.execSQL("DROP TABLE advise");
                database.execSQL("DROP TABLE answer");
                database.execSQL("DROP TABLE desire");
                database.execSQL("DROP TABLE event");
                database.execSQL("DROP TABLE eventTemplate");
                //database.execSQL("ALTER TABLE Employee ADD COLUMN birthday INTEGER DEFAULT 0 NOT NULL")
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database")
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}