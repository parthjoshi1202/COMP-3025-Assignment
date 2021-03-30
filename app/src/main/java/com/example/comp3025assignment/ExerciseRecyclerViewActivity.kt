package com.example.comp3025assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.comp3025assignment.databinding.ActivityExerciseRecyclerViewBinding
import com.example.comp3025assignment.databinding.ActivityMainBinding

class ExerciseRecyclerViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciseRecyclerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExerciseRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}