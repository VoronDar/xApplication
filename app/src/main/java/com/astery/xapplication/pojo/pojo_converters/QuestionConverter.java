package com.astery.xapplication.pojo.pojo_converters;

import com.astery.xapplication.pojo.Question;
import com.astery.xapplication.pojo.only_for_db.QuestionEntity;

import java.util.ArrayList;
import java.util.List;

public class QuestionConverter {

    /**
     * convert question to questionEntity
     * questionEntity has no information about children, so you should take them in other func */
    public static QuestionEntity getEntity(Question question){
        return new QuestionEntity(question.getId(), question.getText(), question.getParentId());
    }

    /**
     * convert question entity to question.
     * questionEntity has no information about children, so you should take them in other func
     * */
    public static Question translateEntity(QuestionEntity entity){
        return new Question(entity.getId(), entity.getText(), entity.getParentId());
    }

    /**
     * convert question to questionEntity
     * questionEntity has no information about children, so you should take them in other func */
    public static ArrayList<QuestionEntity> getEntities(List<Question> questions){
        ArrayList<QuestionEntity> entities = new ArrayList<>();
        for (Question q: questions){
            entities.add(new QuestionEntity(q.getId(), q.getText(), q.getParentId()));
        }
        return entities;
    }


}
