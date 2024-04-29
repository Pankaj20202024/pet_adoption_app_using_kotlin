package com.example.petadoptionapp.Activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petadoptionapp.Adapter.ListFoodAdapter
import com.example.petadoptionapp.Domain.BestPets
import com.example.petadoptionapp.databinding.ActivityListPetBinding
import com.google.firebase.database.*

class ListPetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListPetBinding
    private lateinit var adapterListFood: RecyclerView.Adapter<*>
    private var categoryId: Int = 0
    private lateinit var categoryName: String
    private lateinit var searchText: String
    private var isSearch: Boolean = false
    private lateinit var databaseRef: DatabaseReference
    private val list: ArrayList<BestPets> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListPetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentExtra()
        initList()
    }

    private fun getIntentExtra() {
        categoryId = intent.getIntExtra("CategoryId", 0)
        categoryName = intent.getStringExtra("CategoryName") ?: ""
        searchText = intent.getStringExtra("text") ?: ""
        isSearch = intent.getBooleanExtra("isSearch", false)
        binding.titleTxt.text = categoryName
        binding.backBtn.setOnClickListener { finish() }
    }

    private fun initList() {
        databaseRef = FirebaseDatabase.getInstance().getReference("Foods")
        binding.progressBar.visibility = View.VISIBLE

        val query: Query = if (isSearch) {
            databaseRef.orderByChild("Title").startAt(searchText).endAt(searchText + "\uf8ff")
        } else {
            databaseRef.orderByChild("CategoryId").equalTo(categoryId.toDouble())
        }

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        issue.getValue(BestPets::class.java)?.let { list.add(it) }
                    }
                    if (list.isNotEmpty()) {
                        showRecyclerView()
                    }
                } else {
                    hideProgressBar()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                hideProgressBar()
                // Handle errors here
            }
        })
    }

    private fun showRecyclerView() {
        binding.progressBar.visibility = View.GONE
        binding.foodListView.layoutManager = GridLayoutManager(this@ListPetActivity, 2)
        adapterListFood = ListFoodAdapter(list)
        binding.foodListView.adapter = adapterListFood
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
}
