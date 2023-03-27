package com.example.foodapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.database.FoodEntity
import com.example.foodapp.data.model.ResponseFoodList
import com.example.foodapp.data.repository.FoodDetailRepository
import com.example.foodapp.utils.di.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDetailViewModel @Inject constructor(private val repository: FoodDetailRepository) : ViewModel() {
    var detailFood = MutableLiveData<MyResponse<ResponseFoodList>>()
    var isFavorite = MutableLiveData<Boolean>()

    fun getDetailFood(id:Int) = viewModelScope.launch {
        repository.foodDetail(id).collect{
            detailFood.postValue(it)
        }
    }

    fun isExists(id:Int,entity: FoodEntity) = viewModelScope.launch {
        repository.existsFood(id).collect{
            if (it){
                repository.deleteFood(entity)
                isFavorite.postValue(false)
            }
            else
            {
                repository.saveFood(entity)
                isFavorite.postValue(true)
            }
        }
    }




}