package com.ryer.shoppinglist.shoppingListAdapter

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ryer.shoppinglist.R
import com.ryer.shoppinglist.database.EntetyShoppingList
import com.ryer.shoppinglist.listAdapter.ItemCheckAdapter

class ShoppingListAdapter(private val itemList: List<ShoppingItem>,context:Context,isFinished: Boolean) : RecyclerView.Adapter<ShoppingListAdapter.ShoppingViewHolder>(){

    val isFinished:Boolean = isFinished
    val context:Context = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.shop_list_item,
        parent, false)

        return ShoppingViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.itemName.text = currentItem.title
        //changing note depending if item is completed
        if (!currentItem.isFinished)
            holder.itemDescription.text = "Last Modified:" + currentItem.lastUpdateTime
        else
            holder.itemDescription.text = "Finished:" + currentItem.lastUpdateTime
        if (!isFinished){
            holder.itemView.setOnClickListener(){
                showDialog(context, currentItem)
            }
        }
    }

    override fun getItemCount() = itemList.size

    //function that creates a dialog window to check/uncheck if item on list is found
    private fun showDialog(context: Context,itemList: ShoppingItem){
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.shopping_fragment)
        val recyclerView = dialog.findViewById<RecyclerView>(R.id.shoppingList)
        recyclerView.adapter = ItemCheckAdapter(itemList.listOfItems)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        dialog.show()
    }

    class ShoppingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val itemName: TextView = itemView.findViewById(R.id.item_name)
        val itemDescription: TextView = itemView.findViewById(R.id.item_description)
    }
}