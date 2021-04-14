package com.lh1126914.comp3025assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.lh1126914.comp3025assignment.databinding.ActivityUserProfileBinding

class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding
    private val authDb=FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(authDb.currentUser==null)
            logout()

        //displaying username and email address
        authDb.currentUser?.let { user->
            binding.userNameTextView.text=user.displayName
            binding.emailTextView.text=user.email
        }

        binding.backButton.setOnClickListener{
            startActivity(Intent(applicationContext, ExerciseRecyclerViewActivity::class.java))
        }

        binding.logOutButton.setOnClickListener{
            logout()
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
                startActivity(Intent(applicationContext, AddActivity::class.java))
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
            R.id.app_bar_profile-> {
                //startActivity(Intent(applicationContext, UserProfileActivity::class.java))
                return true
            }
        }
        return  super.onOptionsItemSelected(item)
    }

    private fun logout() {
        authDb.signOut()
        finish()
        startActivity(Intent(applicationContext, SignInActivity::class.java))
    }
}