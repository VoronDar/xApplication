package com.astery.xapplication.ui.navigation;

import android.os.Bundle;

import com.astery.xapplication.ui.views.CalendarFragment;
import com.astery.xapplication.ui.views.SecondFragment;
import com.astery.xapplication.ui.views.XFragment;
import com.google.android.material.transition.MaterialSharedAxis;

/** navigation controller
 * fragments can start another fragment not with calling this fragment itself, but starting action.
 * */
public enum FragmentNavController {
    CALENDAR {
        @Override
        public XFragment getThisFragment() {
            return new CalendarFragment();
        }

        @Override
        public FragmentNavController getParent() {
            return null;
        }

        @Override
        public NavigationTransition getTransition() {
            return new SharedAxisTransition().setAxis(MaterialSharedAxis.Z).setFirstParent(true);
        }
        @Override
        public void checkFriends(FragmentNavController controller) {
            if (controller != null)
                super.checkFriends(controller);
        }
    },
    ADD_EVENT {
        @Override
        public XFragment getThisFragment() {
            return new SecondFragment();
        }
        @Override
        public FragmentNavController getParent() {
            return CALENDAR;
        }
        @Override
        public NavigationTransition getTransition() {
            return new SharedAxisTransition().setAxis(MaterialSharedAxis.Z).setFirstParent(false);
        }
        @Override
        public void checkFriends(FragmentNavController controller) {
            if (controller != FragmentNavController.CALENDAR)
                super.checkFriends(controller);
        }
    },
    GET_A_TIP{
        @Override
        public XFragment getThisFragment() {
            return new SecondFragment();
        }
        @Override
        public FragmentNavController getParent() {
            return CALENDAR;
        }
        @Override
        public NavigationTransition getTransition() {
            return new SharedAxisTransition().setAxis(MaterialSharedAxis.Z).setFirstParent(false);
        }
        @Override
        public void checkFriends(FragmentNavController controller) {
            if (controller != FragmentNavController.CALENDAR)
                super.checkFriends(controller);
        }
    };


    /** transition settings. It may be useful if it's required to get action from different places*/
    protected Bundle transitionBundle;

    FragmentNavController(Bundle transitionBundle) {
        this.transitionBundle = transitionBundle;
    }

    FragmentNavController() {
    }


    /** get XFragment object for this action */
    public abstract XFragment getThisFragment();
    /** get XFragment when back pressed */
    public abstract FragmentNavController getParent();
    /** get transition to transform to this action*/
    public abstract NavigationTransition getTransition();

    /** throws runtime exception if the action started from wrong fragment. This method prevent you from creating new communication
     * with two fragment without declaring it here */
    public void checkFriends(FragmentNavController controller){
        throw new RuntimeException(this.name() + " started from wrong fragment " + controller.name());
    }


}
