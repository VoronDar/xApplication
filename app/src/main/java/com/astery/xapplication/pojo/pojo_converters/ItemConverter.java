package com.astery.xapplication.pojo.pojo_converters;

import com.astery.xapplication.pojo.Item;
import com.astery.xapplication.pojo.Question;
import com.astery.xapplication.pojo.only_for_db.ItemEntity;
import com.astery.xapplication.pojo.only_for_db.QuestionEntity;

public class ItemConverter {

    /**
     * convert item to itemEntity
     * itemEntity has no information about children, so you should take them in other func */
    public static ItemEntity getEntity(Item item){
        return new ItemEntity(item.getId(), item.getText(), item.getParentId());
    }

    /**
     * convert itemEntity to item
     * itemEntity has no information about children, so you should take them in other func */
    public static Item translateEntity(ItemEntity entity){
        return new Item(entity.getId(), entity.getText(), entity.getParentId());
    }


}
