package com.example.comp3025assignment

import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.provider.FontsContractCompat.FontRequestCallback.RESULT_OK
import com.example.comp3025assignment.databinding.ActivityAddBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference

//adding an exercise , the upload image functionality does not work at the moment
class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private var imageUri: Uri? = null

    //private var downloadImageUrl: String? = null
    //private var ProductImagesRef: StorageReference? = null
    //private var ProductsRef: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //binding.chooseButton.setOnClickListener {
         //   chooseImage()
        //}

        //ensuring that all fields are populated
        binding.addExerciseButton.setOnClickListener {

            if (binding.addExerciseName.text.toString().isNotEmpty() && binding.addRepetitions.text.toString().isNotEmpty() &&
                    binding.addInstructions.text.toString().isNotEmpty() && binding.addNotes.text.toString().isNotEmpty()) {
                   // && imageUri.toString().isNotEmpty()

                val exercise = Exercise()
                exercise.exercise_name = binding.addExerciseName.text.toString()
                exercise.repetitions = binding.addRepetitions.text.toString()
                exercise.instructions = binding.addInstructions.text.toString()
                exercise.notes = binding.addNotes.text.toString()


                // taken from https://www.youtube.com/watch?v=emDhMx_2-1E&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=13
                /*val filePath = ProductImagesRef!!.child(imageUri!!.toString())
                val uploadTask = filePath.putFile(imageUri!!)
                uploadTask.addOnFailureListener { e ->
                    val message = e.toString()
                    Toast.makeText(this, "Error: $message", Toast.LENGTH_SHORT).show()
                }.addOnSuccessListener {
                    Toast.makeText(this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show()
                    val urlTask = uploadTask.continueWithTask { task ->
                        if (!task.isSuccessful) {
                            throw task.exception!!
                        }
                        downloadImageUrl = filePath.downloadUrl.toString()
                        filePath.downloadUrl
                    }.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            downloadImageUrl = task.result.toString()
                            Toast.makeText(this, "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                exercise.media = downloadImageUrl.toString()

                 */

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
    }

    //opens gallery in user's phone + filtering type of media which is "image"
    private fun chooseImage() {

        val intent=Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent,galleryReq)

    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // compare the resultCode with the request code
        if (requestCode == galleryReq && resultCode== Activity.RESULT_OK && data!=null) {
            // Get the url of the image from data
            imageUri = data.data
            binding.exerciseImageView!!.setImageURI(imageUri)
        }

    }*/

    //request code to open gallery in the phone
    companion object {
        private const val galleryReq = 1
    }



}