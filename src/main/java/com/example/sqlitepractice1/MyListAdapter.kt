package com.example.sqlitepractice1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class MyListAdapter (private val context: Context, userList:MutableList<User>):
ArrayAdapter<User>(context,R.layout.list_item,userList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val user = getItem(position)
        var view = convertView
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }
        val idText = view?.findViewById<TextView>(R.id.idTV)
        val nameText = view?.findViewById<TextView>(R.id.nameTV)
        val priceText = view?.findViewById<TextView>(R.id.priceTV)
        val weightText = view?.findViewById<TextView>(R.id.weightTV)

        idText?.text = "Id^ ${user?.userId}"
        nameText?.text = "Название ${user?.userName}"
        priceText?.text = "Цена ${user?.userPrice}"
        weightText?.text = "Вес ${user?.userWeight}"

        return view!!
    }
}