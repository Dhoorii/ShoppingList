package com.ryer.shoppinglist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(DataConverter::class)
@Database(entities = [EntetyShoppingList::class], version = 1)
abstract class DataBaseShopping : RoomDatabase(){
    abstract fun daoShoppingList(): DaoShoppingList?

    companion object{
        @Volatile
        var INSTANCE: DataBaseShopping? = null

        fun getShoppingDatabase(context: Context): DataBaseShopping?{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder<DataBaseShopping>(
                    context.applicationContext,DataBaseShopping::class.java,"Database"
                )
                    .allowMainThreadQueries()
                    .build()
                }
            return INSTANCE
            }
        }
    }