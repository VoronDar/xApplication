package com.astery.xapplication.ui.navigation;

import com.google.android.material.transition.MaterialSharedAxis;

/** actions that FragmentNavController accept to change fragments */
public enum NavigationAction {
    ADD_EVENT{
        @Override
        public NavigationTransition getTransition() {
            return new SharedAxisTransition().setAxis(MaterialSharedAxis.Z).setFirstParent(true);
        }
    },
    SUBMIT_EVENT{
        @Override
        public NavigationTransition getTransition() {
            return new SharedAxisTransition().setAxis(MaterialSharedAxis.Z).setFirst(true);
        }
    };

    /** get transition (animation) for this type */
    public abstract NavigationTransition getTransition();

}
