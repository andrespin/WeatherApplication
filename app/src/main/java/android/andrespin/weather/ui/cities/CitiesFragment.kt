package android.andrespin.weather.ui.cities

import android.andrespin.weather.databinding.FragmentCitiesBinding
import android.andrespin.weather.domain.dataclasses.CityCoordinatesData
import android.andrespin.weather.domain.dataclasses.CityForecastData
import android.andrespin.weather.ui.cities.adapter.CitiesAdapter
import android.content.Context
import android.content.SharedPreferences
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CitiesFragment : AbstractCitiesFragment() {

    override val viewModelClass: Class<CitiesViewModel>
        get() = CitiesViewModel::class.java

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCitiesBinding
        get() = FragmentCitiesBinding::inflate

    private val citiesAdapter: CitiesAdapter
            by lazy { CitiesAdapter(this, model) }


    override fun initClickListeners() {


        initListeners(binding)

//        binding.layoutSearch.editSearch.addTextChangedListener(object : TextWatcher {
//
//            override fun afterTextChanged(s: Editable) {}
//
//            override fun beforeTextChanged(
//                s: CharSequence, start: Int,
//                count: Int, after: Int,
//            ) {
//            }
//
//            override fun onTextChanged(
//                s: CharSequence, start: Int,
//                before: Int, count: Int,
//            ) {
//                lifecycleScope.launch {
//                    model.intent.emit(CitiesIntent.SearchFieldTextChanged(s))
//                }
//            }
//        })
//
//        binding.layoutSearch.imgCancel.setOnClickListener {
//
//        }
//
//        binding.layoutSearch.btnFind.setOnClickListener {
//            lifecycleScope.launch {
//                model.intent.emit(CitiesIntent.FindCityCoordinates)
//            }
//        }

    }

    override fun process() {
        super.process()
        initAdapters(binding, citiesAdapter)
        lifecycleScope.launch {
            model.intent.emit(
                CitiesIntent.UpdateCitiesWeather(true)
            )
        }
    }

    override fun observeViewModel() {
        lifecycleScope.launch {
            model.event.collectLatest {
                when (it) {
                    is CitiesEvent.ShowSearchFieldIsEmpty -> showSearchFieldIsEmpty(binding)
                    is CitiesEvent.ShowSearchFieldIsNotEmpty -> showSearchFieldIsNotEmpty(binding)
                    is CitiesEvent.ShowCityIsNotFound -> showCityIsNotFound(binding)
                    is CitiesEvent.ShowCityIsFound -> showCityIsFound(it.city, binding)
                    is CitiesEvent.SetDataToAdapter -> citiesAdapter.setData(it.data)
                    is CitiesEvent.AddDataToAdapter -> citiesAdapter.addData(it.data)
                }
            }
        }
    }

    private fun showCityIsFound(city: CityCoordinatesData, binding: FragmentCitiesBinding) {
        binding.layoutFoundCity.root.visibility = View.VISIBLE
        binding.layoutCityIsNotFound.root.visibility = View.GONE
        binding.layoutFoundCity.txtFoundCity.text = city.city

        binding.layoutFoundCity.btnAddFoundCity.setOnClickListener {
            lifecycleScope.launch {
                model.intent.emit(CitiesIntent.AddCity(city))
            }
            binding.layoutFoundCity.root.visibility = View.GONE
        }

        binding.layoutFoundCity.btnCancelAddingCity.setOnClickListener {
            binding.layoutFoundCity.root.visibility = View.GONE
        }
    }

    private fun showCityIsNotFound(binding: FragmentCitiesBinding) {
        binding.layoutFoundCity.root.visibility = View.GONE
        binding.layoutCityIsNotFound.root.visibility = View.VISIBLE
        binding.layoutCityIsNotFound.btnOk.setOnClickListener {
            binding.layoutCityIsNotFound.root.visibility = View.GONE
        }
    }

}