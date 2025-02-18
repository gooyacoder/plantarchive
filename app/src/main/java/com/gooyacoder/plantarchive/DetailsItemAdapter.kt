package com.gooyacoder.plantarchive

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DetailsItemAdapter(private val itemList: List<DetailsItem>,
                  private val itemClickListener: OnItemClickListener,
                  private val listener: OnItemLongClickListener)
    : RecyclerView.Adapter<DetailsItemAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: DetailsItem)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(item: DetailsItem)
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val date: TextView = view.findViewById(R.id.DetailsDate)
        val image: ImageView = view.findViewById(R.id.DetailsPlantImage)
        
        fun bind(item: DetailsItem, clickListener: OnItemClickListener) {

            val g_date = GerminationDate()
            val persian_date = g_date.dateToPersian(g_date.stringToDate(item.startDate))
            date.text = persian_date.longDateString
            
            image.setImageBitmap(DbBitmapUtility.getImage(item.image))
            itemView.setOnClickListener {
                clickListener.onItemClick(item)
            }
        }
        init {
            itemView.setOnLongClickListener {
                listener.onItemLongClick(itemList[adapterPosition])
                true
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.details_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position], itemClickListener)
    }

    override fun getItemCount() = itemList.size

}