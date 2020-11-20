package com.example.cattime.ui.sharedViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.cattime.data.model.User
import com.example.cattime.domain.RepositoryInterface
import com.example.cattime.valueObject.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedUserViewModel(private val repository: RepositoryInterface) : ViewModel() {

    fun verifyEmail(email: String): Boolean {
        return User.verifyEmail(email)
    }

    fun verifyPassword(password: String): Boolean {
        return User.verifyPassword(password)
    }

    fun insertUser(context: Context, email: String, password: String) {
        viewModelScope.launch {
            repository.insertUser(context, User(email, password))
        }
    }

    fun getUser(context: Context) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repository.getUser(context))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}