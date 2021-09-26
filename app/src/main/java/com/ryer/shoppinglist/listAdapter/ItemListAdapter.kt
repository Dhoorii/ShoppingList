package com.ryer.shoppinglist.listAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ryer.shoppinglist.R

class ItemListAdapter(private val itemList: ArrayList<ItemListItem>) : RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.shop_item,
        parent, false)

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.itemName.text = currentItem.itemName
        holder.itemCount.text = currentItem.itemCount
        holder.delete.setOnClickListener {
            itemList.removeAt(holder.adapterPosition)
            notifyItemRemoved(holder.adapterPosition)
        }
    }

    override fun getItemCount() = itemList.size

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val itemName: TextView = itemView.findViewById(R.id.item_name)
        val itemCount: TextView = itemView.findViewById(R.id.item_description)
        val delete: ImageView = itemView.findViewById(R.id.delete_item)
    }
}