package com.example.shriganeshtradelink.model

data class Item @JvmOverloads constructor(
    val name: String="",
    val image: String ="",
    val id:String = "",
    val type: String="",
    val price: Int=0,
    val description:String=""
): java.io.Serializable
{
}
