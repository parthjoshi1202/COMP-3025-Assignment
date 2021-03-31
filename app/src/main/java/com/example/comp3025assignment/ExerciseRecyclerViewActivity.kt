package com.example.comp3025assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.comp3025assignment.databinding.ActivityExerciseRecyclerViewBinding
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Recycler View Activity for Exercise
 */

class ExerciseRecyclerViewActivity : AppCompatActivity(){

    private lateinit var binding: ActivityExerciseRecyclerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExerciseRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val model: ExerciseListViewModel by viewModels()
        model.getExercises().observe(this, Observer<List<Exercise>>{ exercises->
            var recyclerAdapter=RecyclerViewAdapter(this, exercises)
            binding.recyclerViewVertical.adapter=recyclerAdapter

        })
    }
}