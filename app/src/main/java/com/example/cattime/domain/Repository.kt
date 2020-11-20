package com.example.cattime.domain

import android.content.Context
import com.example.cattime.data.model.Cat
import com.example.cattime.data.model.User
import com.example.cattime.valueObject.Resource

class Repository(private val dataSource: DataSourceInterface) : RepositoryInterface {
    override suspend fun getCatList(): Resource<List<Cat>> {
        return dataSource.getCatList()
    }

    override suspend fun getUser(context: Context): Resource<User> {
        return dataSource.getUser(context)
    }

    override suspend fun insertUser(context: Context, user: User) {
        dataSource.insertUser(context, user)
    }
}