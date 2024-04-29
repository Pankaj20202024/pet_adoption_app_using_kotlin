package com.example.petadoptionapp.Adapter

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petadoptionapp.Domain.BestPets
import com.example.petadoptionapp.Helper.ChangeNumberItemsListener
import com.example.petadoptionapp.Helper.ManagmentCart
import com.example.petadoptionapp.R
import eightbitlab.com.blurview.RenderScriptBlur
import eightbitlab.com.blurview.BlurView

class CartAdapter(
    private val listItemSelected: ArrayList<BestPets>,
    private val context: Context,
    private val changeNumberItemsListener: ChangeNumberItemsListener
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private val managmentCart: ManagmentCart = ManagmentCart(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_cart, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val radius = 10f
        val decorView: View = (holder.itemView.context as Activity).window.decorView
        val rootView = decorView.findViewById<ViewGroup>(android.R.id.content)
        val windowBackground: Drawable? = decorView.background

        holder.blurView.setupWith(rootView, RenderScriptBlur(holder.itemView.context))
            .setFrameClearDrawable(windowBackground)
            .setBlurRadius(radius)

        holder.blurView.outlineProvider = ViewOutlineProvider.BACKGROUND
        holder.blurView.clipToOutline = true

        Glide.with(holder.itemView.context)
            .load(listItemSelected[position].ImagePath)
            .into(holder.pic)

        holder.title.text = listItemSelected[position].Title
        holder.feeEachItem.text = "$" + listItemSelected[position].Price
        holder.totalEachItem.text = "${listItemSelected[position].numberInCart} * $" +
                (listItemSelected[position].numberInCart * listItemSelected[position].Price)

        holder.num.text = listItemSelected[position].numberInCart.toString()

        holder.plusItem.setOnClickListener {
            val listener = object : ChangeNumberItemsListener {
                override fun change() {
                    changeNumberItemsListener.change()
                }
            }
            managmentCart.plusNumberItem(listItemSelected, position, listener)
            notifyDataSetChanged()
        }

        holder.minusItem.setOnClickListener {
            val listener = object : ChangeNumberItemsListener {
                override fun change() {
                    changeNumberItemsListener.change()
                }
            }
            managmentCart.minusNumberItem(listItemSelected, position, listener)
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return listItemSelected.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.titleTxt)
        val pic: ImageView = itemView.findViewById(R.id.pic)
        val feeEachItem: TextView = itemView.findViewById(R.id.feeEachItem)
        val totalEachItem: TextView = itemView.findViewById(R.id.totalEachItem)
        val plusItem: TextView = itemView.findViewById(R.id.plusBtn)
        val minusItem: TextView = itemView.findViewById(R.id.minusBtn)
        val num: TextView = itemView.findViewById(R.id.numTxt)
        val blurView: BlurView = itemView.findViewById(R.id.blurView)
    }
}
