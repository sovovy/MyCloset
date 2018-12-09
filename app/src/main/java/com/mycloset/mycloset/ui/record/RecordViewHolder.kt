package com.mycloset.mycloset.ui.record

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mycloset.mycloset.R
import com.mycloset.mycloset.ui.edit.EditActivity

class RecordViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    var recordDate : TextView = itemView!!.findViewById(R.id.item_tv_date) as TextView              // 오늘 날짜

    var recordWeather : ImageView = itemView!!.findViewById(R.id.item_iv_weather) as ImageView      // 오늘 날씨 아이콘
    var recordWeatherBackground : ImageView = itemView!!.findViewById(R.id.item_iv_background) as ImageView  // 오늘 날씨 배경

    var recordTemper : TextView = itemView!!.findViewById(R.id.item_tv_temper) as TextView          // 기온

    var recordFeel : TextView = itemView!!.findViewById(R.id.item_tv_feel) as TextView              // 체감 온도

    var recordOuter : TextView = itemView!!.findViewById(R.id.item_tv_outer) as TextView            // 겉옷

    var recordTop : TextView = itemView!!.findViewById(R.id.item_tv_top) as TextView                // 상의

    var recordBottom : TextView = itemView!!.findViewById(R.id.item_tv_bottom) as TextView          // 하의

    var recordMemo : TextView = itemView!!.findViewById(R.id.item_tv_memo) as TextView              // 메모

}