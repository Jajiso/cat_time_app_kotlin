package com.example.cattime.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cattime.domain.RepositoryInterface

class VMFactory(private val repository: RepositoryInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RepositoryInterface::class.java).newInstance(repository)
    }
}