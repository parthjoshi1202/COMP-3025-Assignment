package com.lh1126914.comp3025assignment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

/**
 * Recycler View Adapter for Exercise, adapts to the data stored
 */


class RecyclerViewAdapter(
    val context: Context,
    val exercises: List<Exercise>
) :RecyclerView.Adapter<RecyclerViewAdapter.ExerciseViewHolder>()
{
        inner class ExerciseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val exerciseNameRecyclerTextView=itemView.findViewById<TextView>(R.id.exerciseNameRecyclerTextView) //addExerciseName
            val recyclerAddRepetitionTextView=itemView.findViewById<TextView>(R.id.recyclerAddRepetitionTextView) //addRepetitions
            val recyclerAddInstructionsTextView=itemView.findViewById<TextView>(R.id.recyclerAddInstructionsTextView) //addInstructions
            val recyclerAddNotesTextView=itemView.findViewById<TextView>(R.id.recyclerAddNotesTextView) //addNotes
            val recyclerAddMediaTextView=itemView.findViewById<TextView>(R.id.recyclerAddMediaTextView)
        }

    override fun getItemCount(): Int {
        return exercises.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val inflater =LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise= exercises[position]
        with(holder) {
            exerciseNameRecyclerTextView.text=exercise.exercise_name
            recyclerAddRepetitionTextView.text=exercise.repetitions
            recyclerAddInstructionsTextView.text=exercise.instructions
            recyclerAddNotesTextView.text=exercise.notes
            recyclerAddMediaTextView.text=exercise.media
        }
    }
}