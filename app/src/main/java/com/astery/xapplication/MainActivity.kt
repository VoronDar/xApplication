package com.astery.xapplication

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.astery.xapplication.architecture.App
import com.astery.xapplication.data_source.local.database.db_utils.LocalLoadable
import com.astery.xapplication.databinding.ActivityMainBinding
import com.astery.xapplication.pojo.Advise
import com.astery.xapplication.pojo.Question
import com.astery.xapplication.pojo.only_for_db.QuestionEntity
import io.reactivex.observers.DisposableSingleObserver

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)


        // TODO make localDataSource private and delete it all
        val localDataSource = (application as App).container.localDataSource;


        localDataSource.loadQuestion(Question("12", "is it qorrect?", "123"), object: LocalLoadable{
            override fun onCompleteListener() {
                Log.i("main", "completed");
            }

            override fun onErrorListener() {
                Log.i("main", "error");
            }
        })


        binding.fab.setOnClickListener {
            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //    .setAction("Action", null).show()
            Log.i("main", "started");
            localDataSource.getQuestions("123", object : DisposableSingleObserver<List<Question?>?>() {
                override fun onSuccess(questions: List<Question?>) {
                    Log.i("main", questions.toString())
                }
                override fun onError(e: Throwable) {
                    Log.i("main", e.message.toString());
                }
            })
        }


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

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}