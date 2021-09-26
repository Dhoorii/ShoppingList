package com.ryer.shoppinglist.listAdapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ryer.shoppinglist.R

class ItemCheckAdapter(private val itemList: ArrayList<ItemListItem>) : RecyclerView.Adapter<ItemCheckAdapter.ItemViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.shop_list_item,
            parent, false)

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.itemName.text = currentItem.itemName
        holder.itemCount.text = currentItem.itemCount
        if (!currentItem.isFinished){
            holder.itemView.setBackgroundColor(Color.RED)
        }
        else{
            holder.itemView.setBackgroundColor(Color.GREEN)
        }
        holder.itemView.setOnClickListener(){
            if (currentItem.isFinished){
                holder.itemView.setBackgroundColor(Color.RED)
                itemList[holder.adapterPosition].isFinished = false
            }
            else{
                holder.itemView.setBackgroundColor(Color.GREEN)
                itemList[holder.adapterPosition].isFinished = true
            }
        }
    }

    override fun getItemCount() = itemList.size

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val itemName: TextView = itemView.findViewById(R.id.item_name)
        val itemCount: TextView = itemView.findViewById(R.id.item_description)

    }
}