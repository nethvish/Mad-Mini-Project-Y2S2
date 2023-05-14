package com.example.agroceylon.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agroceylon.R
import com.example.agroceylon.models.BuyerPostModel
import com.google.android.material.chip.Chip


class BuyerAdapter(private val buyerPostList: ArrayList<BuyerPostModel>) :
    RecyclerView.Adapter<BuyerAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.buyer_list_item, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPost = buyerPostList[position]
        holder.tvBuyerPostTitle.text = currentPost.postTitle
        holder.tvBuyerPostLocation.text = currentPost.postLocation
        holder.tvBuyerPostQty.text = currentPost.postQty
        holder.chipBuyerPostType.text = currentPost.postType
        holder.tvBuyerPostName.text = currentPost.postName
        holder.tvBuyerPostPhone.text = currentPost.postDate
//        holder.ivBuyerPost.image = currentPost.postTitle


    }

    override fun getItemCount(): Int {
        return buyerPostList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val tvBuyerPostTitle : TextView = itemView.findViewById(R.id.tvBuyerPostTitle)
        val tvBuyerPostLocation : TextView = itemView.findViewById(R.id.tvBuyerPostLocation)
        val tvBuyerPostQty : TextView = itemView.findViewById(R.id.tvBuyerPostQty)
        val chipBuyerPostType : Chip = itemView.findViewById(R.id.chipBuyerPostType)
        val tvBuyerPostName : TextView = itemView.findViewById(R.id.tvBuyerPostName)
        val tvBuyerPostPhone : TextView = itemView.findViewById(R.id.tvBuyerPostPhone)
        val ivBuyerPost : ImageView = itemView.findViewById(R.id.ivBuyerPost)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }

}