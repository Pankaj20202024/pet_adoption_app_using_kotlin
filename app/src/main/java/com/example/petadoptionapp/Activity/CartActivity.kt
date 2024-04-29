package com.example.petadoptionapp.Activity


import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petadoptionapp.Adapter.CartAdapter
import com.example.petadoptionapp.Helper.ManagmentCart
import com.example.petadoptionapp.Helper.ChangeNumberItemsListener

import com.example.petadoptionapp.databinding.ActivityCartBinding
import eightbitlab.com.blurview.RenderScriptBlur

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: RecyclerView.Adapter<*>
    private lateinit var managmentCart: ManagmentCart
    private var tax: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)
        setVariables()
        calculateCart()
        initList()
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

    private fun initList() {
        if (managmentCart.getListCart().isEmpty()) {
            binding.emptyTxt.visibility = View.VISIBLE
            binding.scrollView.visibility = View.GONE
        } else {
            binding.emptyTxt.visibility = View.GONE
            binding.scrollView.visibility = View.VISIBLE
        }
        binding.cartView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = CartAdapter(managmentCart.getListCart(), this,
            object : ChangeNumberItemsListener {
                override fun change() {
                    calculateCart()
                }
            })
        binding.cartView.adapter = adapter
    }

    private fun setVariables() {
        binding.backBtn.setOnClickListener { finish() }
    }

    private fun calculateCart() {
        val percentTax = 0.02
        val delivery = 10.0
        tax = (managmentCart.getTotalFee() * percentTax * 100.0) / 100
        val total = (managmentCart.getTotalFee() + tax + delivery * 100) / 100
        val itemTotal = (managmentCart.getTotalFee() * 100) / 100
        binding.totalFeeTxt.text = "$itemTotal"
        binding.taxTxt.text = "$tax"
        binding.deliveryTxt.text = "$delivery"
        binding.totalTxt.text = "$total"
    }
}
