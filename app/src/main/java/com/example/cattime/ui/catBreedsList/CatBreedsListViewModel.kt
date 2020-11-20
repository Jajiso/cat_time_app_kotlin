package com.example.cattime.ui.catBreedsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.cattime.domain.RepositoryInterface
import com.example.cattime.valueObject.Resource
import kotlinx.coroutines.Dispatchers

class CatBreedsListViewModel(private val repository: RepositoryInterface) : ViewModel() {

    val fetchCatList = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repository.getCatList())
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}