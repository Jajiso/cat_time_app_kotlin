package com.example.cattime.data.model

data class Cat(
        var catImage: CatImage,
        val name: String = "",
        val description: String = "",
        val id: String = "",
        val wikipedia_url: String = "",
        val country_code: String = "",
        val origin: String = ""
)

data class CatImage(
        val url: String = ""
)