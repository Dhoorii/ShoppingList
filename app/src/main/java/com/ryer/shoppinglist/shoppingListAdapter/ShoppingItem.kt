package com.ryer.shoppinglist.shoppingListAdapter

import com.ryer.shoppinglist.database.EntetyShoppingList
import com.ryer.shoppinglist.listAdapter.ItemListItem
import java.io.Serializable

class ShoppingItem(var title: String,var lastUpdateTime: String,var listOfItems: ArrayList<ItemListItem>) : Serializable{
    var isFinished = false
    fun checkIfFinished(){
        if (listOfItems.all{itemListItem -> itemListItem.isFinished })
        {
            isFinished = true
        }

    }
}