package edu.ou.flowerstore

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Cart : AppCompatActivity() {

    private lateinit var cartItems: MutableList<CartItem>
    private lateinit var adapter: CartItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        // Sample data
        cartItems = mutableListOf(
            CartItem("Hoa hồng", 83.97, 1),
            CartItem("Hoa lan", 120.0,  1)
        )

        // Initialize RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewCartItems)
        adapter = CartItemAdapter(cartItems) { item, newQuantity ->
            item.quantity = newQuantity
            adapter.notifyDataSetChanged()
            updateSummary()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        updateSummary()
    }

    private fun updateSummary() {
        val subtotal = cartItems.sumOf { it.price * it.quantity }
        val deliveryFee = 25.0
        val discount = 35.0
        val totalCost = subtotal + deliveryFee - discount

        findViewById<TextView>(R.id.tvSubtotal).text = "$${"%.2f".format(subtotal)}"
        findViewById<TextView>(R.id.tvDeliveryFee).text = "$${"%.2f".format(deliveryFee)}"
        findViewById<TextView>(R.id.tvDiscount).text = "-$${"%.2f".format(discount)}"
        findViewById<TextView>(R.id.tvTotalCost).text = "$${"%.2f".format(totalCost)}"
    }
}
