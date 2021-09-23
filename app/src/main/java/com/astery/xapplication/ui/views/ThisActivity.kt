package com.astery.xapplication.ui.views

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.ui.AppBarConfiguration
import com.astery.xapplication.R
import com.astery.xapplication.databinding.ActivityMainBinding
import com.astery.xapplication.ui.navigation.FragmentNavController
import com.astery.xapplication.ui.navigation.ParentActivity

class ThisActivity : AppCompatActivity(), ParentActivity {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: FragmentNavController
    private lateinit var currentFragment: XFragment

    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar);


        pushFragment()

    }

    private fun pushFragment(){
        navController = FragmentNavController.CALENDAR
        currentFragment = navController.thisFragment

        fragmentManager = supportFragmentManager;
        val ft = fragmentManager.beginTransaction();
        ft.add(R.id.hostFragment, currentFragment);
        ft.commit();
    }

    override fun move(action: FragmentNavController, bundle: Bundle?) {
        navController = action;
        val newFragment = navController.thisFragment
        newFragment.arguments = bundle

        currentFragment.setTransition(navController.transition)
        newFragment.setTransition(navController.transition.reverseCopy())

        val ft = fragmentManager.beginTransaction();
        ft.replace(R.id.hostFragment, newFragment);
        ft.commit();

        currentFragment = newFragment
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    /*
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.host_fragment)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

     */
}