package com.example.petadoptionapp.Activity


import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.petadoptionapp.Domain.BestPets
import com.example.petadoptionapp.Helper.ManagmentCart
import com.example.petadoptionapp.databinding.ActivityDetailsBinding
import eightbitlab.com.blurview.RenderScriptBlur

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var bestPet: BestPets
    private var num = 1
    private lateinit var managmentCart: ManagmentCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)

        getBundleExtra()
        setVariable()
        setBlurEffect()
    }

    private fun setBlurEffect() {
        val radius = 10f
        val decorView: View = window.decorView
        val rootView = decorView.findViewById<ViewGroup>(android.R.id.content)
        val windowBackground = decorView.background

        binding.blurView.setupWith(rootView, RenderScriptBlur(this))
            .setFrameClearDrawable(windowBackground)
            .setBlurRadius(radius)

        binding.blurView.outlineProvider = ViewOutlineProvider.BACKGROUND
        binding.blurView.clipToOutline = true

        binding.blurView2.setupWith(rootView, RenderScriptBlur(this))
            .setFrameClearDrawable(windowBackground)
            .setBlurRadius(radius)

        binding.blurView2.outlineProvider = ViewOutlineProvider.BACKGROUND
        binding.blurView2.clipToOutline = true
    }

    private fun setVariable() {
        binding.backBtn.setOnClickListener { finish() }

        Glide.with(this)
            .load(bestPet.ImagePath)
            .into(binding.pic)

        binding.priceTxt.text = "$" + bestPet.Price
        binding.titleTxt.text = bestPet.Title
        binding.descriptionTxt.text = bestPet.Description
        binding.ratingTxt.text = "${bestPet.Star} Rating"
        binding.ratingBar.rating = bestPet.Star.toFloat()
        binding.totalTxt.text = (num * bestPet.Price).toString() + "$"

        binding.plusBtn.setOnClickListener {
            num++
            binding.numTxt.text = "$num "
            binding.totalTxt.text = (num * bestPet.Price).toString() + "$"
        }

        binding.minusBtn.setOnClickListener {
            if (num > 1) {
                num--
                binding.numTxt.text = num.toString()
                binding.totalTxt.text = (num * bestPet.Price).toString() + "$"
            }
        }

        binding.addBtn.setOnClickListener {
            bestPet.numberInCart = num
            managmentCart.insertFood(bestPet)
        }
    }

    private fun getBundleExtra() {
        bestPet = intent.getSerializableExtra("object") as BestPets
    }
}
