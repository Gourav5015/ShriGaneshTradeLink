package com.example.shriganeshtradelink

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.shriganeshtradelink.ViewModel.CartViewModel
import com.example.shriganeshtradelink.model.CartModel
import com.example.shriganeshtradelink.model.Item
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.shadow
import androidx.lifecycle.LiveData


@Composable
fun ItemSection(
    items: List<Item>,
    cartViewModel: CartViewModel,
    modifier: Modifier= Modifier
){
    val cart by cartViewModel.cart.collectAsState()

    LazyColumn (
        modifier=modifier.padding(top = 10.dp)
    ){
        items(items.size) {
            ItemLayout(item =items[it] ,cartViewModel,cart,
                modifier= modifier
                    .fillMaxWidth()
                    .padding(2.dp))
            Divider()
        }
    }
}
@SuppressLint("UnrememberedMutableState")
@Composable
fun ItemLayout(
    item:Item,
    cartViewModel: CartViewModel,
    cart:List<CartModel>,
    modifier: Modifier=Modifier
){
    Row (
        modifier = modifier
            .height(140.dp)
            .padding(horizontal = 10.dp)
    ){
        AsyncImage(model = item.image, contentDescription =item.name,
                modifier = modifier
                    .weight(.4f)
                    .fillMaxSize()
                    .clip(
                        RoundedCornerShape(5.dp)
                    )
                    .shadow(2.dp)
                    .padding(2.dp),
            contentScale = ContentScale.Crop
            )
        Spacer(modifier = Modifier.width(20.dp))
        Column(
            modifier = modifier
                .fillMaxHeight()
                .weight(.7f),
            verticalArrangement = Arrangement.Top,
        ) {
            Text(text = item.name
            , fontWeight = FontWeight(700),
                fontSize = 17.sp
                , style = MaterialTheme.typography.bodySmall
                , fontFamily = FontFamily.Default
                ,
            )
            Text(text = "Rs. "+ item.price.toString(),
                fontSize = 14.sp,modifier=modifier.padding(start = 5.dp))
            if(cart.find { it.cartItem.name == item.name} ==null) {
                Log.d("cart", "ItemLayout: "+cart.toString())
                Log.d("cart", "ItemLayout: "+item.toString())
                ElevatedButton(onClick = {
                    cartViewModel.add(item)
                    Log.d("view", "ItemLayout: "+cartViewModel.cart.value.toString())
                }, shape = RoundedCornerShape(10.dp)) {
                    Text(text = "Add")
                }
            }
            else {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        cartViewModel.add(item)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Add, contentDescription = null,
                            Modifier
                                .border(1.dp, Color.Black, CircleShape)
                                .size(24.dp, 24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = cart.find { it.cartItem.name==item.name }?.quantity.toString())
                    Spacer(modifier = Modifier.width(5.dp))
                    IconButton(onClick = {
                        cartViewModel.reomve(item)
                        Log.d("view", "ItemLayout: "+cartViewModel.cart.value.toString())
                        Log.d("view", "ItemLayout: "+cart.toString())
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_minus),
                            contentDescription = null,
                            Modifier
                                .border(1.dp, Color.Black, CircleShape)
                                .size(24.dp, 24.dp)
                        )
                    }
                }
            }
        }
    }
}