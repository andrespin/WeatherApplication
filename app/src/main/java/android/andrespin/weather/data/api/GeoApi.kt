package android.andrespin.weather.data.api

import android.andrespin.weather.data.server_responses.CityCoordinatesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoApi {

    @GET("geo/1.0/direct?")
    suspend fun getCityCoordinates(
        @Query("q") q: String,
        @Query("limit") limit: String,
        @Query("appid") appid: String
    ) : Response<CityCoordinatesResponse>

}