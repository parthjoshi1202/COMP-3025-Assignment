package com.lh1126914.comp3025assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.lh1126914.comp3025assignment.databinding.ActivityExerciseRecyclerViewBinding
import com.google.firebase.auth.FirebaseAuth

/**
 * Recycler View Activity for Exercise
 */

class ExerciseRecyclerViewActivity : AppCompatActivity(){

    private lateinit var binding: ActivityExerciseRecyclerViewBinding
    private val authDb= FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExerciseRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val model: ExerciseListViewModel by viewModels()
        model.getExercises().observe(this, Observer<List<Exercise>>{ exercises->
            var recyclerAdapter=RecyclerViewAdapter(this, exercises)
            binding.recyclerViewVertical.adapter=recyclerAdapter

        })

        setSupportActionBar(binding.mainToolBar.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_add-> {
                startActivity(Intent(applicationContext, AddActivity::class.java))
                return true
            }
            R.id.action_list-> {
                //startActivity(Intent(applicationContext, ExerciseRecyclerViewActivity::class.java))
                return true
            }
            R.id.action_search-> {
                //startActivity(Intent(applicationContext, AddActivity::class.java))
                return true
            }
            R.id.your_exercises-> {
                //startActivity(Intent(applicationContext, AddActivity::class.java))
                return true
            }
            R.id.app_bar_logout-> {
                authDb.signOut()
                finish()
                startActivity(Intent(applicationContext, SignInActivity::class.java))
                //return true
            }
        }
        return  super.onOptionsItemSelected(item)
    }

}