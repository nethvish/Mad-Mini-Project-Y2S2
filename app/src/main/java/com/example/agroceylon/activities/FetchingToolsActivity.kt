package com.example.agroceylon.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agroceylon.R
import com.example.agroceylon.adapters.ToolPostAdapter
import com.example.agroceylon.databinding.ActivityFetchingToolsBinding
import com.example.agroceylon.databinding.ActivityToolDetailsBinding
import com.example.agroceylon.models.ToolPostModel
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class FetchingToolsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFetchingToolsBinding
    private lateinit var binding2: ActivityToolDetailsBinding

    private lateinit var toolRecyclerView: RecyclerView
    private lateinit var toolLoadingData: TextView
    private lateinit var toolList: ArrayList<ToolPostModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFetchingToolsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolRecyclerView = findViewById(R.id.rvTool)
        toolRecyclerView.layoutManager = LinearLayoutManager(this)
        toolRecyclerView.setHasFixedSize(true)
        toolLoadingData = findViewById(R.id.tvLoadingData)

        toolList = arrayListOf<ToolPostModel>()

        getToolsData()

        @Suppress("DEPRECATION")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else{
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
            )
        }

        binding.btnAdd.setOnClickListener{
            val intent = Intent(this@FetchingToolsActivity, ToolInsertionActivity::class.java)
            startActivity(intent)

        }

    }

    private fun getToolsData() {

        toolRecyclerView.visibility = View.GONE
        toolLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Tools")



        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                toolList.clear()
                if (snapshot.exists()){
                    for (toolSnap in snapshot.children){
                        val toolData = toolSnap.getValue(ToolPostModel::class.java)
                        toolList.add(toolData!!)
                    }
                    val mAdapter = ToolPostAdapter(toolList)
                    toolRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : ToolPostAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@FetchingToolsActivity, ToolDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("toolId", toolList[position].toolId)
                            intent.putExtra("toolTitle", toolList[position].toolTitle)
                            intent.putExtra("toolPrice", toolList[position].toolPrice)
                            intent.putExtra("toolLocation", toolList[position].toolLocation)
                            intent.putExtra("toolType", toolList[position].toolType)
                            //val currentTool = toolList[position]
                            // Picasso.get().load(currentTool.imgUri).into(binding2.ivToolPhoto)
                            startActivity(intent)
                        }

                    })

                    toolRecyclerView.visibility = View.VISIBLE
                    toolLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}