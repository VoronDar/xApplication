package com.astery.xapplication.ui.navigation

import android.os.Bundle
import com.astery.xapplication.ui.views.CalendarFragment

/**
 *
 * */
interface ParentActivity {
    fun move(action: FragmentNavController, bundle: Bundle?)
    fun changeTitle(title:String)
    fun showSearch(show:Boolean)
    fun showMenuNav(show:Boolean, listener:CalendarFragment.MenuNavListener)
}