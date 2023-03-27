package com.example.foodapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.model.Category
import com.example.foodapp.data.model.Meal
import com.example.foodapp.data.model.ResponseCategoriesList
import com.example.foodapp.data.model.ResponseFoodList
import com.example.foodapp.data.repository.FoodListRepository
import com.example.foodapp.utils.di.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FoodListViewModel @Inject constructor(private val repository: FoodListRepository) : ViewModel() {
    var randomFood = MutableLiveData<List<Meal>>()
    var filterList = MutableLiveData<MutableList<Char>>()
    var categoriesFood = MutableLiveData<MyResponse<ResponseCategoriesList>>()
    var foodList = MutableLiveData<MyResponse<ResponseFoodList>>()
    var loading = MutableLiveData<Boolean>()

    fun loadRandomFood() = viewModelScope.launch {
        repository.getRandomFood().collect{
            randomFood.postValue(it.body()?.meals)
        }
    }

    fun loadFilterList() = viewModelScope.launch {
        val letters = listOf('A' ..  'Z').flatten().toMutableList()
        filterList.postValue(letters)
    }

    fun loadCategories() = viewModelScope.launch {
        repository.getCategoriesFood().collect{
            categoriesFood.postValue(it)
        }
    }

    fun loadFoodList(letter: String) = viewModelScope.launch {
        repository.getFoodList(letter).collect{
            foodList.postValue(it)
        }
    }

    fun loadFoodBySearch(letter:String) = viewModelScope.launch {
        repository.getFooBySearch(letter).collect{
            foodList.postValue(it)
        }
    }

    fun loadFoodByCategory(letter:String) = viewModelScope.launch {
        repository.getFoodByCategory(letter).collect{
            foodList.postValue(it)
        }
    }
}