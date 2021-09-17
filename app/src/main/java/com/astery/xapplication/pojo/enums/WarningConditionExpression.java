package com.astery.xapplication.pojo.enums;

import com.astery.xapplication.pojo.Event;

import java.util.List;

public enum WarningConditionExpression{
    AT_LEAST{ // alert if at least <time> times in a <period>
        @Override
        public boolean check(int time) {
            return allEvents.size() >= time;
        }
    },
    NOT_MANY{ // alert if lesser then <time> times in a <period>,
        @Override
        public boolean check(int time) {
            return allEvents.size() < time;
        }
    },
    AT_LEAST_PERCENT{ // alert if at least <time>% times in a <period>
        @Override
        public boolean check(int time) {
            return allEvents.size() < time;
        }
    },
    NOT_MANY_PERCENT{  // alert if lesser then <time>% times in a <period>
        @Override
        public boolean check(int time) {
            return allEvents.size() < time;
        }
    },
    AT_LEAST_PROPERTY_VALUES{ // alert if property <propertyKey> value in <value> more then <time>% times in all events.
        @Override
        public boolean check(int time) {
            int count = 0;
            for (Event e: allEvents){
                String value = e.getEventDescription().getProperties().get(propertyKey);
                if (value != null && values.contains(value))
                    count+=1;
            }
            if (count == 0)
                return false;
            return (100*(allEvents.size()/count) >= time);
        }
    },
    NOT_MANY_PROPERTY_VALUES{ // alert if property <propertyKey> value in <value> less then <time>% times in all events.
        @Override
        public boolean check(int time) {
            int count = 0;
            for (Event e: allEvents){
                String value = e.getEventDescription().getProperties().get(propertyKey);
                if (value != null && values.contains(value))
                    count+=1;
            }
            if (count == 0)
                return false;
            return (100*(allEvents.size()/count) < time);
        }
    };

    protected String propertyKey;
    protected List<String> values;

    protected List<Event> allEvents;


    public abstract boolean check(int time);

    public void setAllEvents(List<Event> allEvents) {
        this.allEvents = allEvents;
    }

    public void setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}