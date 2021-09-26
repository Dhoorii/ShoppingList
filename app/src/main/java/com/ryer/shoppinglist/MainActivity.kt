package com.ryer.shoppinglist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.ryer.shoppinglist.database.EntetyShoppingList
import com.ryer.shoppinglist.database.ShoppingModel
import com.ryer.shoppinglist.databinding.ActivityMainBinding
import com.ryer.shoppinglist.listAdapter.ItemListItem
import com.ryer.shoppinglist.shoppingListAdapter.ShoppingItem
import com.ryer.shoppinglist.shoppingListAdapter.ShoppingListAdapter
import com.ryer.shoppinglist.shoppingListAdapter.ShoppingPageAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    var BUNDLE : String = "BUNDLE"
    var SHOPPINGLIST : String = "SHOPPINGLIST"

    private lateinit var binding: ActivityMainBinding
    lateinit var recyclerView: RecyclerView
    lateinit var viewModel: ShoppingModel

    val shoppingList = ArrayList<ShoppingItem>()
    val archiveList = ArrayList<ShoppingItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fragmentAdapter = ShoppingPageAdapter(this, supportFragmentManager)

        viewModel = ViewModelProvider(this).get(ShoppingModel::class.java)
        viewModel.getShoppingListLive().observe(this, androidx.lifecycle.Observer<List<EntetyShoppingList>>{
            it.forEach{
                if (!it.isFinished!!)
                    convertEntetyToShoppingList(it)?.let { it1 -> shoppingList.add(it1) }
                else
                    convertEntetyToShoppingList(it)?.let { it1 -> archiveList.add(it1) }
            }

        })

        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = fragmentAdapter

        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)

        val fab: FloatingActionButton = binding.fab

        fab.setOnClickListener { view ->
            val intent = Intent(this,AddShopingListActivity::class.java)
            getResult.launch(intent)

        }
    }

    override fun onDestroy() {
        viewModel.deleteAllShoppingList()
        shoppingList.forEach{
            saveToDatabase(it)
        }
        archiveList.forEach {
            saveToDatabase(it)
        }
        super.onDestroy()
    }
    //this part is to check if shopping list item is finished after exiting from dialog
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus) {
            if (shoppingList.size > 0)
                for (i in shoppingList.indices)
                {
                    shoppingList[i].checkIfFinished()
                    if (shoppingList[i].isFinished){
                        val test = SimpleDateFormat("dd-MM-yyyy")
                        val currentDateTime = test.format(Date())
                        shoppingList[i].lastUpdateTime = currentDateTime
                        archiveList.add(shoppingList[i])
                        updateArchiveList()
                        shoppingList.removeAt(i)
                        updateShoppingList()
                        break
                    }
                }
            updateShoppingList()
            updateArchiveList()
        }
        super.onWindowFocusChanged(hasFocus)
    }

    //this part is to checking result from 2nd activity[AddShoppingListActivity]
    private val getResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == Activity.RESULT_OK){
                val value = it.data?.getBundleExtra(SHOPPINGLIST)
                val shoppingListItem = value?.getSerializable(BUNDLE) as ShoppingItem

                if (!shoppingListItem.isFinished) {
                    shoppingList.add(shoppingListItem)
                    updateShoppingList()
                }
                else{
                    archiveList.add(shoppingListItem)
                    updateArchiveList()
                }
            }
        }

    //function to add to archive list
    private fun updateArchiveList(){
        recyclerView = findViewById(R.id.achiveList)
        archiveList.sortBy { it.lastUpdateTime }
        recyclerView.adapter = ShoppingListAdapter(archiveList,this,true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
    }
    //function to add to active shopping list
    private fun updateShoppingList(){
        recyclerView = findViewById(R.id.shoppingList)
        shoppingList.sortBy { it.lastUpdateTime }
        recyclerView.adapter = ShoppingListAdapter(shoppingList,this,false)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
    }

    private fun saveToDatabase(item : ShoppingItem){
            val database = EntetyShoppingList(0,item.title,item.lastUpdateTime,item.listOfItems,item.isFinished)
            viewModel.insertShoppingInfo(database)

    }

    //function that converst values from database to app class
    fun convertEntetyToShoppingList(entetyShoppingList: EntetyShoppingList) : ShoppingItem? {
        val title = entetyShoppingList.title
        val lastUpdateTime = entetyShoppingList.lastUpdateTime
        val listOfItems = ArrayList<ItemListItem>()
        entetyShoppingList.itemsList?.forEach{
            listOfItems.add(it)
        }
        val shoppingItem = title?.let { lastUpdateTime?.let { it1 ->
            ShoppingItem(it,
                it1,listOfItems)
        } }
        shoppingItem!!.isFinished = entetyShoppingList.isFinished!!
        System.out.println("TEST")

        return shoppingItem
    }
}