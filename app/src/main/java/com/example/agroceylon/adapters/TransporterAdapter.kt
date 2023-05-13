package com.example.agroceylon.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agroceylon.R
import com.example.agroceylon.models.TransporterModel

class TransporterAdapter(private val transpList: ArrayList<TransporterModel>) :
    RecyclerView.Adapter<TransporterAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.transp_list_item, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTransp = transpList[position]
        holder.tvTranspName.text = currentTransp.trfName
        holder.tvTranspVehiModel.text = currentTransp.vehiModel
        holder.tvTranspVehiId.text = currentTransp.vehiId
        holder.tvTranspMobile.text = currentTransp.trMobile
        holder.tvTranspLocation.text = currentTransp.trLocation

    }

    override fun getItemCount(): Int {
        return transpList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val tvTranspName : TextView = itemView.findViewById(R.id.tvTranspName)
        val tvTranspVehiModel : TextView = itemView.findViewById(R.id.tvTranspVehiModel)
        val tvTranspVehiId : TextView = itemView.findViewById(R.id.tvTranspVehiId)
        val tvTranspMobile : TextView = itemView.findViewById(R.id.tvTranspMobile)
        val tvTranspLocation : TextView = itemView.findViewById(R.id.tvTranspLocation)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }

}