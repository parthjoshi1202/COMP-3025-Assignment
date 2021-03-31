package com.example.comp3025assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.comp3025assignment.databinding.ActivityMainBinding

//Not being used at the moment
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}