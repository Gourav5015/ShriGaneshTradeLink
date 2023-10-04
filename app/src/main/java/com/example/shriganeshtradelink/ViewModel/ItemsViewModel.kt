package com.example.shriganeshtradelink.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shriganeshtradelink.model.Item
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import okhttp3.Dispatcher

class ItemsViewModel :ViewModel() {
    var _items : MutableLiveData<MutableList<Item>> = MutableLiveData(mutableListOf())
    val items :LiveData<MutableList<Item>> get()=_items
    var fireBasedb:FirebaseFirestore
    var _itemReference :MutableList<DocumentSnapshot> = mutableListOf()
     init {
        fireBasedb=FirebaseFirestore.getInstance()
        viewModelScope.launch{
            var response=getItems()
            _items.value=response
            listener()
        }
    }
    suspend fun getItems() :MutableList<Item>{

        var responseList=fireBasedb.collection("Items").get().await().documents
        _itemReference=responseList as MutableList<DocumentSnapshot>
        var response:MutableList<Item> = responseList.map {
            it-> it.toObject(Item::class.java)
        } as MutableList<Item>
        return response
    }
    suspend fun listener(
    ){
        _itemReference.forEachIndexed{index,it->
            it.reference?.addSnapshotListener{ snapshot,e->
                if (e != null) {
                    // Handle errors
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    Log.d("yes", "listener: "+snapshot.toString())
                    _items.value?.set(index, snapshot.toObject(Item::class.java)!!)
                    _itemReference[index]=(snapshot)
                } else {
                    // Document does not exist
                }
            }
        }

    }
    suspend fun writeItem(){

        val i= listOf<Item>(
            Item("Chilli Sauce","https://www.vikashbazar.in/wp-content/uploads/2020/10/chillyyy.jpg","124","sauce",120,"Try it"),
            Item("Tamato Sauce","https://i0.wp.com/www.apkarasan.com/wp-content/uploads/2021/11/new-lall-s-tomato-ketchup-1kg-e1636700544636.jpg?fit=702%2C702","124","sauce",120,"Try it"),
            Item("Tamato Sauce pauch","data:image/jpeg;base64,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","124","sauce",120,"Try it"),
            Item("Soya Sauce ","https://www.themmgrocery.com/wp-content/uploads/2022/06/download-2022-06-11T160228.510-1.jpg","124","sauce",120,"Try it"),
        )
        i.forEach{
            fireBasedb.collection("Items").add(it)
        }
    }
}