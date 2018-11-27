package com.mycloset.mycloset.ui.record

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mycloset.mycloset.R

class RecordAdapter(var recordItems: ArrayList<RecordItem>) : RecyclerView.Adapter<RecordViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_card, parent, false)
        //mainView.setOnClickListener(onItemClick)
        return RecordViewHolder(mainView)
    }

    override fun getItemCount(): Int = recordItems.size

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.recordDate.text = recordItems[position].date
//        weather ="sunny, cloud"
        when(recordItems[position].weather){
            "sunny"->{
                holder.recordWeather.setImageResource(R.drawable.weather_sun)
                holder.recordWeatherBackground.setImageResource(R.color.colorPrimary)
                //holder.recordWeatherBackground.setImageResource(R.drawable.weather_sun)
            }
            "cloud"->{
                holder.recordWeather.setImageResource(R.drawable.weather_cloud)
                holder.recordWeatherBackground.setImageResource(R.color.colorPrimary)
                //holder.recordWeatherBackground.setImageResource(R.drawable.weather_cloud)
            }
            "cloud2"->{
                holder.recordWeather.setImageResource(R.drawable.weather_cloud_2)
                holder.recordWeatherBackground.setImageResource(R.color.colorPrimary)
                //holder.recordWeatherBackground.setImageResource(R.drawable.weather_cloud_2)
            }
            "lighting"->{
                holder.recordWeather.setImageResource(R.drawable.weather_lighting)
                holder.recordWeatherBackground.setImageResource(R.color.colorPrimary)
                //holder.recordWeatherBackground.setImageResource(R.drawable.weather_lighting)
            }
            "rain"->{
                holder.recordWeather.setImageResource(R.drawable.weather_rain)
                holder.recordWeatherBackground.setImageResource(R.color.colorPrimary)
                //holder.recordWeatherBackground.setImageResource(R.drawable.weather_rain)
            }
            "snow"->{
                holder.recordWeather.setImageResource(R.drawable.weather_snow)
                holder.recordWeatherBackground.setImageResource(R.color.colorPrimary)
                //holder.recordWeatherBackground.setImageResource(R.drawable.weather_snow)
            }
        }

        holder.recordTemper.text = recordItems[position].temper.toString()
        holder.recordFeel.text = recordItems[position].feel.toString()
        holder.recordOuter.text = recordItems[position].outer
        holder.recordTop.text = recordItems[position].top
        holder.recordBottom.text = recordItems[position].bottom
        holder.recordMemo.text = recordItems[position].memo

    }
}