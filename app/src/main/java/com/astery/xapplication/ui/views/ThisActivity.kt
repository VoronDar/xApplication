package com.astery.xapplication.ui.views

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
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

        setSupportActionBar(binding.toolbar)
        pushFragment()
        //supportActionBar?.title = "title"
    }




    private fun prepareSearchView(hide:Boolean){
        if (hide){
            binding.searchView.visibility = GONE
        }else{
            binding.searchView.visibility = VISIBLE

            binding.searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {

                    this@ThisActivity.currentFocus.let { view ->
                        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                        imm?.hideSoftInputFromWindow(view?.windowToken, 0)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })

            binding.searchView.setOnCloseListener {
                this.currentFocus?.let { view ->
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(view.windowToken, 0)
                }

                return@setOnCloseListener true
            }
        }
    }

    private fun pushFragment(){
        navController = FragmentNavController.CALENDAR
        currentFragment = navController.thisFragment

        fragmentManager = supportFragmentManager
        val ft = fragmentManager.beginTransaction()
        ft.add(R.id.hostFragment, currentFragment)
        ft.commit()
    }

    override fun move(action: FragmentNavController, bundle: Bundle?) {
        navController = action
        val newFragment = navController.thisFragment
        newFragment.arguments = bundle

        currentFragment.setTransition(navController.transition)
        newFragment.setTransition(navController.transition.reverseCopy())

        val ft = fragmentManager.beginTransaction()
        ft.replace(R.id.hostFragment, newFragment)
        ft.commit()

        currentFragment = newFragment
    }

    override fun changeTitle(title: String) {
        supportActionBar?.title = title;
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)
    }


    override fun onSupportNavigateUp(): Boolean {
        //val navController =
        //return navController.navigateUp(appBarConfiguration)
        //        || super.onSupportNavigateUp()
        return true
    }
}