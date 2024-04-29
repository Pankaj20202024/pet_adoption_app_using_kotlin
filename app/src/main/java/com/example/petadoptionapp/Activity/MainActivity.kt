package com.example.petadoptionapp.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.ImageView

import androidx.recyclerview.widget.LinearLayoutManager

import com.example.petadoptionapp.Adapter.BestPetAdapter
import com.example.petadoptionapp.Adapter.CategoryAdapter

import com.example.petadoptionapp.Domain.BestPets
import com.example.petadoptionapp.Domain.Category
import com.example.petadoptionapp.Domain.Location


import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.example.petadoptionapp.Domain.Time
import com.example.petadoptionapp.Domain.Price
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth


class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var LogOutBtn : ImageView
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        LogOutBtn = findViewById(R.id.logout)
        LogOutBtn.setOnClickListener{
            firebaseAuth.signOut();
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        initLocation()
        initTime()
        initPrice()
        initBestFood()
        initCategory()
        setVariable()
    }

    private fun setVariable() {
        binding.cartBtn.setOnClickListener { startActivity(Intent(this@MainActivity, CartActivity::class.java)) }
        binding.searchBtn.setOnClickListener {
            val text = binding.searchEdt.text.toString()
            if (text.isNotEmpty()) {
                val intent = Intent(this@MainActivity, ListPetActivity::class.java)
                intent.putExtra("text", text)
                intent.putExtra("isSearch", true)
                startActivity(intent)
            }
        }
    }


    private fun initCategory() {
        val myRef = database.getReference("Category")
        binding.progressBarCategory.visibility = View.VISIBLE
        val list = ArrayList<Category>()

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        list.add(issue.getValue(Category::class.java)!!)
                    }
                }
                if (list.size > 0) {
                    binding.categoryView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                    val adapterCategory = CategoryAdapter(list)
                    binding.categoryView.adapter = adapterCategory
                }
                binding.progressBarCategory.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event if needed
            }
        })
    }

    private fun initBestFood() {
        val myRef = database.getReference("Foods")
        binding.progressBarBestFood.visibility = View.VISIBLE
        val list = ArrayList<BestPets>()
        val query = myRef.orderByChild("BestFood").equalTo(true)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        list.add(issue.getValue(BestPets::class.java)!!)
                    }
                }
                if (list.size > 0) {
                    binding.bestFoodView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                    val adapterBestFood = BestPetAdapter(list)
                    binding.bestFoodView.adapter = adapterBestFood
                }
                binding.progressBarBestFood.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun initLocation() {
        var myRef: DatabaseReference = database.getReference("Location")
        val list: ArrayList<Location> = ArrayList()

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        list.add(issue.getValue(Location::class.java)!!)
                    }
                    val adapter = ArrayAdapter<Location>(this@MainActivity, R.layout.sp_item, list)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.locationSp.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun initTime() {
        var myRef: DatabaseReference = database.getReference("Time")
        val list: ArrayList<Time> = ArrayList()

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        list.add(issue.getValue(Time::class.java)!!)
                    }
                    val adapter = ArrayAdapter<Time>(this@MainActivity, R.layout.sp_item, list)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.timeSp.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event if needed
            }
        })
    }

    private fun initPrice() {
        var myRef: DatabaseReference = database.getReference("Price")
        val list: ArrayList<Price> = ArrayList()

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        list.add(issue.getValue(Price::class.java)!!)
                    }
                    val adapter = ArrayAdapter<Price>(this@MainActivity, R.layout.sp_item, list)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.priceSp.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event if needed
            }
        })
    }
}
