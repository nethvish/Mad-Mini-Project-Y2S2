package com.example.agroceylon.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agroceylon.R
import com.example.agroceylon.adapters.TransporterAdapter
import com.example.agroceylon.databinding.ActivityTransporterFetchingBinding
import com.example.agroceylon.models.TransporterModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TransporterFetchingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransporterFetchingBinding

    private lateinit var transpRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var transpList: ArrayList<TransporterModel>
    private lateinit var dbRef: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransporterFetchingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        transpRecyclerView = findViewById(R.id.rvTransp)
        transpRecyclerView.layoutManager = LinearLayoutManager(this)
        transpRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        transpList = arrayListOf<TransporterModel>()

        getTransporterData()

        binding.btn4Add.setOnClickListener{
            val intent = Intent(this@TransporterFetchingActivity, TransporterInsertionActivity::class.java)
            startActivity(intent)

        }

    }

    private fun getTransporterData() {

        transpRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Transporter")



        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                transpList.clear()
                if (snapshot.exists()){
                    for (transpSnap in snapshot.children){
                        val transpData = transpSnap.getValue(TransporterModel::class.java)
                        transpList.add(transpData!!)
                    }
                    val mAdapter = TransporterAdapter(transpList)
                    transpRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : TransporterAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@TransporterFetchingActivity, TransporterDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("trId", transpList[position].trId)
                            intent.putExtra("trfName", transpList[position].trfName)
                            intent.putExtra("vehiNumber", transpList[position].vehiNumber)
                            intent.putExtra("vehiId", transpList[position].vehiId)
                            intent.putExtra("trMobile", transpList[position].trMobile)
                            intent.putExtra("trLocation", transpList[position].trLocation)

                            startActivity(intent)
                        }

                    })

                    transpRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}

