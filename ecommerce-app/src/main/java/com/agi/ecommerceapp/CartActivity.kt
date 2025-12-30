package com.agi.ecommerceapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.agi.ecommerceapp.adapters.CartAdapter
import com.agi.ecommerceapp.databinding.ActivityCartBinding
import java.text.NumberFormat

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Shopping Cart"
        
        setupRecyclerView()
        updateTotal()
        
        binding.checkoutButton.setOnClickListener {
            CartManager.clearCart()
            Toast.makeText(this, "Order placed!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    
    private fun setupRecyclerView() {
        adapter = CartAdapter(CartManager.getCartItems()) {
            updateTotal()
        }
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.cartRecyclerView.adapter = adapter
    }
    
    private fun updateTotal() {
        val total = CartManager.getTotalPrice()
        binding.totalTextView.text = "Total: ${NumberFormat.getCurrencyInstance().format(total)}"
        adapter.notifyDataSetChanged()
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

