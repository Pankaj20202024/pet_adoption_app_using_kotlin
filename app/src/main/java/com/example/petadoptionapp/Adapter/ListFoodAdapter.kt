package com.example.petadoptionapp.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.petadoptionapp.Activity.DetailsActivity
import com.example.petadoptionapp.Domain.BestPets
import com.example.petadoptionapp.R
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur

class ListFoodAdapter(private val items: ArrayList<BestPets>) : RecyclerView.Adapter<ListFoodAdapter.ViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_list_food, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.titleTxt.text = item.Title
        holder.priceTxt.text = "$${item.Price}"
        holder.timeTxt.text = "${item.TimeValue} min"
        holder.starTxt.text = item.Star.toString()

        val radius = 10f
        val decorView = (context as Activity).window.decorView
        val rootView = decorView.findViewById<ViewGroup>(android.R.id.content)
        val windowBackground: Drawable? = decorView.background

        holder.blurView.setupWith(rootView, RenderScriptBlur(context))
            .setFrameClearDrawable(windowBackground)
            .setBlurRadius(radius)

        holder.blurView.outlineProvider = ViewOutlineProvider.BACKGROUND
        holder.blurView.clipToOutline = true

        Glide.with(context)
            .load(item.ImagePath)
            .transform(RoundedCorners(30))
            .into(holder.pic)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("object", item)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTxt: TextView = itemView.findViewById(R.id.titleTxt)
        val priceTxt: TextView = itemView.findViewById(R.id.priceTxt)
        val starTxt: TextView = itemView.findViewById(R.id.starTxt)
        val timeTxt: TextView = itemView.findViewById(R.id.timeTxt)
        val pic: ImageView = itemView.findViewById(R.id.img)
        val blurView: BlurView = itemView.findViewById(R.id.blurView)
    }
}
