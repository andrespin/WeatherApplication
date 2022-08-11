package android.andrespin.weather.ui.city

import android.andrespin.weather.data.entities.CityForecastEntity
import android.andrespin.weather.ui.base.BaseFragment
import android.andrespin.weather.databinding.FragmentCityBinding
import android.andrespin.weather.domain.dataclasses.CityForecastData
import android.andrespin.weather.ui.cities.CitiesEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CityFragment : BaseFragment<FragmentCityBinding, CityViewModel>() {

    override val viewModelClass: Class<CityViewModel>
        get() = CityViewModel::class.java

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCityBinding
        get() = FragmentCityBinding::inflate


    override fun initClickListeners() {

    }

    override fun process() {
        super.process()
        lifecycleScope.launch {
            var cityName = ""
            val j1 = launch(start = CoroutineStart.LAZY, context = Dispatchers.Main) {
                 cityName = arguments?.getString("City").toString()
            }
            val j2 = launch(start = CoroutineStart.LAZY) {
                model.intent.emit(CityIntent.GetCityForecast(cityName))
            }
            j1.start()
            j1.join()
            j2.start()
            j2.join()
        }
    }

    override fun observeViewModel() {

        lifecycleScope.launch {
            model.event.collectLatest {
                when (it) {
                    is CityEvent.SetCityInfo -> setCityInfo(it.data)
                }
            }
        }

    }

    private fun setCityInfo(data: CityForecastData) {
        binding.txtCityName.text = data.nameOfCity

        binding.txtFirstDate.text = data.firstDayDate
        binding.txtFirstTemp.text = data.firstDayTemp

        binding.txtSecondDate.text = data.secondDayDate
        binding.txtSecondTemp.text = data.secondDayTemp

        binding.txtThirdDate.text = data.thirdDayDate
        binding.txtThirdTemp.text = data.thirdDayTemp

    }


}