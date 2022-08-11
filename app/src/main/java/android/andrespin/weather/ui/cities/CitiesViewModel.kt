package android.andrespin.weather.ui.cities

import android.andrespin.weather.data.entities.CityForecastEntity
import android.andrespin.weather.domain.dataclasses.CityForecastData
import android.andrespin.weather.domain.usecases.DataInteraction
import android.andrespin.weather.keyId
import android.andrespin.weather.ui.base.AppViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.andrespin.weather.domain.utils.Result
import android.andrespin.weather.domain.utils.convertEntityListToCityForecastDataList
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.delay

@HiltViewModel
class CitiesViewModel
@Inject constructor(
    private val interactor: DataInteraction,
) : AppViewModel<CitiesIntent, CitiesState, CitiesEvent>() {

    override val tag: String
        get() = TODO("Not yet implemented")
    override val sendState: MutableStateFlow<CitiesState>
        get() = TODO("Not yet implemented")
    override val state: StateFlow<CitiesState>
        get() = TODO("Not yet implemented")

    private lateinit var cityToFind: String

    init {
        handleIntent()
    }

    override fun handleIntent() {
        viewModelScope.launch {
            getIntent.collectLatest {
                when (it) {
                    is CitiesIntent.SearchFieldTextChanged -> searchFieldTextChanged(it)
                    is CitiesIntent.FindCityCoordinates -> findCityCoordinates()
                    is CitiesIntent.AddCity -> addCity(it.city.city)
                    is CitiesIntent.UpdateCitiesWeather -> updateCitiesWeather()
                }
            }
        }
    }

    private fun updateCitiesWeather() = viewModelScope.launch {

        var allForecastsLocal = mutableListOf<CityForecastEntity>()
        val updatedForecasts = mutableListOf<CityForecastData>()

        val j = launch(start = CoroutineStart.LAZY) {

            val msc = interactor.getCityForecastLocal(
                "Moscow")
            val spb = interactor.getCityForecastLocal(
                "Saint Petersburg")

            if (msc == null) addCity("Moscow")
            if (spb == null) addCity("Saint Petersburg")

        }

        val j1 = launch(start = CoroutineStart.LAZY) {
            allForecastsLocal = interactor.getAllForecastsLocal() as MutableList<CityForecastEntity>
            sendEvent.emit(CitiesEvent.SetDataToAdapter(convertEntityListToCityForecastDataList(allForecastsLocal)))
        }

        val j2 = launch(start = CoroutineStart.LAZY) {
            allForecastsLocal.indices.forEach { i ->
                allForecastsLocal[i].nameOfCity
                when (val response =
                    interactor.getCityWeather(allForecastsLocal[i].nameOfCity, "metric", keyId)) {
                    is Result.Success -> {
                        updatedForecasts.add(response.data)
                    }

                    is Result.Failure -> {
                        val data = CityForecastData(
                            allForecastsLocal[i].nameOfCity,
                            allForecastsLocal[i].firstDayDate,
                            allForecastsLocal[i].firstDayTemp,
                            allForecastsLocal[i].secondDayDate,
                            allForecastsLocal[i].secondDayTemp,
                            allForecastsLocal[i].thirdDayDate,
                            allForecastsLocal[i].thirdDayTemp
                        )
                        updatedForecasts.add(data)
                    }
                }
            }
        }

        val j3 = launch(start = CoroutineStart.LAZY) {
            sendEvent.emit(CitiesEvent.SetDataToAdapter(updatedForecasts))
            interactor.updateListOfCityWeather(updatedForecasts)
        }

        j.start()
        j.join()

        j1.start()
        j1.join()

        j2.start()
        j2.join()

        j3.start()
        j3.join()

    }


    private fun addCity(it: String) = viewModelScope.launch {
        when (val response = interactor.getCityWeather(it, "metric", keyId)) {
            is Result.Success -> {
                println("response.data " + response.data)
                interactor.addForecastToDb(response.data)
                sendEvent.emit(CitiesEvent.AddDataToAdapter(response.data))
            }

            is Result.Failure -> {

            }
        }
    }

    private fun findCityCoordinates() = viewModelScope.launch {
        when (val response = interactor.getCityCoordinates(cityToFind, "5", keyId)) {
            is Result.Success -> {
                sendEvent.emit(CitiesEvent.ShowCityIsFound(response.data))
            }

            is Result.Failure -> {
                sendEvent.emit(CitiesEvent.ShowCityIsNotFound)
            }
        }
    }

    private suspend fun searchFieldTextChanged(it: CitiesIntent.SearchFieldTextChanged) =
        viewModelScope.launch {
            if (it.s.isBlank()) {
                cityToFind = ""
                sendEvent.emit(CitiesEvent.ShowSearchFieldIsEmpty)
            } else {
                cityToFind = it.s.toString()
                sendEvent.emit(CitiesEvent.ShowSearchFieldIsNotEmpty)
            }
        }

}