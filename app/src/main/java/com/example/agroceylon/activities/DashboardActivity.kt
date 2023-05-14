package com.example.agroceylon.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.example.agroceylon.R
import com.example.agroceylon.databinding.ActivityDashboardBinding



class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        @Suppress("DEPRECATION")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else{
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
            )
        }

        binding.view8.setOnClickListener{

            val intent = Intent(this@DashboardActivity, BuyerFetchingActivity::class.java)
            startActivity(intent)

        }

        binding.view9.setOnClickListener{

            val intent = Intent(this@DashboardActivity, FetchingToolsActivity::class.java)
            startActivity(intent)

        }

        binding.view11.setOnClickListener{

            val intent = Intent(this@DashboardActivity, UserProfileActivity::class.java)
            startActivity(intent)

        }

        binding.view7.setOnClickListener{

            val intent = Intent(this@DashboardActivity, ProductFetchingActivity::class.java)
            startActivity(intent)

        }

        binding.view7.setOnClickListener{

            val intent = Intent(this@DashboardActivity, ProductFetchingActivity::class.java)
            startActivity(intent)

        }
        binding.view10.setOnClickListener{

            val intent = Intent(this@DashboardActivity, TransporterFetchingActivity::class.java)
            startActivity(intent)

        }












    }
}