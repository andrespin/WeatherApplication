package android.andrespin.weather.ui.cities.adapter

import android.andrespin.weather.R
import android.andrespin.weather.databinding.ItemRvCityTempBinding
import android.andrespin.weather.domain.dataclasses.CityForecastData
import android.andrespin.weather.ui.cities.CitiesFragment
import android.andrespin.weather.ui.cities.CitiesIntent
import android.andrespin.weather.ui.cities.CitiesViewModel
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class CitiesAdapter(
    private val fragment: CitiesFragment,
    private val viewModel: CitiesViewModel,
) : RecyclerView.Adapter<CitiesViewHolder>() {


    private var data: MutableList<CityForecastData> = mutableListOf()

    fun setData(data: List<CityForecastData>) {
        this.data = data as MutableList<CityForecastData>
        notifyDataSetChanged()
    }

    fun addData(data: CityForecastData) {
        this.data.add(data)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder =
        CitiesViewHolder(
            ItemRvCityTempBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        ).apply {
            itemView.setOnClickListener {
                val pos = this.layoutPosition

                val bundle = bundleOf(
                    "City" to data[pos].nameOfCity
                )

                findNavController(fragment).navigate(R.id.action_cities_to_city, bundle)

//                fragment.lifecycleScope.launch {
//
//
//
//                    viewModel.intent.emit(CitiesIntent.OpenCityForecast(data[pos]))
//                }
            }
        }
}
