package android.andrespin.weather.ui.city

import android.andrespin.weather.domain.dataclasses.CityForecastData
import android.andrespin.weather.domain.usecases.DataInteraction
import android.andrespin.weather.ui.base.AppViewModel
import android.andrespin.weather.ui.cities.CitiesIntent
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel
@Inject constructor(
    private val interactor: DataInteraction,
) : AppViewModel<CityIntent, CityState, CityEvent>() {

    override val tag: String
        get() = TODO("Not yet implemented")
    override val sendState: MutableStateFlow<CityState>
        get() = TODO("Not yet implemented")
    override val state: StateFlow<CityState>
        get() = TODO("Not yet implemented")

    init {
        handleIntent()
    }

    override fun handleIntent() {
        viewModelScope.launch {
            getIntent.collectLatest {
                when (it) {
                    is CityIntent.GetCityForecast -> getCityForecast(it.city)
                }
            }
        }
    }

    private fun getCityForecast(city: String) {
        viewModelScope.launch {
            val d = interactor.getCityForecastLocal(city)
            sendEvent.emit(CityEvent.SetCityInfo(
                CityForecastData(
                    d.nameOfCity,
                    d.firstDayDate,
                    d.firstDayTemp,
                    d.secondDayDate,
                    d.secondDayTemp,
                    d.thirdDayDate,
                    d.thirdDayTemp
                )
            ))
        }
    }

}