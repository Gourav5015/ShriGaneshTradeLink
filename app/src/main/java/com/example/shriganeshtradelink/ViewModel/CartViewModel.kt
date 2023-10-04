package com.example.shriganeshtradelink.ViewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shriganeshtradelink.model.CartModel
import com.example.shriganeshtradelink.model.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CartViewModel :ViewModel() {
    private var _cart= MutableStateFlow(emptyList<CartModel>())
    val cart :StateFlow<List<CartModel>> get() = _cart
    init {
    }
    fun add(item:Item){
        val currentCart = _cart.value.toMutableList()
        var existingIndex = currentCart.indexOfFirst { it.cartItem.name == item.name }

        if (existingIndex != -1) {
            // If the item is already in the cart, update its quantity
            val existing =currentCart[existingIndex]
            currentCart[existingIndex]=existing.copy(quantity = existing.quantity+1)
        } else {
            val existing=CartModel(item,1)
            // If the item is not in the cart, add it to the cart
            currentCart.add(existing)
        }

        _cart.value = currentCart
    }
    fun reomve(item:Item){
        val currentCart = _cart.value.toMutableList()
        var existingIndex = currentCart.indexOfFirst { it.cartItem.name == item.name }
        if (existingIndex !=-1 && currentCart[existingIndex].quantity!=0) {
            // If the item is already in the cart, update its quantity
            val existing =currentCart[existingIndex]
            currentCart[existingIndex]=existing.copy(quantity = existing.quantity-1)
        }
        if(existingIndex!=-1 && currentCart[existingIndex].quantity==0){
            // If the item is not in the cart, add it to the cart
            currentCart.remove(currentCart[existingIndex])
        }
        _cart.value = currentCart
    }
}