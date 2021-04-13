package com.lh1126914.comp3025assignment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.lh1126914.comp3025assignment.databinding.ActivityAddBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


//adding an exercise , the upload image functionality does not work at the moment
class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private val authDb= FirebaseAuth.getInstance()

    @SuppressLint("QueryPermissionNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ensuring that all fields are populated
        binding.addExerciseButton.setOnClickListener {

            if (binding.addExerciseName.text.toString().isNotEmpty() && binding.addRepetitions.text.toString().isNotEmpty() &&
                    binding.addInstructions.text.toString().isNotEmpty() && binding.addNotes.text.toString().isNotEmpty()
                && binding.addMedia.text.toString().isNotEmpty())  {


                val exercise = Exercise()
                exercise.exercise_name = binding.addExerciseName.text.toString()
                exercise.repetitions = binding.addRepetitions.text.toString()
                exercise.instructions = binding.addInstructions.text.toString()
                exercise.notes = binding.addNotes.text.toString()
                exercise.media = binding.addMedia.text.toString()

                val db = FirebaseFirestore.getInstance().collection("exercise")
                exercise.exercise_id = db.document().id

                //pushing it to db
                db.document(exercise.exercise_id!!).set(exercise)
                        .addOnSuccessListener {

                            //confirmed and clear the fields
                            Toast.makeText(this, "Exercise Added Successfully", Toast.LENGTH_LONG).show()

                            //now back to recycler view
                            val intent = Intent(this, ExerciseRecyclerViewActivity::class.java)
                            startActivity(intent)
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                        }
            } else {
                Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_LONG).show()
            }
        }

        setSupportActionBar(binding.mainToolBar.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_add-> {
                //startActivity(Intent(applicationContext, AddActivity::class.java))
                return true
            }
            R.id.action_list-> {
                startActivity(Intent(applicationContext, ExerciseRecyclerViewActivity::class.java))
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