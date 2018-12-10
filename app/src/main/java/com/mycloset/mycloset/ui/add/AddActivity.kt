package com.mycloset.mycloset.ui.add

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.mycloset.mycloset.R
import com.mycloset.mycloset.ui.MainActivity
import com.mycloset.mycloset.ui.record.RecordItem
import com.mycloset.mycloset.util.TodayWeather
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_add.*
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var realm: Realm
    lateinit var weather: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        add_check_ib.setOnClickListener(this)
        Realm.init(this)
        realm = Realm.getDefaultInstance()

        var idx = 0
        // 지금 날씨상태, 온도, 체감온도에 맞는 데이터 띄우기
        if(TodayWeather.selected != -1){
            idx = TodayWeather.selected
            add_timePicker_tv.text = ((idx+1)*3).toString() + "시"
        }else{
            add_timePicker_tv.text = TodayWeather.start.toString() + "시"
        }
        weather =TodayWeather.weather[idx]
        add_temper_tv.text = TodayWeather.temp[idx].toString() + "º"
        add_effectiveTemper_tv.text = TodayWeather.feel[idx].toString() + "º"
        when(weather) {
            "sunny" -> {
                add_weather_iv.setImageResource(R.drawable.weather_sun)
            }
            "little cloudy" -> {
                add_weather_iv.setImageResource(R.drawable.weather_cloud)
            }
            "cloudy" -> {
                add_weather_iv.setImageResource(R.drawable.weather_cloud_2)
            }
            "rainy" -> {
                add_weather_iv.setImageResource(R.drawable.weather_rain)
            }
            else -> {
                add_weather_iv.setImageResource(R.drawable.weather_snow)
            }
        }
    }

    // add_check_ib ImageButton을 누른 경우 db에 새로 저장
    override fun onClick(v: View?) {
        when (v) {
            add_check_ib -> {
                // Add a recordItem
                Log.d("asdf",weather)
                insertRecord(realm.where(RecordItem::class.java).findAll().size,
                        SimpleDateFormat("yyyy-MM-dd").format(Date()).toString(),   // 오늘 날짜
                        // (시간, 상태, 기온, 체감온도)를 todayfragment에서 가져와야함
                        add_timePicker_tv.text.toString().substring(0, add_timePicker_tv.text.toString().length - 1).toInt(),  //
                        weather,  //
                        add_temper_tv.text.toString().substring(0, add_temper_tv.text.length - 1).toInt(),  //
                        add_effectiveTemper_tv.text.toString().substring(0, add_effectiveTemper_tv.text.length - 1).toInt(), //
                        add_outer_et.text.toString(),
                        add_top_et.text.toString(),
                        add_bottom_et.text.toString(),
                        add_memo_et.text.toString())

                val goMain = Intent(this, MainActivity::class.java)
                startActivity(goMain)
            }
        }
    }

    private fun insertRecord(idx:Int = 0, date:String = " ", time:Int = 0, weather:String = " ",
                                 temper:Int = 0, feel:Int = 0, outer:String? = null,
                                 top:String? = null, bottom:String? = null, memo:String? = null) {
        var recordItem = RecordItem()

        recordItem.idx = idx
        recordItem.date = date
        recordItem.time = time
        recordItem.weather = weather
        recordItem.temper = temper
        recordItem.feel = feel
        recordItem.outer = outer
        recordItem.top = top
        recordItem.bottom = bottom
        recordItem.memo = memo

        realm.beginTransaction()
        realm.copyToRealm(recordItem)
        realm.commitTransaction()
    }
}