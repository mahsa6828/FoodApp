package com.example.foodapp.data.server

import com.example.foodapp.data.model.ResponseCategoriesList
import com.example.foodapp.data.model.ResponseFoodList
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("random.php")
    suspend fun getRandomFood() : Response<ResponseFoodList>

    @GET("categories.php")
    suspend fun getCategoriesFood() : Response<ResponseCategoriesList>

    @GET("search.php")
    suspend fun getFoodList(@Query("f") letter:String) : Response<ResponseFoodList>

    @GET("search.php")
    suspend fun searchFood(@Query("s") letter:String) : Response<ResponseFoodList>

    @GET("filter.php")
    suspend fun getFoodByCategory(@Query("c") letter:String) : Response<ResponseFoodList>

    @GET("lookup.php")
    suspend fun foodDetail(@Query("i") id:Int) : Response<ResponseFoodList>



}