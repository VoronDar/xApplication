package com.astery.xapplication.ui.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.astery.xapplication.R;
import com.astery.xapplication.pojo.Answer;
import com.astery.xapplication.pojo.Event;
import com.astery.xapplication.pojo.serialazable.EventDescription;
import com.astery.xapplication.repository.Repository;
import com.astery.xapplication.repository.listeners.GetItemListener;
import com.astery.xapplication.repository.listeners.JobListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ConstantConditions")
public class CalendarViewModel extends ViewModel {
    private final MutableLiveData<Calendar> selectedDay;
    private final MutableLiveData<List<Event>> events;
    private final MutableLiveData<Integer> selectedEvent;
    private Repository repository;

    public LiveData<Calendar> getSelectedDay(){
        return selectedDay;
    }

    public MutableLiveData<List<Event>> getEvents() {
        return events;
    }

    public MutableLiveData<Integer> getSelectedEvent() {
        return selectedEvent;
    }

    public Repository getRepository() {
        return repository;
    }



    public CalendarViewModel() {
        super();

        selectedDay = new MutableLiveData<>();
        selectedDay.setValue(clean(GregorianCalendar.getInstance()));
        events = new MutableLiveData<>();
        selectedEvent = new MutableLiveData<>();
    }

    /** prepare calendar to be converted to date to be converted to long in sql*/
    private Calendar clean(Calendar cal){
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
        return cal;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;

        // TODO remove these items
       repository.addEvent(new Event("13", "t", new EventDescription(null), selectedDay.getValue().getTime()),
                success -> Log.i("main", success + " done"));
        repository.addEvent(new Event("123", "t", new EventDescription(null), selectedDay.getValue().getTime()),
                success -> Log.i("main", success + " done"));
        repository.addEvent(new Event("111", "t", new EventDescription(null), selectedDay.getValue().getTime()),
                success -> Log.i("main", success + " done"));


        Map<String, String> properties = new HashMap<>();
        properties.put("value", "ad");
        properties.put("value2", "ad2");

        repository.addEvent(new Event("1123", "t", new EventDescription(properties), selectedDay.getValue().getTime()),
                success -> Log.i("main", success + " done"));

        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("ad", "asdasdasd", "1", "qweqwe"));
        answers.add(new Answer("ad2", "22222", "2", "qweqwe"));

        repository.dataController.pushDataToLocal(answers, success -> Log.i("main", "answer done " + success), Answer.class);

    }


    /** change current date if the user changes month */
    public void changeMonth(boolean isMore){
        int month = selectedDay.getValue().get(Calendar.MONTH);
        int year = selectedDay.getValue().get(Calendar.YEAR);
        if (isMore) {
            month += 1;
            if (month < Calendar.JANUARY) {
                month = Calendar.DECEMBER;
                year-=1;
            }
        }
        else {
            month -= 1;
            if (month > Calendar.DECEMBER) {
                month = Calendar.JANUARY;
                year+=1;
            }
        }
        selectedDay.setValue(new GregorianCalendar(year, month, 1));


    }

    /** change current date if the user changes day */
    public void changeDay(int day){
        selectedDay.setValue(new GregorianCalendar(selectedDay.getValue().get(Calendar.YEAR),
                selectedDay.getValue().get(Calendar.MONTH), day));
    }

    /** get events for this day */
    public void updateEvents(){
        repository.loadEvents(selectedDay.getValue(), new GetItemListener<List<Event>>() {
            @Override
            public void getItem(List<Event> item) {
                events.setValue(addFirstItem(item));
            }

            @Override
            public void error() {
                events.setValue(addFirstItem(new ArrayList<>()));
            }
        });
    }

    /** get eventTemplate for this event */
    public void getTemplate(JobListener listener){
        Event event = events.getValue().get(selectedEvent.getValue());

        if (event.getTemplate() != null)
            listener.done(true);

        repository.getEventInfo(event, listener);
    }

    /** add empty unit in events list (it used for adding more events) */
    private List<Event> addFirstItem(List<Event> units){
        units.add(0, null);
        return units;
    }

    /** convert Calendar.get(MONTH) to String */
    public String getMonth(int calendar, Context context){
        switch (calendar){
            case Calendar.JANUARY:
                return context.getResources().getString(R.string.calendar_january);
            case Calendar.FEBRUARY:
                return context.getResources().getString(R.string.calendar_february);
            case Calendar.MARCH:
                return context.getResources().getString(R.string.calendar_march);
            case Calendar.APRIL:
                return context.getResources().getString(R.string.calendar_april);
            case Calendar.MAY:
                return context.getResources().getString(R.string.calendar_may);
            case Calendar.JUNE:
                return context.getResources().getString(R.string.calendar_june);
            case Calendar.JULY:
                return context.getResources().getString(R.string.calendar_july);
            case Calendar.AUGUST:
                return context.getResources().getString(R.string.calendar_august);
            case Calendar.SEPTEMBER:
                return context.getResources().getString(R.string.calendar_september);
            case Calendar.OCTOBER:
                return context.getResources().getString(R.string.calendar_october);
            case Calendar.NOVEMBER:
                return context.getResources().getString(R.string.calendar_november);
            case Calendar.DECEMBER:
                return context.getResources().getString(R.string.calendar_december);
            default:
                throw new RuntimeException("CalendarViewModel.getMonth got invalid calendar " + calendar);
        }
    }

}
