package com.mycloset.mycloset.ui.today.recyclerview

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mycloset.mycloset.R

class ColumnViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    var columnTime : TextView = itemView!!.findViewById(R.id.item_time_tv) as TextView
    var columnWeather : ImageView = itemView!!.findViewById(R.id.item_weather_iv) as ImageView
    var columnTemper : TextView = itemView!!.findViewById(R.id.item_temper_tv) as TextView
    var columnSensible : TextView = itemView!!.findViewById(R.id.item_sensible_tv) as TextView
    var columnLayout : ConstraintLayout = itemView!!.findViewById(R.id.item_column_constraint) as ConstraintLayout
}