package com.astery.xapplication.pojo.pojo_converters;

import com.astery.xapplication.pojo.Question;
import com.astery.xapplication.pojo.only_for_db.QuestionEntity;

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


}
