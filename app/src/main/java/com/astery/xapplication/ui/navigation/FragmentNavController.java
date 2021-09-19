package com.astery.xapplication.ui.navigation;

import com.astery.xapplication.ui.views.CalendarFragment;
import com.astery.xapplication.ui.views.SecondFragment;
import com.astery.xapplication.ui.views.XFragment;

/** navigation controller
 * allows to get this fragment object, previous fragment controller, children fragment controller (by desire)
 * */
public enum FragmentNavController {
    CALENDAR_FR{
        @Override
        public FragmentNavController getNextFragment(NavigationAction type) {
            switch (type){
                case ADD_EVENT:
                    return ADD_EVENT_FR;
                default:
                    return invalidType(type);
            }
        }
        @Override
        public XFragment getThisFragment() {
            return new CalendarFragment();
        }

        @Override
        public FragmentNavController getParent() {
            return null;
        }
    },
    ADD_EVENT_FR{
        @Override
        public FragmentNavController getNextFragment(NavigationAction type) {
            return null;
        }

        @Override
        public XFragment getThisFragment() {
            return new SecondFragment();
        }
        @Override
        public FragmentNavController getParent() {
            return CALENDAR_FR;
        }
    };

    public abstract FragmentNavController getNextFragment(NavigationAction type);
    public abstract XFragment getThisFragment();
    public abstract FragmentNavController getParent();

    protected FragmentNavController invalidType(NavigationAction type){
        throw new RuntimeException(this.getClass().getSimpleName() + " got invalid type - " + type.getClass().getSimpleName());
    }
}
