package android.andrespin.weather.di

import android.andrespin.weather.data.LocalRepoImpl
import android.andrespin.weather.data.RemoteRepoImpl
import android.andrespin.weather.data.api.GeoApi
import android.andrespin.weather.data.api.WeatherApi
import android.andrespin.weather.data.room.ForecastDao
import android.andrespin.weather.data.room.RoomDb
import android.andrespin.weather.domain.repo.LocalRepo
import android.andrespin.weather.domain.repo.RemoteRepo
import android.andrespin.weather.domain.usecases.DataInteraction
import android.andrespin.weather.domain.usecases.DataUseCases
import android.content.Context
import androidx.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@InstallIn(ViewModelComponent::class)
@Module
class DataModule {

    @Named("WeatherApi")
    @Provides
    fun getWeatherApi(gson: Gson): WeatherApi = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(WeatherApi::class.java)

    @Named("GeoApi")
    @Provides
    fun getGeoApi(gson: Gson): GeoApi = Retrofit.Builder()
        .baseUrl("http://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(GeoApi::class.java)

    @Provides
    fun gson(): Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    @Provides
    fun getRemoteRepo(
        @Named("GeoApi") geoApi: GeoApi,
        @Named("WeatherApi") weatherApi: WeatherApi
    ): RemoteRepo = RemoteRepoImpl(geoApi, weatherApi)

    @Provides
    internal fun createDatabase(@ApplicationContext appContext: Context): RoomDb {
        return Room.databaseBuilder(appContext, RoomDb::class.java, "db.1")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun getForecastDao(db: RoomDb): ForecastDao =
        db.forecastDao()

    @Provides
    fun getLocalRepo(forecastDao: ForecastDao) : LocalRepo = LocalRepoImpl(forecastDao)

    @Provides
    fun getDataUseCases(remoteRepo: RemoteRepo, localRepo: LocalRepo): DataInteraction =
        DataUseCases(remoteRepo, localRepo)

}




