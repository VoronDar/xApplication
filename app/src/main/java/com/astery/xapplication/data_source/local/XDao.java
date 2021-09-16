package com.astery.xapplication.data_source.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import com.astery.xapplication.pojo.Advise;
import com.astery.xapplication.pojo.AdviseList;
import com.astery.xapplication.pojo.Answer;
import com.astery.xapplication.pojo.Article;
import com.astery.xapplication.pojo.Consequence;
import com.astery.xapplication.pojo.Desire;
import com.astery.xapplication.pojo.Item;
import com.astery.xapplication.pojo.Question;

import java.util.List;

@Dao
public interface XDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAdvise(Advise advise);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAdvises(List<Advise> advises);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAdviseList(AdviseList list);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAdviseListList(List<AdviseList> lists);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAnswer(Answer answer);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAnswers(List<Answer> answers);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addArticle(Article article);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addArticles(List<Article> articles);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addConsequence(Consequence consequence);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addConsequences(List<Consequence> consequences);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addDesire(Desire desire);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addDesires(List<Desire> desires);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addItem(Item item);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addItems(List<Item> items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addQuestion(Question questions);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addQuestions(List<Question> questions);


    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAdvise(Advise advise);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAdvises(List<Advise> advises);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAdviseList(AdviseList list);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAdviseListList(List<AdviseList> lists);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAnswer(Answer answer);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAnswers(List<Answer> answers);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateArticle(Article article);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateArticles(List<Article> articles);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateConsequence(Consequence consequence);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateConsequences(List<Consequence> consequences);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateDesire(Desire desire);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateDesires(List<Desire> desires);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateItem(Item item);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateItems(List<Item> items);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateQuestion(Question questions);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateQuestions(List<Question> questions);

}
