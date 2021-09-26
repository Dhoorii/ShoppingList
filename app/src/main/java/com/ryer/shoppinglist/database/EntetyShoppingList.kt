package com.ryer.shoppinglist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ryer.shoppinglist.listAdapter.ItemListItem

@Entity
data class EntetyShoppingList(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "lastUpdateTime") val lastUpdateTime: String?,
    @ColumnInfo(name = "itemsList") val itemsList: List<ItemListItem>?,
    @ColumnInfo(name = "isFinished") val isFinished: Boolean?
)