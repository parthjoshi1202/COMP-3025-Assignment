package com.lh1126914.comp3025assignment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.FileProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.lh1126914.comp3025assignment.databinding.ActivityAddBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File
import java.io.FileInputStream

//adding an exercise , the upload image functionality does not work at the moment
class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private val authDb= FirebaseAuth.getInstance()

    //variable for exercise picture
    private val REQUEST_CODE=1000
    private val PERMISSION_CODE = 1001
    private lateinit var filePhoto: File
    private val FILE_NAME="photo"


    private val chosenImageUri= mutableListOf<Uri>()

    @SuppressLint("QueryPermissionNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (checkSelfPermission(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED) || checkSelfPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                    arrayOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    1
            )
        }

         */

        binding.chooseButton.setOnClickListener {
                //chooseImage()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                } else{
                    chooseImage();
                }
            }else{
                chooseImage();
            }
        }


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

    //opens gallery in user's phone + filtering type of media which is "image"
    private fun chooseImage() {

        //create intent to navigate to the camera
        val intent=Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,REQUEST_CODE)
        //startActivityForResult(Intent.createChooser(intent, "Choose pics"), 1002)

        //add the file location to the intent
        /*filePhoto=getPhotoFile(FILE_NAME)
        val providerFile = FileProvider.getUriForFile(this,
                "com.lh1126914.comp3025assignment.fileProvider",
                filePhoto)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, providerFile)

        //this will run the intent to open the gallery
        if(intent.resolveActivity(this.packageManager)!=null) {
            startActivityForResult(intent, REQUEST_CODE)
            intent.setAction(Intent.ACTION_GET_CONTENT)
        }

         */


        //else
         //   Toast.makeText(this, "Gallery did not open", Toast.LENGTH_LONG).show()

    //val intent=Intent(Intent.ACTION_PICK) //it was () originally
        //intent.type = "image/*"
        //startActivityForResult(intent, REQUEST_CODE)
    //intent.setType("image/*")
        //intent.setAction(Intent.ACTION_GET_CONTENT)
        //startActivityForResult(intent,galleryReq)

    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    chooseImage()
                }else{
                    Toast.makeText(this,"Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //This method will return the file object for the picture (the actual .jpg)
    private fun getPhotoFile(fileName: String) : File{
        val directoryStorage = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", directoryStorage)
    }

    //if the Intent successfully took a photo, convert the photo to a bitmap and display in the imageview
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /*if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val takenPhoto = BitmapFactory.decodeFile(filePhoto.absolutePath)
            binding.exerciseImageView.setImageBitmap(takenPhoto)

            //try saving the photoURI to the user's firebase profile for persistence
            //convert the file path from a String to URI before saving
            var builder = Uri.Builder()
            var localUri = builder.appendPath(filePhoto.absolutePath).build()
            saveProfilePhoto(localUri)
        }
        else
        //this never really excecutes...
            super.onActivityResult(requestCode, resultCode, data)

         */
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            binding.exerciseImageView.setImageURI(data?.data)
        }
    }

    //This method will save the image and update Firebase to know where to find it.
    private fun saveProfilePhoto(imageUri: Uri?){
        var profileUpdates = UserProfileChangeRequest.Builder()
                .setPhotoUri(imageUri)
                .build()
        //Commit the update to Firebase. This runs ascynronously as a task
        authDb.currentUser?.updateProfile(profileUpdates)?.addOnCompleteListener{
            OnCompleteListener<Void?> {
                if (it.isSuccessful)
                    Toast.makeText(this, "Exercise Updated", Toast.LENGTH_LONG).show()
            }
        }
    }

    //load the profile photo into the imageview
    private fun loadProfileImage(pathToImage: String) {
        var file: File=File(pathToImage)

        //convert to bitmap
        var bitmapImage=BitmapFactory.decodeStream(FileInputStream(file))

        //display in the image view
        binding.exerciseImageView.setImageBitmap(bitmapImage)
    }



    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            binding.exerciseImageView.setImageURI(data?.data)
        }
    }

     */

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // compare the resultCode with the request code
        if (requestCode == galleryReq && resultCode== Activity.RESULT_OK && data!=null) {
            // Get the url of the image from data
            imageUri = data.data
            binding.exerciseImageView!!.setImageURI(imageUri)
        }

    }*/

}