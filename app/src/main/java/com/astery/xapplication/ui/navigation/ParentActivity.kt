package com.astery.xapplication.ui.navigation

import android.os.Bundle

/**
 *
 * */
interface ParentActivity {
    fun move(action: FragmentNavController, bundle: Bundle?)
    fun changeTitle(title:String)
}