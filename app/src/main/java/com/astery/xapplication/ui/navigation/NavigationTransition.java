package com.astery.xapplication.ui.navigation;

public abstract class NavigationTransition {
    /** first = true will be applied for current fragment */
    protected boolean isFirst = true;
    public NavigationTransition setFirst(boolean isFirst){
        this.isFirst = isFirst;
        return this;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public abstract NavigationTransition reverseCopy();
}
