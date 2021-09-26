package com.ryer.shoppinglist.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.ryer.shoppinglist.listAdapter.ItemListItem
import org.json.JSONObject
import java.io.Serializable

class DataConverter :Serializable {
    @TypeConverter
    fun fromItemList(itemList:List<ItemListItem>) = Gson().toJson(itemList)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value,Array<ItemListItem>::class.java).toList()
}