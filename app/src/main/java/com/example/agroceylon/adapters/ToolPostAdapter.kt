package com.example.agroceylon.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agroceylon.R
import com.example.agroceylon.models.ToolPostModel
import com.squareup.picasso.Picasso

class ToolPostAdapter (private val toolsList: ArrayList<ToolPostModel>) :
    RecyclerView.Adapter<ToolPostAdapter.ViewHolder>() {

    private lateinit var mListener: ToolPostAdapter.onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: ToolPostAdapter.onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToolPostAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.tools_list_item, parent, false)
        return ToolPostAdapter.ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ToolPostAdapter.ViewHolder, position: Int) {
        val currentTool = toolsList[position]
        holder.tvToolTitle.text = currentTool.toolTitle
        holder.tvToolPrice.text = currentTool.toolPrice
        holder.tvToolLocation.text = currentTool.toolLocation
        holder.tvToolType.text = currentTool.toolType
        Picasso.get().load(currentTool.imgUri).into(holder.ivToolImg)
    }

    override fun getItemCount(): Int {
        return toolsList.size
    }

    class ViewHolder(itemView: View, clickListener: ToolPostAdapter.onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val tvToolTitle : TextView = itemView.findViewById(R.id.tvToolTitle)
        val tvToolPrice : TextView = itemView.findViewById(R.id.tvToolPrice)
        val tvToolLocation : TextView = itemView.findViewById(R.id.tvToolLocation)
        val tvToolType : TextView = itemView.findViewById(R.id.tvToolType)
        val ivToolImg : ImageView = itemView.findViewById(R.id.ivToolPost)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }

}