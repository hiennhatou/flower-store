package edu.ou.flowerstore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartItemAdapter(
    private val cartItems: List<CartItem>,
    private val onQuantityChanged: (CartItem, Int) -> Unit
) : RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>() {

    class CartItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage: ImageView = view.findViewById(R.id.ivProductImage)
        val productName: TextView = view.findViewById(R.id.tvProductName)

        val productPrice: TextView = view.findViewById(R.id.tvProductPrice)
        val quantity: TextView = view.findViewById(R.id.tvQuantity)
        val decreaseButton: ImageButton = view.findViewById(R.id.btnDecreaseQuantity)
        val increaseButton: ImageButton = view.findViewById(R.id.btnIncreaseQuantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart_product, parent, false)
        return CartItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.productName.text = cartItem.name

        holder.productPrice.text = "$${cartItem.price}"
        holder.quantity.text = cartItem.quantity.toString()

        // Placeholder for image
        holder.productImage.setImageResource(R.drawable.ic_placeholder)

        holder.decreaseButton.setOnClickListener {
            val newQuantity = cartItem.quantity - 1
            if (newQuantity > 0) {
                onQuantityChanged(cartItem, newQuantity)
            }
        }

        holder.increaseButton.setOnClickListener {
            val newQuantity = cartItem.quantity + 1
            onQuantityChanged(cartItem, newQuantity)
        }
    }

    override fun getItemCount(): Int = cartItems.size
}
