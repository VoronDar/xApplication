package com.astery.xapplication.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.astery.xapplication.pojo.Event;
import com.astery.xapplication.pojo.Item;
import com.astery.xapplication.pojo.Question;
import com.astery.xapplication.repository.Repository;
import com.astery.xapplication.ui.adapters.units.AdvicesUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TipsViewModel extends ViewModel {
    private Event event;
    private final MutableLiveData<List<AdvicesUnit>> units;

    public TipsViewModel() {
        units = new MutableLiveData<>();
    }

    public LiveData<List<AdvicesUnit>> getUnits() {
        return units;
    }

    public Event getEvent() {
        return event;
    }

    public Repository getRepository() {
        return repository;
    }

    private Repository repository;

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public void setEvent(Event event){
        this.event = event;
    }

    public void getTips(){
        repository.getEventInfoForTips(event, success -> {
            if (success){
                ArrayList<AdvicesUnit> units = new ArrayList<>();
                for (Question question: event.getTemplate().getQuestions()){
                    if (question.getSelectedAnswer().getItem() != null){
                        units.add(new AdvicesUnit(question.getText(), question.getSelectedAnswer().getText(),
                                question.getSelectedAnswer().getItemOb().getAdvises(), question.getSelectedAnswer().getItem()));
                    }
                }
                TipsViewModel.this.units.setValue(units);
            }
        });
    }


    public Item getItemByPositionInUnits(int position){
        String itemId = Objects.requireNonNull(units.getValue()).get(position).getItemId();
        for (Question question: event.getTemplate().getQuestions()){
            if (question.getSelectedAnswer().getItem() == null)
                continue;
            if (question.getSelectedAnswer().getItem().equals(itemId)){
                return question.getSelectedAnswer().getItemOb();
            }
        }
        throw new RuntimeException("TipsViewModel.getItemByPositionInUnits got strange position = "+  position
        + "\nunits - " + units.getValue().toString() + "\nevent - " + event.toString());
    }


}
