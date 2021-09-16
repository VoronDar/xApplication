package com.astery.xapplication.data_source.local.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.astery.xapplication.pojo.Advise;
import com.astery.xapplication.pojo.Article;
import com.astery.xapplication.pojo.Item;
import com.astery.xapplication.pojo.only_for_db.ArticleEntity;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ArticleDao{

    /** return items with all of its advises */
    @Query("SELECT * FROM itementity WHERE id = :itemId")
    Single<Item> getItemById(String itemId);


    /** return article with all of its items and advises */
    @Query("SELECT * FROM ArticleEntity WHERE id = :articleId")
    Single<Article> getArticleById(String articleId);

    @Query("SELECT * from advise where parent_id = :parentId")
    Single<Article> getAdvisesForItem(String parentId);


    //
    ///** return article with all of its items and advises */
    //@Query("SELECT * FROM ArticleEntity ORDER BY timestamp LIMIT :limit")
    //Single<List<Article>> getArticleById(int limit);



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAdvise(Advise advise);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAdvises(List<Advise> advises);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addArticle(ArticleEntity article);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addArticles(List<ArticleEntity> articles);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addItem(Item item);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addItems(List<Item> items);



    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAdvise(Advise advise);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAdvises(List<Advise> advises);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateArticle(ArticleEntity article);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateArticles(List<ArticleEntity> articles);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateItem(Item item);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateItems(List<Item> items);

}
