package android.andrespin.weather.ui.cities

import android.andrespin.weather.domain.dataclasses.CityCoordinatesData
import android.andrespin.weather.domain.dataclasses.CityForecastData
import android.andrespin.weather.ui.base.Event
import android.andrespin.weather.ui.base.Intent
import android.andrespin.weather.ui.base.State

sealed class CitiesState : State {
    object Idle : CitiesState()
    object Loading : CitiesState()
}

sealed class CitiesEvent : Event {

    object ShowSearchFieldIsEmpty : CitiesEvent()

    object ShowSearchFieldIsNotEmpty : CitiesEvent()

    data class ShowCityIsFound(val city: CityCoordinatesData) : CitiesEvent()

    data class SetDataToAdapter(val data: List<CityForecastData>) : CitiesEvent()

    data class AddDataToAdapter(val data: CityForecastData) : CitiesEvent()

    object ShowCityIsNotFound : CitiesEvent()

}

sealed class CitiesIntent : Intent {

    data class SearchFieldTextChanged(val s: CharSequence) : CitiesIntent()

    data class AddCity(val city: CityCoordinatesData) : CitiesIntent()

    data class OpenCityForecast(val forecast: CityForecastData) : CitiesIntent()

    object FindCityCoordinates : CitiesIntent()

    data class UpdateCitiesWeather(val areMscAndSpbAdded: Boolean) : CitiesIntent()

}