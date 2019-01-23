package com.mycloset.mycloset.ui.record

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mycloset.mycloset.R

class RecordAdapter(var recordItems: ArrayList<RecordItem>) : RecyclerView.Adapter<RecordViewHolder>() {
    private lateinit var onItemClick : View.OnClickListener
    private lateinit var onLongClick : View.OnLongClickListener

    fun setOnItemClickListener(l : View.OnClickListener, long : View.OnLongClickListener){
        onItemClick = l
        onLongClick = long
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_card, parent, false)
        mainView.setOnClickListener(onItemClick)
        mainView.setOnLongClickListener(onLongClick)
        return RecordViewHolder(mainView)
    }

    override fun getItemCount(): Int = recordItems.size

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.recordDate.text = recordItems[position].date
        when(recordItems[position].weather){
            "sunny"->{
                holder.recordWeather.setImageResource(R.drawable.weather_sun)
                holder.recordWeatherBackground.setImageResource(R.drawable.back_sunny)
            }
            "little cloudy"->{
                holder.recordWeather.setImageResource(R.drawable.weather_cloud)
                holder.recordWeatherBackground.setImageResource(R.drawable.back_cloud)
            }
            "cloudy"->{
                holder.recordWeather.setImageResource(R.drawable.weather_cloud_2)
                holder.recordWeatherBackground.setImageResource(R.drawable.back_cloud_2)
            }
            "lighting"->{
                holder.recordWeather.setImageResource(R.drawable.weather_lighting)
                holder.recordWeatherBackground.setImageResource(R.drawable.back_rainy)
            }
            "rainy"->{
                holder.recordWeather.setImageResource(R.drawable.weather_rain)
                holder.recordWeatherBackground.setImageResource(R.drawable.back_rainy)
            }
            else->{
                holder.recordWeather.setImageResource(R.drawable.weather_snow)
                holder.recordWeatherBackground.setImageResource(R.drawable.back_snowy)
            }
        }

        holder.recordTemper.text = recordItems[position].temper.toString()+"º"
        holder.recordFeel.text = recordItems[position].feel.toString()+"º"
        holder.recordOuter.text = recordItems[position].outer
        holder.recordTop.text = recordItems[position].top
        holder.recordBottom.text = recordItems[position].bottom
        holder.recordMemo.text = recordItems[position].memo

    }
}