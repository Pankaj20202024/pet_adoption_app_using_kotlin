package com.example.petadoptionapp.Helper

import TinyDB
import android.content.Context
import android.widget.Toast
import com.example.petadoptionapp.Domain.BestPets


class ManagmentCart(private val context: Context) {
    private val tinyDB: TinyDB = TinyDB(context)

    fun insertFood(item: BestPets) {
        val listpop = getListCart()
        var existAlready = false
        var n = 0
        for (i in listpop.indices) {
            if (listpop[i].Title == item.Title) {
                existAlready = true
                n = i
                break
            }
        }
        if (existAlready) {
            listpop[n].numberInCart = item.numberInCart
        } else {
            listpop.add(item)
        }
        tinyDB.putListObject("CartList", listpop)
        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show()
    }

    fun getListCart(): ArrayList<BestPets> {
        return tinyDB.getListObject("CartList")
    }

    fun getTotalFee(): Double {
        val listItem = getListCart()
        var fee = 0.0
        for (i in listItem.indices) {
            fee += listItem[i].Price * listItem[i].numberInCart
        }
        return fee
    }

    fun minusNumberItem(listItem: ArrayList<BestPets>, position: Int, changeNumberItemsListener: ChangeNumberItemsListener) {
        if (listItem[position].numberInCart == 1) {
            listItem.removeAt(position)
        } else {
            listItem[position].numberInCart = listItem[position].numberInCart - 1
        }
        tinyDB.putListObject("CartList", listItem)
        changeNumberItemsListener.change()
    }

    fun plusNumberItem(listItem: ArrayList<BestPets>, position: Int, changeNumberItemsListener: ChangeNumberItemsListener) {
        listItem[position].numberInCart = listItem[position].numberInCart + 1
        tinyDB.putListObject("CartList", listItem)
        changeNumberItemsListener.change()
    }
}
