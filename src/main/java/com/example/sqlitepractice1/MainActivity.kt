package com.example.sqlitepractice1

import android.annotation.SuppressLint

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    var users:MutableList<User> = mutableListOf()
    var listAdapter:MyListAdapter?= null
    val dataBase = DBHelper(this)

    private lateinit var userIdET:EditText
    private lateinit var listViewLV:ListView
    private lateinit var saveBTN:Button
    private lateinit var deleteBTN:Button
    private lateinit var updateBTN:Button
    private lateinit var userNameET:EditText
    private lateinit var userPriceET:EditText
    private lateinit var userWeightET:EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        listViewLV = findViewById(R.id.listViewLV)
        init()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        saveBTN.setOnClickListener {
            saveRecord()
        }
    }

    override fun onResume() {
        super.onResume()
        updateBTN.setOnClickListener {
            updateRecord()
        }
        deleteBTN.setOnClickListener {
            deleteRecord()
        }

    }

    @SuppressLint("MissingInflatedId")
    private fun deleteRecord() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.delete_dialog, null)
        dialogBuilder.setView(dialogView)

        val chooseDeleteId = dialogView.findViewById<EditText>(R.id.deleteIdET)
        dialogBuilder.setTitle("Удалить запись")
        dialogBuilder.setMessage("Введите id:")
        dialogBuilder.setPositiveButton("Удалить"){_,_ ->
            val deleteId = chooseDeleteId.text.toString()
            if(deleteId.trim()!=""){
                val user = User(Integer.parseInt(deleteId),"","", "")
                dataBase.deleteUser(user)
                viewDataAdapter()
                Toast.makeText(applicationContext, "Запись удалена", Toast.LENGTH_SHORT).show()
            }
        }
        dialogBuilder.setNegativeButton("Отмена") { _, _ ->
        }
        dialogBuilder.create().show()
    }





    private fun init() {
        userIdET = findViewById(R.id.userIdET)
        userWeightET = findViewById(R.id.userWeightET)
        userNameET = findViewById(R.id.userNameET)
        userPriceET = findViewById(R.id.userPriceET)
        saveBTN = findViewById(R.id.saveBTN)
        updateBTN = findViewById(R.id.updateBTN)
        deleteBTN = findViewById(R.id.deleteBTN)
        viewDataAdapter()
    }


    private fun viewDataAdapter() {
        users = dataBase.readUser()
        listAdapter = MyListAdapter(this, users)
        listViewLV.adapter = listAdapter
        listAdapter?.notifyDataSetChanged()
    }

    private fun saveRecord() {
        val id = userIdET.text.toString()
        val name = userNameET.text.toString()
        val weight = userWeightET.text.toString()
        val price = userPriceET.text.toString()
        if(name.trim()!="" && weight.trim()!= "" && price.trim() !=""){
            val user = User(Integer.parseInt(id), name,weight,price)
            users.add(user)
            dataBase.addUser(user)
            Toast.makeText(applicationContext, "Запись добавлена", Toast.LENGTH_SHORT).show()
            userIdET.text.clear()
            userNameET.text.clear()
            userWeightET.text.clear()
            userPriceET.text.clear()
            viewDataAdapter()
        }
    }


    @SuppressLint("MissingInflatedId")
    private fun updateRecord() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.update_dialo, null)
        dialogBuilder.setView(dialogView)
        val editId = dialogView.findViewById<EditText>(R.id.updateIdET)
        val editName = dialogView.findViewById<EditText>(R.id.updateNameET)
        val editWeight = dialogView.findViewById<EditText>(R.id.updateWeightET)
        val editPrice = dialogView.findViewById<EditText>(R.id.updatePriceET)

        dialogBuilder.setTitle("Обновить запись")
        dialogBuilder.setMessage("Введите данные ниже:")
        dialogBuilder.setPositiveButton("Обновить"){_,_ ->
            val updateId = editId.text.toString()
            val updateName = editName.text.toString()
            val updateWeight = editWeight.text.toString()
            val updatePrice = editPrice.text.toString()
            if(updateName.trim()!=""&&updateWeight.trim()!="" &&updatePrice.trim()!=""){
                val user = User(Integer.parseInt(updateId), updateName, updateWeight,updatePrice)
                 dataBase.updateUser(user)
                viewDataAdapter()
                Toast.makeText(applicationContext, "Запись обновлена", Toast.LENGTH_SHORT).show()
            }
        }
        dialogBuilder.setNegativeButton("Отмена"){dialog,which->
        }
        dialogBuilder.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.exit -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}