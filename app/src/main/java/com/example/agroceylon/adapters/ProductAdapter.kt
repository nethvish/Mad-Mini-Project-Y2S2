package com.example.agroceylon.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agroceylon.R
import com.example.agroceylon.models.ProductModel

class ProductAdapter(private val productList:ArrayList<ProductModel>):
    RecyclerView.Adapter<ProductAdapter.ViewHolder>(){

    private lateinit var pListner: onItemClickListner

    interface onItemClickListner {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListner(clickListner: onItemClickListner){
        pListner = clickListner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_list_item,parent,false)
        return ViewHolder(itemView,pListner)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val currentProduct = productList[position]
        holder.tvProductName.text = currentProduct.productName
        holder.tvProductLocation.text = currentProduct.productLocation
        holder.tvProductPrice.text = currentProduct.productPrice
        holder.tvProductQty.text = currentProduct.productQuantity
        holder.tvProductDate.text = currentProduct.productDate

    }

    override fun getItemCount(): Int {
       return productList.size
    }

    class ViewHolder(itemView: View, clickListner: onItemClickListner) : RecyclerView.ViewHolder(itemView){

        val tvProductName : TextView = itemView.findViewById(R.id.tvFarmerPostTitle)
        val tvProductLocation : TextView = itemView.findViewById(R.id.tvFarmerPostLocation)
        val tvProductPrice : TextView = itemView.findViewById(R.id.chipFarmerPostPrice)
        val tvProductQty : TextView = itemView.findViewById(R.id.tvFarmerPostQty)
        val tvProductDate : TextView = itemView.findViewById(R.id.tvFarmerPostDate)

        init {
            itemView.setOnClickListener {
                clickListner.onItemClick(adapterPosition)
            }
        }


    }


}