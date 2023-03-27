package com.example.foodapp.data.repository

import com.example.foodapp.data.model.ResponseCategoriesList
import com.example.foodapp.data.model.ResponseFoodList
import com.example.foodapp.data.server.ApiService
import com.example.foodapp.utils.di.MyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher
import retrofit2.Response
import javax.inject.Inject

class FoodListRepository @Inject constructor(private val api:ApiService) {
    suspend fun getRandomFood():Flow<Response<ResponseFoodList>>{
        return flow { emit(api.getRandomFood()) }
            .flowOn(Dispatchers.IO)
    }

    suspend fun getCategoriesFood():Flow<MyResponse<ResponseCategoriesList>>{
        return flow {
            emit(MyResponse.loading())
            //response
            when(api.getCategoriesFood().code()){
                in 200..202 -> {
                   emit(MyResponse.success(api.getCategoriesFood().body()))
                }
                422 -> {
                    emit(MyResponse.error(""))
                }
                in 400..499 -> {
                    emit(MyResponse.error(""))
                }
                in 500..599 -> {
                    emit(MyResponse.error(""))
                }
            }
        }.catch {  emit(MyResponse.error(it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFoodList(letter:String):Flow<MyResponse<ResponseFoodList>>{
        return flow {
            emit(MyResponse.loading())
            //response
            when(api.getFoodList(letter).code()){
                in 200..202 -> {
                    emit(MyResponse.success(api.getFoodList(letter).body()))
                }
                422 -> {
                    emit(MyResponse.error(""))
                }
                in 400..499 -> {
                    emit(MyResponse.error(""))
                }
                in 500..599 -> {
                    emit(MyResponse.error(""))
                }
            }
        }.catch {  emit(MyResponse.error(it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFooBySearch(letter:String):Flow<MyResponse<ResponseFoodList>>{
        return flow {
            emit(MyResponse.loading())
            //response
            when(api.searchFood(letter).code()){
                in 200..202 -> {
                    emit(MyResponse.success(api.searchFood(letter).body()))
                }
                422 -> {
                    emit(MyResponse.error(""))
                }
                in 400..499 -> {
                    emit(MyResponse.error(""))
                }
                in 500..599 -> {
                    emit(MyResponse.error(""))
                }
            }
        }.catch {  emit(MyResponse.error(it.message.toString()))
        }.flowOn(Dispatchers.IO)

    }

    suspend fun getFoodByCategory(letter:String):Flow<MyResponse<ResponseFoodList>>{
        return flow {
            emit(MyResponse.loading())
            //response
            when(api.getFoodByCategory(letter).code()){
                in 200..202 -> {
                    emit(MyResponse.success(api.getFoodByCategory(letter).body()))
                }
                422 -> {
                    emit(MyResponse.error(""))
                }
                in 400..499 -> {
                    emit(MyResponse.error(""))
                }
                in 500..599 -> {
                    emit(MyResponse.error(""))
                }
            }
        }.catch {  emit(MyResponse.error(it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }
}