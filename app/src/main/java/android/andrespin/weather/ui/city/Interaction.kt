package android.andrespin.weather.ui.city

import android.andrespin.weather.domain.dataclasses.CityForecastData
import android.andrespin.weather.ui.base.Event
import android.andrespin.weather.ui.base.Intent
import android.andrespin.weather.ui.base.State

sealed class CityState : State {
    object Idle : CityState()
    object Loading : CityState()
}

sealed class CityEvent : Event {
    data class SetCityInfo(val data: CityForecastData) : CityEvent()
}

sealed class CityIntent : Intent {

    data class GetCityForecast(val city: String) : CityIntent()

}