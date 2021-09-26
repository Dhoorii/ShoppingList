package com.ryer.shoppinglist.database

import androidx.room.*

@Dao
interface DaoShoppingList {
    @Query("SELECT * FROM entetyshoppinglist")
    fun getAll(): List<EntetyShoppingList>

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    fun insert(shoppingItem: EntetyShoppingList)

    @Query("DELETE FROM entetyshoppinglist")
    fun deleteAll()
}