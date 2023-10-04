package com.example.shriganeshtradelink

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.shriganeshtradelink.ViewModel.CartViewModel
import com.example.shriganeshtradelink.model.CartModel
import java.nio.file.WatchEvent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    navController: NavHostController,
    modifier: Modifier=Modifier
){
    val cart by cartViewModel.cart.collectAsState(emptyList())
    var totalPrice by remember {
        mutableStateOf(0f)
    }
    LaunchedEffect(key1 = cart){
        totalPrice= getTotal(cart)
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color= MaterialTheme.colorScheme.background)
     {
        Scaffold (
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                             }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription ="back" )
                        }
                    },
                    title ={
                        Text(text = "Cart")
                    },
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Outlined.Search, contentDescription ="search" )
                        }
                    })
            }
        ){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Column(
                    modifier=modifier.padding(horizontal = 10.dp),
                ) {
                    Text(text = "Cart Items",
                        textAlign = TextAlign.Center,
                        modifier=modifier.padding(horizontal = 12.dp))
                    Spacer(modifier = modifier.height(5.dp))
                    Card (
                        modifier= modifier
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth(),
                    )  {
                        LazyColumn (
                            contentPadding = PaddingValues( horizontal = 12.dp)
                        ) {
                            items(cart.size){
                                Row(
                                    modifier=Modifier,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = cart[it].cartItem.name
                                        , modifier = modifier.weight(.5f),
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight(800)
                                    )
                                    Row(
                                        modifier=Modifier,
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        IconButton(onClick = {
                                            val temp = cartViewModel.add(cart[it].cartItem)
                                        }) {
                                            Icon(
                                                imageVector = Icons.Filled.Add,
                                                contentDescription = null,
                                            )
                                        }
                                        Text(text = cart[it].quantity.toString(),
                                            style = MaterialTheme.typography.bodySmall,
                                            fontWeight = FontWeight(800))
                                        IconButton(onClick = {
                                            val temp = cartViewModel.reomve(cart[it].cartItem)
                                        }) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_minus),
                                                contentDescription = null,
                                            )
                                        }
                                    }
                                    Spacer(modifier = modifier.width(4.dp))
                                    Text(text ="Rs." +(cart[it].quantity*cart[it].cartItem.price).toString(),
                                        modifier=modifier
                                    , textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight(800))
                                }
                            }

                        }
                }

                }
                Box(modifier=modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomStart){
                    ElevatedCard(
                        modifier=modifier.fillMaxHeight(.1f),
                        elevation = CardDefaults.cardElevation(6.dp)
                    ) {
                        Row(
                            modifier= modifier
                                .fillMaxSize()
                                .padding(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(text ="Rs." + totalPrice.toString())
                            Button(onClick = { /*TODO*/ })
                            {
                                Text(text = "Proceed to pay")
                            }
                        }
                    }

                }
            }
        }
    }
}
suspend fun getTotal(
    cart:List<CartModel>
):Float{
    var total:Float=0f
    cart.forEach{
        total+=it.cartItem.price*it.quantity
    }
    return total
}

