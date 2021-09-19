package com.astery.xapplication;

import com.astery.xapplication.pojo.Category;
import com.astery.xapplication.pojo.CategoryBuilder;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class CategoryBuilderTest {

    @Test
    public void test1(){
        // regular test

        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category("1", null));
        categories.add(new Category("2", null));
        categories.add(new Category("3", "1"));
        categories.add(new Category("4", "3"));
        categories.add(new Category("5", "4"));
        categories.add(new Category("6", "2"));
        categories.add(new Category("7", "2"));


        StringBuilder stringBuilder = new StringBuilder();
        new CategoryBuilder(categories).build().getLink(stringBuilder, "");
        assertEquals("1|13|134|1345|2|26|27|", stringBuilder.toString());
    }

    @Test
    public void test2(){
        // check what to do with unresolved parent.

        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category("1", null));
        categories.add(new Category("2", "1"));
        categories.add(new Category("3", "1"));
        categories.add(new Category("4", "1"));
        categories.add(new Category("5", "1"));
        categories.add(new Category("6", "2"));
        categories.add(new Category("7", "3"));
        categories.add(new Category("8", "4"));
        categories.add(new Category("9", "10"));


        StringBuilder stringBuilder = new StringBuilder();
        new CategoryBuilder(categories).build().getLink(stringBuilder, "");
        assertEquals("1|12|126|13|137|14|148|15|", stringBuilder.toString());
    }

    @Test
    public void test3(){
        // check what to do with null

        ArrayList<Category> categories = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        new CategoryBuilder(categories).build().getLink(stringBuilder, "");
        assertEquals("", stringBuilder.toString());
    }


    @Test
    public void test4(){
        // another regular check

        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category("1", null));
        categories.add(new Category("2", "1"));
        categories.add(new Category("3", "2"));
        categories.add(new Category("4", "3"));
        categories.add(new Category("5", "4"));
        categories.add(new Category("6", "5"));
        categories.add(new Category("7", "6"));
        categories.add(new Category("8", "7"));
        categories.add(new Category("9", "8"));


        StringBuilder stringBuilder = new StringBuilder();
        new CategoryBuilder(categories).build().getLink(stringBuilder, "");
        assertEquals("1|12|123|1234|12345|123456|1234567|12345678|123456789|", stringBuilder.toString());
    }


}
