package com.lh1126914.comp3025assignment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth


//taken from https://firebase.google.com/docs/auth/android/firebaseui?authuser=0
@Suppress("DEPRECATION")
class SignInActivity: AppCompatActivity() {
        val RC_SIGN_IN=1234
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_sign_in)


            // Choose authentication providers
            val providers = arrayListOf(
                    AuthUI.IdpConfig.EmailBuilder().build(),
                    //AuthUI.IdpConfig.PhoneBuilder().build(),
                    AuthUI.IdpConfig.GoogleBuilder().build())
                    //AuthUI.IdpConfig.FacebookBuilder().build(),
                    //AuthUI.IdpConfig.TwitterBuilder().build())

            // Create and launch sign-in intent
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN)
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                val intent=Intent(this, ExerciseRecyclerViewActivity::class.java) // AddActivity
                startActivity(intent)
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                Toast.makeText(this, "Sign-in Failed", Toast.LENGTH_LONG).show()
            }
        }
    }
}