package android.andrespin.weather.data.room

import android.andrespin.weather.data.entities.CityForecastEntity
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CityForecastEntity::class], version = 1)
abstract class RoomDb : RoomDatabase() {
    abstract fun forecastDao(): ForecastDao
}