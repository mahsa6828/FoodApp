package com.example.foodapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodapp.utils.FOOD_DB_TABLE
import dagger.Provides
import javax.inject.Inject


@Entity(tableName = FOOD_DB_TABLE)
data class FoodEntity(
    @PrimaryKey
    var id:Int = 0,
    var title:String = "",
    var img : String = ""
)