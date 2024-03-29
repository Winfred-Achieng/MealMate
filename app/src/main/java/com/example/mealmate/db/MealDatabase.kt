package com.example.mealmate.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mealmate.pojo.Meal

@Database(entities = [Meal::class], version = 1 )
@TypeConverters(MealTypeConverter::class)
abstract class MealDatabase:RoomDatabase() {

    abstract fun MealDao():MealDao

    companion object {
        @Volatile
        var INSTANCE:MealDatabase? = null

        @Synchronized
        fun getInstance(context:Context) : MealDatabase{
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealDatabase::class.java,
                    "Meal.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()

            }
            return INSTANCE as MealDatabase
        }
    }
}