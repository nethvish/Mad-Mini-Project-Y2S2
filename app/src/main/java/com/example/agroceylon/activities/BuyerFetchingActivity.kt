package com.example.agroceylon.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agroceylon.R
import com.example.agroceylon.adapters.BuyerAdapter
import com.example.agroceylon.databinding.ActivityBuyerDetailsBinding
import com.example.agroceylon.databinding.ActivityFetchingToolsBinding
import com.example.agroceylon.models.BuyerPostModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BuyerFetchingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFetchingBuyersBinding

    private lateinit var buyerRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var buyerPostList: ArrayList<BuyerPostModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFetchingToolsBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_buyer_fetching)

        buyerRecyclerView = findViewById(R.id.rvBuyerPosts)
        buyerRecyclerView.layoutManager = LinearLayoutManager(this)
        buyerRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        buyerPostList = arrayListOf<BuyerPostModel>()

        getBuyerPostData()

        binding.buyerAddNewPost.setOnClickListener{
            val intent = Intent(this@BuyerFetchingActivity, BuyerInsertionActivity::class.java)
            startActivity(intent)
        }
    }



    private fun getBuyerPostData() {

        buyerRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("BuyerPosts")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                buyerPostList.clear()
                if (snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val empData = empSnap.getValue(BuyerPostModel::class.java)
                        buyerPostList.add(empData!!)
                    }
                    val mAdapter = BuyerAdapter(buyerPostList)
                    buyerRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : BuyerAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            // add ActivityBuyerDetails
                            val intent = Intent(this@BuyerFetchingActivity, BuyerDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("postId", buyerPostList[position].postId)
                            intent.putExtra("postTitle", buyerPostList[position].postTitle)
                            intent.putExtra("postDate", buyerPostList[position].postDate)
                            intent.putExtra("postPrice", buyerPostList[position].postPrice)
                            intent.putExtra("postLocation", buyerPostList[position].postLocation)
                            intent.putExtra("postType", buyerPostList[position].postType)
                            intent.putExtra("postQty", buyerPostList[position].postQty)
                            intent.putExtra("postName", buyerPostList[position].postName)

                            startActivity(intent)
                        }

                    })

                    buyerRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}