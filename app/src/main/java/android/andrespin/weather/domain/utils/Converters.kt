package android.andrespin.weather.domain.utils

import android.andrespin.weather.data.entities.CityForecastEntity
import android.andrespin.weather.data.server_responses.CityCoordinatesResponse
import android.andrespin.weather.data.server_responses.CityWeatherForecast
import android.andrespin.weather.domain.dataclasses.CityCoordinatesData
import android.andrespin.weather.domain.dataclasses.CityForecastData

fun convertToCityCoordinatesData(response: CityCoordinatesResponse, city: String) =
    CityCoordinatesData(
        city,
        response[0].lat,
        response[0].lon,
    )

fun convertToCityForecastData(response: CityWeatherForecast): CityForecastData =
    CityForecastData(
        response.city.name,
        response.list[0].dt_txt,
        response.list[0].main.temp.toString(),
        response.list[1].dt_txt,
        response.list[1].main.temp.toString(),
        response.list[2].dt_txt,
        response.list[2].main.temp.toString()
    )

fun convertEntityListToCityForecastDataList(entity: List<CityForecastEntity>)
        : List<CityForecastData> {
    val list = mutableListOf<CityForecastData>()
    for (i in 0 until entity.size) {
        list.add(CityForecastData(
            entity[i].nameOfCity,
            entity[i].firstDayDate,
            entity[i].firstDayTemp,
            entity[i].secondDayDate,
            entity[i].secondDayTemp,
            entity[i].thirdDayDate,
            entity[i].thirdDayTemp
        )
        )
    }
    return list
}







