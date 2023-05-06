package com.example.agroceylon.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agroceylon.R
import com.example.agroceylon.adapters.ProductAdapter
import com.example.agroceylon.databinding.ActivityProductFetchingBinding
import com.example.agroceylon.models.ProductModel
import com.google.firebase.database.*


class ProductFetchingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductFetchingBinding
    private lateinit var productRecyclerView: RecyclerView
    private lateinit var tvLoadingDat:TextView
    private lateinit var productList: ArrayList<ProductModel>
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductFetchingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productRecyclerView = findViewById(R.id.rvProducts)
        productRecyclerView.layoutManager = LinearLayoutManager(this)
        productRecyclerView.setHasFixedSize(true)
        tvLoadingDat = findViewById(R.id.tvLoadingData)

        productList = arrayListOf<ProductModel>()

        getProductsData()

        binding.btn5Add.setOnClickListener{
            val intent = Intent(this@ProductFetchingActivity, ProductInsertionActivity::class.java)
            startActivity(intent)

        }
    }

    private fun getProductsData(){
        productRecyclerView.visibility = View.GONE
        tvLoadingDat.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Products")
        
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()
                if(snapshot.exists()){
                    for(productSnap in snapshot.children){
                        val productData = productSnap.getValue(ProductModel::class.java)
                        productList.add(productData!!)
                    }
                    val pAdapter = ProductAdapter(productList)
                    productRecyclerView.adapter = pAdapter

                    pAdapter.setOnItemClickListner(object : ProductAdapter.onItemClickListner{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@ProductFetchingActivity,ProductDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("productID",productList[position].productID)
                            intent.putExtra("productName",productList[position].productName)
                            intent.putExtra("productLocation",productList[position].productLocation)
                            intent.putExtra("productPrice",productList[position].productPrice)
                            intent.putExtra("productQuantity",productList[position].productQuantity)
                            intent.putExtra("productDate",productList[position].productDate)
                            startActivity(intent)
                        }

                    })

                    productRecyclerView.visibility = View.VISIBLE
                    tvLoadingDat.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }


}