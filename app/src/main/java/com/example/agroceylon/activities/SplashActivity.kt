package com.example.agroceylon.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.example.agroceylon.R

class SplashActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //getting rid of top bar for splash activity
        //method 1 deprecated code (Not recommended)
        //window.setFlags(
        //WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //WindowManager.LayoutParams.FLAG_FULLSCREEN
        //)

        //method 2 (recommended)
        @Suppress("DEPRECATION")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else{
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
            )
        }

        //directing to main activity after splash screen with a delay
        @Suppress("DEPRECATION")
        Handler().postDelayed(
            {
                //launch the main activity
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()//call this when the activity is done and should be closed.
            }, 2500
        )

    }
}