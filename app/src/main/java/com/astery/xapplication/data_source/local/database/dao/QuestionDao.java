package com.astery.xapplication.data_source.local.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.astery.xapplication.pojo.Answer;
import com.astery.xapplication.pojo.Consequence;
import com.astery.xapplication.pojo.Desire;
import com.astery.xapplication.pojo.Question;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface QuestionDao {

    /** get questions with their answers */
    @Query("SELECT * from questionentity WHERE parent_id = :parentId")
    Single<List<Question>> getQuestions(String parentId);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAnswer(Answer answer);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAnswers(List<Answer> answers);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addConsequence(Consequence consequence);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addConsequences(List<Consequence> consequences);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addDesire(Desire desire);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addDesires(List<Desire> desires);


    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAnswer(Answer answer);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAnswers(List<Answer> answers);


    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateConsequence(Consequence consequence);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateConsequences(List<Consequence> consequences);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateDesire(Desire desire);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateDesires(List<Desire> desires);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateQuestion(Question questions);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateQuestions(List<Question> questions);

}
