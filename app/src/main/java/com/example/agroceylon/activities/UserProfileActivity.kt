package com.example.agroceylon.activities

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.example.agroceylon.databinding.ActivityUserProfileBinding
import com.example.agroceylon.models.UserModel
import com.example.agroceylon.utils.Constants
import java.io.IOException

class UserProfileActivity : AppCompatActivity(){

    private lateinit var binding: ActivityUserProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var userModelDetails: UserModel = UserModel()

        @Suppress("DEPRECATION")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else{
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
            )
        }

        if(intent.hasExtra(Constants.EXTRA_USER_DETAILS)){
            //get the user details from intent as a parcelableExtra
            userModelDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }

        binding.etFirstName.isEnabled = false
        binding.etFirstName.setText(userModelDetails.firstName)

        binding.etLastName.isEnabled = false
        binding.etLastName.setText(userModelDetails.lastName)

        binding.etEmail.isEnabled = false
        binding.etEmail.setText(userModelDetails.email)

    }

}

