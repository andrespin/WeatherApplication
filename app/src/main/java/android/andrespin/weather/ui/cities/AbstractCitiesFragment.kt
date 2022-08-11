package android.andrespin.weather.ui.cities

import android.andrespin.weather.databinding.FragmentCitiesBinding
import android.andrespin.weather.domain.dataclasses.CityForecastData
import android.andrespin.weather.ui.base.BaseFragment
import android.andrespin.weather.ui.cities.adapter.CitiesAdapter
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch

abstract class AbstractCitiesFragment : BaseFragment<FragmentCitiesBinding, CitiesViewModel>() {

    protected fun showSearchFieldIsNotEmpty(binding: FragmentCitiesBinding) =
        with(binding.layoutSearch) {
            btnFind.visibility = View.VISIBLE
            imgCancel.visibility = View.VISIBLE
            imgLoupe.visibility = View.GONE
        }

    protected fun showSearchFieldIsEmpty(binding: FragmentCitiesBinding) =
        with(binding.layoutSearch) {
            btnFind.visibility = View.INVISIBLE
            imgCancel.visibility = View.GONE
            imgLoupe.visibility = View.VISIBLE
        }

    protected fun initAdapters(binding: FragmentCitiesBinding, citiesAdapter: CitiesAdapter) {
        binding.rvCities.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCities.adapter = citiesAdapter
    }

    protected fun initListeners(binding: FragmentCitiesBinding) {
        binding.layoutSearch.editSearch.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int,
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int,
            ) {
                lifecycleScope.launch {
                    model.intent.emit(CitiesIntent.SearchFieldTextChanged(s))
                }
            }
        })

        binding.layoutSearch.imgCancel.setOnClickListener {

        }

        binding.layoutSearch.btnFind.setOnClickListener {
            lifecycleScope.launch {
                model.intent.emit(CitiesIntent.FindCityCoordinates)
            }
        }
    }


}