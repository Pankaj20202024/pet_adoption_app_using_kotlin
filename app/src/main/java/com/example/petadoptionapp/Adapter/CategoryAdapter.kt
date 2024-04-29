package com.example.petadoptionapp.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petadoptionapp.Activity.ListPetActivity


import com.example.petadoptionapp.Domain.Category
import com.example.petadoptionapp.R
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur

class CategoryAdapter(private val items: ArrayList<Category>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_category, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoryName = items[position].Name
        if (categoryName != null) {
            holder.titleTxt.text = categoryName
            val radius = 10f
            val decorView = (holder.itemView.context as Activity).window.decorView
            val rootView = decorView.findViewById<ViewGroup>(android.R.id.content)
            val windowBackground = decorView.background

            holder.blurView.setupWith(rootView, RenderScriptBlur(holder.itemView.context))
                .setFrameClearDrawable(windowBackground)
                .setBlurRadius(radius)
            holder.blurView.outlineProvider = ViewOutlineProvider.BACKGROUND
            holder.blurView.clipToOutline = true

            val drawableResourceId = holder.itemView.resources.getIdentifier(items[position].ImagePath, "drawable", context.packageName)

            Glide.with(context)
                .load(drawableResourceId)
                .into(holder.pic)

            holder.itemView.setOnClickListener {
                val intent = Intent(context, ListPetActivity::class.java)
                intent.putExtra("CategoryId", items[position].Id)
                intent.putExtra("CategoryName", items[position].Name)

                context.startActivity(intent)
            }

        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTxt: TextView = itemView.findViewById(R.id.titleCatTxt)
        var pic: ImageView = itemView.findViewById(R.id.imgCat)
        var blurView: BlurView = itemView.findViewById(R.id.blurView)
    }
}
