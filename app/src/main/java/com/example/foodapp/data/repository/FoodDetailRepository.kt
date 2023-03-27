package com.example.foodapp.data.repository

import com.example.foodapp.data.database.FoodDao
import com.example.foodapp.data.database.FoodEntity
import com.example.foodapp.data.model.ResponseFoodList
import com.example.foodapp.data.server.ApiService
import com.example.foodapp.utils.di.MyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FoodDetailRepository @Inject constructor(private val api:ApiService,private val dao: FoodDao) {
    suspend fun foodDetail(id:Int) : Flow<MyResponse<ResponseFoodList>> {
        return flow {
            emit(MyResponse.loading())
            when(api.foodDetail(id).code())
            {
                in 200..202 -> {
                    emit(MyResponse.success(api.foodDetail(id).body()))
                }
            }
        }.catch { emit(MyResponse.error(it.message.toString())) }
            .flowOn(Dispatchers.IO)
    }

    suspend fun saveFood(entity: FoodEntity) = dao.saveFood(entity)
    suspend fun deleteFood(entity: FoodEntity) = dao.deleteFood(entity)
    fun existsFood(id:Int) = dao.existsFood(id)
}