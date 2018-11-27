package com.mycloset.mycloset.ui.today.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mycloset.mycloset.R

class ColumnAdapter(var columnItems : ArrayList<ColumnItem>) : RecyclerView.Adapter<ColumnViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColumnViewHolder {
        val mainView : View = LayoutInflater.from(parent.context).inflate(R.layout.item_column, parent, false)
        return ColumnViewHolder(mainView)
    }

    override fun getItemCount(): Int = columnItems.size

    override fun onBindViewHolder(holder: ColumnViewHolder, position: Int) {
        when(columnItems[position].weather) {
            "sunny" -> {
                holder.columnWeather.setImageResource(R.drawable.weather_sun)
            }
            "little cloudy" -> {
                holder.columnWeather.setImageResource(R.drawable.weather_cloud)
            }
            "cloudy" -> {
                holder.columnWeather.setImageResource(R.drawable.weather_cloud_2)
            }
            "rainy" -> {
                holder.columnWeather.setImageResource(R.drawable.weather_rain)
            }
            else -> {
                holder.columnWeather.setImageResource(R.drawable.weather_snow)
            }
        }
        holder.columnTime.text = columnItems[position].time.toString()
        holder.columnTemper.text = columnItems[position].temper.toString()+"º"
        holder.columnSensible.text = columnItems[position].sensible.toString()+"º"
    }

}
