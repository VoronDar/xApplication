package com.astery.xapplication.ui.adapters.units;


import com.astery.xapplication.pojo.Advise;

import java.util.ArrayList;

public class AdvicesUnit {
    private final String question;
    private final String answer;
    private final ArrayList<Advise> advices;
    private final String itemId;

    public AdvicesUnit(String question, String answer, ArrayList<Advise> advices, String itemId) {
        this.question = question;
        this.answer = answer;
        this.advices = advices;
        this.itemId = itemId;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public ArrayList<Advise> getAdvices() {
        return advices;
    }

    public String getItemId() {
        return itemId;
    }
}
