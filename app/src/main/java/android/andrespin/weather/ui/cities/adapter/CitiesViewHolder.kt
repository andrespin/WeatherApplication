package android.andrespin.weather.ui.cities.adapter

import android.andrespin.weather.databinding.ItemRvCityTempBinding
import android.andrespin.weather.domain.dataclasses.CityForecastData
import androidx.recyclerview.widget.RecyclerView

class CitiesViewHolder(
    private val vb: ItemRvCityTempBinding
) : RecyclerView.ViewHolder(vb.root) {

    fun bind(data: CityForecastData) {
        vb.txtNameOfCity.text = data.nameOfCity
        vb.txtTempOfTheCityToday.text = "${data.firstDayTemp} C"
    }

}