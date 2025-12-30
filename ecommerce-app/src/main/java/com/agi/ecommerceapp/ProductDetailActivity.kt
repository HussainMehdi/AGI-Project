package com.agi.ecommerceapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.agi.ecommerceapp.data.SampleData
import com.agi.ecommerceapp.databinding.ActivityProductDetailBinding
import com.agi.ecommerceapp.models.Product
import com.bumptech.glide.Glide
import java.text.NumberFormat

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var product: Product
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val productId = intent.getStringExtra("product_id") ?: return finish()
        product = SampleData.products.find { it.id == productId } ?: return finish()
        
        setupViews()
    }
    
    private fun setupViews() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = product.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        binding.productName.text = product.name
        binding.productPrice.text = NumberFormat.getCurrencyInstance().format(product.price)
        binding.productDescription.text = product.description
        binding.productRating.text = "★ ${product.rating} (${product.reviews} reviews)"
        binding.productCategory.text = product.category
        
        // Load product image using Glide
        Glide.with(this)
            .load(product.imageUrl)
            .placeholder(android.R.color.darker_gray)
            .error(android.R.color.darker_gray)
            .centerCrop()
            .into(binding.productImage)
        
        binding.addToCartButton.setOnClickListener {
            CartManager.addToCart(product)
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

