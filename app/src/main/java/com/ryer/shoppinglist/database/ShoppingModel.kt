package com.ryer.shoppinglist.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ShoppingModel(app: Application): AndroidViewModel(app) {
    private var allList: MutableLiveData<List<EntetyShoppingList>> = MutableLiveData()

    init {
        getShopoingList()
    }

    fun getShoppingListLive(): LiveData<List<EntetyShoppingList>>{
        return allList
    }
    fun getShopoingList() {
        val shoppingDao = DataBaseShopping.getShoppingDatabase((getApplication()))?.daoShoppingList()
        val list= shoppingDao?.getAll()

        allList.postValue(list)
    }
    //function to insert Shopping item into local database
    fun insertShoppingInfo(entity: EntetyShoppingList){
        val shoppingDao = DataBaseShopping.getShoppingDatabase(getApplication())?.daoShoppingList()
        shoppingDao?.insert(entity)
        getShopoingList()
    }
    //function to delete whole database so the items wont be doubled
    fun deleteAllShoppingList()
    {
        val shoppingDao = DataBaseShopping.getShoppingDatabase((getApplication()))?.daoShoppingList()
        shoppingDao?.deleteAll()
    }
}