package com.example.cattime.ui.sharedViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cattime.data.model.Cat

class SharedCatViewModel : ViewModel() {
    val cat = MutableLiveData<Cat>()

    fun shareCat(cat: Cat) {
        this.cat.value = cat
    }
}