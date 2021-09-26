package com.ryer.shoppinglist.listAdapter

import java.io.Serializable

data class ItemListItem(
    val itemName: String,
    val itemCount: String
) : Serializable{
    var isFinished = false
}