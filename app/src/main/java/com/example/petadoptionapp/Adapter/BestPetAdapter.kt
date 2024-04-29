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
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.petadoptionapp.Activity.DetailsActivity

import com.example.petadoptionapp.Domain.BestPets
import com.example.petadoptionapp.R
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur

class BestPetAdapter(private val items: ArrayList<BestPets>) : RecyclerView.Adapter<BestPetAdapter.ViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_best_food, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTxt.text = items[position].Title
        holder.priceTxt.text = "$" + items[position].Price
        holder.timeTxt.text = items[position].TimeValue.toString() + " min"
        holder.starTxt.text = items[position].Star.toString()
        val radius = 10f
        val decorView = (holder.itemView.context as Activity).window.decorView
        val rootView = decorView.findViewById<ViewGroup>(android.R.id.content)
        val windowBackground = decorView.background

        holder.blurView.setupWith(rootView, RenderScriptBlur(holder.itemView.context))
            .setFrameClearDrawable(windowBackground)
            .setBlurRadius(radius)

        holder.blurView.outlineProvider = ViewOutlineProvider.BACKGROUND
        holder.blurView.clipToOutline = true
        Glide.with(context)
            .load(items[position].ImagePath)
            .transform(CenterCrop(), RoundedCorners(30))
            .into(holder.pic)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("object", items[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTxt: TextView = itemView.findViewById(R.id.titleTxt)
        var priceTxt: TextView = itemView.findViewById(R.id.priceTxt)
        var starTxt: TextView = itemView.findViewById(R.id.starTxt)
        var timeTxt: TextView = itemView.findViewById(R.id.timeTxt)
        var pic: ImageView = itemView.findViewById(R.id.img)
        var blurView: BlurView = itemView.findViewById(R.id.blueView)
    }
}
