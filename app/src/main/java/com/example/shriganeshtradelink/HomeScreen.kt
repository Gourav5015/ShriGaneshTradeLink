package com.example.shriganeshtradelink

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.shriganeshtradelink.ViewModel.CartViewModel
import com.example.shriganeshtradelink.ViewModel.ItemsViewModel
import com.example.shriganeshtradelink.model.Item
import com.example.shriganeshtradelink.ui.theme.chipColor
import com.example.shriganeshtradelink.ui.theme.chipSelectedColor

@SuppressLint("StateFlowValueCalledInComposition", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController:NavHostController,
    itemViewModel:ItemsViewModel,
    cartViewModel: CartViewModel
){

    val menus=listOf<NavItem>(
        NavItem("Home", Icons.Outlined.Home),
        NavItem("Cart", Icons.Outlined.ShoppingCart),
        NavItem("Account", Icons.Outlined.Person))


    val Items by itemViewModel.items.observeAsState(emptyList<Item>())
    Log.d("no", Items.size.toString())
    var selected by rememberSaveable {
        mutableStateOf(0)
    }
    var title by rememberSaveable {
        mutableStateOf("")
    }
    val scrollable = TopAppBarDefaults.pinnedScrollBehavior()
    val chips= listOf<Chips>(
        Chips("All"),
        Chips("Tamato Sauce"),
        Chips("Soya Sauce"),
        Chips("Chilli Sauce")
    )
    var selectedChip by rememberSaveable {
        mutableStateOf(0)
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollable.nestedScrollConnection),
        color= MaterialTheme.colorScheme.background){
        Scaffold(
            topBar = {
                TopAppBar(
                    title ={
                        Text(
                            text = title,
                         style = MaterialTheme.typography.titleMedium
                        , overflow = TextOverflow.Ellipsis
                        , maxLines = 1
                        , modifier = Modifier.fillMaxWidth(.8f))}
                    , scrollBehavior = scrollable,
                    actions = {
                        IconButton(onClick = {
                            navController.navigate("cart")
                        }) {
                            Icon(imageVector = Icons.Outlined.Search, contentDescription ="search" )
                        }
                    })
            },
            bottomBar={
                NavigationBar{
                    menus.forEachIndexed { index, navItem ->
                        NavigationBarItem(
                            selected = selected==index,
                            onClick = {
                                if (index==1) navController.navigate("cart")
                                else {
                                    selected=index
                                }
                                      },
                            label={
                                Text(text = navItem.name)
                            },
                            icon = {
                                Icon(imageVector = navItem.image, contentDescription =navItem.name)
                            },
                        )
                    }
                }
            }
        ){ values->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(values)
            ) {
                when(selected){
                    0-> {
                        title="Shri Ganesh Trade Link"
                        Surface (
                            modifier = Modifier
                                .nestedScroll(scrollable.nestedScrollConnection)
                                .padding(top = 10.dp),
                            color= MaterialTheme.colorScheme.background,
                        ) {
                            Scaffold(
                            ) {
                                Column(
                                    modifier = Modifier.padding(it)
                                ) {
                                    LazyRow (
                                        modifier = Modifier.padding(horizontal = 15.dp),
                                    ){
                                        items(chips){
                                            Chip(chip = it, modifier = Modifier
                                                .clip(RoundedCornerShape(10.dp))
                                                .clickable {
                                                           selectedChip=chips.indexOf(it)
                                                },
                                                color=   if (selectedChip==chips.indexOf(it))  chipSelectedColor else chipColor
                                            )
                                            Spacer(modifier = Modifier.width(10.dp))
                                        }

                                    }
                                    ItemSection(items = Items ,cartViewModel)



                                }
                            }
                        }
                    }
                    2-> {
                        title="Account"
                        Accountscreen()
                    }
                }
            }
        }
    }
}
@Composable
fun Chip(
    modifier: Modifier= Modifier,
    chip:Chips,
    color:Color
){
    Box (modifier = modifier
        .background(color)
        .border(1.dp, Color.hsl(209f, 1f, .80f, 1f), RoundedCornerShape(10.dp))
        .padding(horizontal = 15.dp, vertical = 10.dp)
    ){
        Text(text = chip.text,
            style = MaterialTheme.typography.bodySmall
            , fontWeight = FontWeight(600)
        )
    }
}