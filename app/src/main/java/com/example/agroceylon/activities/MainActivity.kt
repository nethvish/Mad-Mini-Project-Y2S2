package com.example.agroceylon.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.agroceylon.R
import com.example.agroceylon.utils.Constants
import com.example.agroceylon.databinding.ActivityMainBinding

//class MainActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityMainBinding
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val sharedPref = getSharedPreferences(Constants.AGRO_PREFERENCES, Context.MODE_PRIVATE)
//        val userName = sharedPref.getString(Constants.LOGGED_IN_USERNAME,"")!!
//
//        binding.tvMain.text = "Hello $userName"
//
//    }
//
//}


class MainActivity : AppCompatActivity() {

    private lateinit var btnInsertData: Button
    private lateinit var btnFetchData: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnInsertData = findViewById(R.id.btnInsertData)
        btnFetchData = findViewById(R.id.btnFetchData)

        btnInsertData.setOnClickListener {
            val intent = Intent(this, ToolInsertionActivity::class.java)
            startActivity(intent)
        }

        btnFetchData.setOnClickListener {
            val intent = Intent(this, FetchingToolsActivity::class.java)
            startActivity(intent)
        }

    }
}