package com.astery.xapplication.ui.navigation;

public class SharedAxisTransition extends NavigationTransition{
        private int axis;
        /** firstParent = true for cases when current fragment - parent */
        private boolean isFirstParent;
        public SharedAxisTransition setAxis(int axis){
            this.axis = axis;
            return this;
        }
        public int getAxis() {
            return axis;
        }

    public SharedAxisTransition setFirstParent(boolean isParent){
        this.isFirstParent = isParent;
        return this;
    }
    public boolean getFirstParent() {
        return isFirstParent;
    }


    @Override
    public NavigationTransition reverseCopy() {
       return new SharedAxisTransition().setAxis(axis);
    }
}
