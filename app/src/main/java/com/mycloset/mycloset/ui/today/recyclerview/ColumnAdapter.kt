package com.mycloset.mycloset.ui.today.recyclerview

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mycloset.mycloset.R
import com.mycloset.mycloset.R.id.today_column_rv

class ColumnAdapter(var columnItems : ArrayList<ColumnItem>) : RecyclerView.Adapter<ColumnViewHolder>() {
    private lateinit var onItemClick : View.OnClickListener

    fun setOnItemClickListener(l : View.OnClickListener){
        onItemClick = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColumnViewHolder {
        val mainView : View = LayoutInflater.from(parent.context).inflate(R.layout.item_column, parent, false)
        mainView.setOnClickListener(onItemClick)
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
        if(columnItems[position].selected)
            holder.columnLayout.setBackgroundColor(Color.parseColor("#D9EAE7"))
        else
            holder.columnLayout.setBackgroundColor(Color.parseColor("#F0F7F6"))
    }

}
