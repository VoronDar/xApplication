package com.astery.xapplication.ui.navigation

import android.os.Bundle

/**
 *
 * */
interface ParentActivity {
    fun move(action: NavigationAction, bundle: Bundle?)
}