package com.example.cattime.domain

import android.content.Context
import com.example.cattime.data.model.Cat
import com.example.cattime.data.model.User
import com.example.cattime.valueObject.Resource

interface DataSourceInterface {
    suspend fun getCatList(): Resource<List<Cat>>
    suspend fun insertUser(context: Context, user: User)
    suspend fun getUser(context: Context): Resource<User>
}