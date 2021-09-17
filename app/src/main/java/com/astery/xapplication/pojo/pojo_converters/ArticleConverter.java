package com.astery.xapplication.pojo.pojo_converters;

import com.astery.xapplication.pojo.Article;
import com.astery.xapplication.pojo.Item;
import com.astery.xapplication.pojo.only_for_db.ArticleEntity;
import com.astery.xapplication.pojo.only_for_db.ItemEntity;

import java.util.ArrayList;
import java.util.List;

public class ArticleConverter {

    /**
     * convert article to ArticleEntity
     * articleEntity has no information about children, so you should take them in other func */
    public static ArticleEntity getEntity(Article article){
        return new ArticleEntity(article.getId(), article.getName(), article.getDescription(), article.getLikes(), article.getDislikes(),
                article.getWatched(), article.getTimestamp(), article.getWideTags(), article.getKeyWords());
    }

    /**
     * convert articleEntity to article
     * articleEntity has no information about children, so you should take them in other func */
    public static Article translateEntity(ArticleEntity entity){
        return new Article(entity.getId(), null, entity.getName(), entity.getDescription(), entity.getLikes(), entity.getDislikes(),
                entity.getWatched(), entity.getTimestamp(), entity.getWideTags(), entity.getKeyWords(), null, null);
    }

    public static List<ArticleEntity> getEntities(List<Article> items){
        List<ArticleEntity> list = new ArrayList<>();
        for (Article i : items){
            list.add(getEntity(i));
        }
        return list;
    }


}
