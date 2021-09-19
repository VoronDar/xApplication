package com.astery.xapplication.pojo;

import java.util.ArrayList;
import java.util.List;

public class CategoryBuilder {
    private final List<Category> list;

    public CategoryBuilder(List<Category> list) {
        this.list = list;
    }
    public Category build(){
        Category category = new Category("", null, null, null, null);
        category.setChildren(new ArrayList<>());
        for (Category c: list){
            if (c.getParentId() == null)
                category.getChildren().add(c);
        }
        if (category.getChildren() == null)
            return null;
        else
            for (Category c: category.getChildren()){
                newStep(c);
            }
            return category;
    }

    private void newStep(Category category){
        category.setChildren(new ArrayList<>());
        for (Category c: list){
            if (c.getParentId() == null)
                continue;
            if (c.getParentId().equals(category.getId())) {
                category.getChildren().add(c);
                newStep(c);
            }
        }
    }
}
