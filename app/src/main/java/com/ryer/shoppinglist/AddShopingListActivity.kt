package com.ryer.shoppinglist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ryer.shoppinglist.listAdapter.ItemListAdapter
import com.ryer.shoppinglist.listAdapter.ItemListItem
import com.ryer.shoppinglist.shoppingListAdapter.ShoppingItem
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddShopingListActivity : AppCompatActivity() {
    var BUNDLE : String = "BUNDLE"
    var SHOPPINGLIST : String = "SHOPPINGLIST"

    lateinit var itemNameEditText :EditText
    lateinit var itemCountEditText :EditText
    lateinit var shoppingListTitleEditText :EditText
    lateinit var itemList: ArrayList<ItemListItem>
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_shoping_list)
        itemNameEditText = findViewById(R.id.editTextName)
        itemCountEditText = findViewById(R.id.editTextItemCount)
        recyclerView = findViewById(R.id.item_list)
        shoppingListTitleEditText = findViewById(R.id.editTextTitle)
        itemList = ArrayList()
    }

    //button to add items to shopping list
    fun addItem(view: View) {
        if (!itemNameEditText.text.toString().equals("") && !itemCountEditText.text.toString().equals("")) {
            val shoppingItem =
                ItemListItem(itemNameEditText.text.toString(), itemCountEditText.text.toString())
            itemList.add(shoppingItem)
            recyclerView.adapter = ItemListAdapter(itemList)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.setHasFixedSize(true)
            itemNameEditText.setText("")
            itemCountEditText.setText("")
        }
    }

    fun onClickCancel(view: View) {
        super.onBackPressed()
    }

    //function button to return to main activity with shopping item
    fun onClickFinish(view: View) {
        val title = shoppingListTitleEditText.text.toString()

        val test = SimpleDateFormat("dd-MM-yyyy")
        val currentDateTime = test.format(Date())
        val shoppingList = ShoppingItem(title,currentDateTime,itemList)
        val bundle = Bundle()

        bundle.putSerializable(BUNDLE,shoppingList)
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra(SHOPPINGLIST,bundle)
        setResult(Activity.RESULT_OK,intent)
        finish()

    }
}