package com.example.comp3025assignment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ExerciseListViewModel: ViewModel() {

    private val exercises=MutableLiveData<List<Exercise>>()

    init {
        loadExercises()
    }

    fun getExercises(): LiveData<List<Exercise>> {
        return exercises
    }

    private fun loadExercises() {
        val db=FirebaseFirestore.getInstance().collection("exercise")
                .orderBy("exercise_name", Query.Direction.ASCENDING)

        db.addSnapshotListener { documents, exception ->
            Log.i("DB_RESPONSE","elements returned ${documents?.size()}")

            if(exception!=null) {
                Log.w("DB_RESPONSE","Listen failed",exception)
                return@addSnapshotListener
            }

            val exerciseList=ArrayList<Exercise>()

            documents?.let {
                for(document in documents) {
                    val exercise=document.toObject(Exercise::class.java)
                    exerciseList.add(exercise)
                }
                exercises.value=exerciseList
            }
        }
    }

}