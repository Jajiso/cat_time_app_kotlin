package com.example.cattime.data

import android.content.Context
import com.example.cattime.data.model.Cat
import com.example.cattime.data.model.User
import com.example.cattime.domain.DataSourceInterface
import com.example.cattime.domain.SharedPreferenceHelper
import com.example.cattime.valueObject.Resource
import com.example.cattime.valueObject.RetrofitClient

class DataSource : DataSourceInterface {
    override suspend fun getCatList(): Resource<List<Cat>> {
        val catList = RetrofitClient.webservice.getBreedsList()
        for (cat in catList) {
            cat.catImage = RetrofitClient.webservice.getCatImage(cat.id)[0]
        }
        return Resource.Success(catList)
    }

    override suspend fun insertUser(context: Context, user: User) {
        SharedPreferenceHelper.setUserEmail(context, user.email)
        SharedPreferenceHelper.setUserPassword(context, user.password)
    }

    override suspend fun getUser(context: Context): Resource<User> {
        return Resource.Success(
                User(SharedPreferenceHelper.getUserEmail(context), SharedPreferenceHelper.getUserPassword(context))
        )
    }
}