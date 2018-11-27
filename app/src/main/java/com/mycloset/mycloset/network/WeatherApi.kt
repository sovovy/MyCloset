package com.mycloset.mycloset.network

import android.util.Log
import retrofit2.Call
import java.text.SimpleDateFormat
import java.util.*

class WeatherApi {
    private val networkService  = ApplicationController.instance.networkService
    private val key = "HU4E5Cp/oX7HkuqQaBh8bQSicggj6Y7ZZf5rg/osV8HOI/s0at+/xSvjRhL6U+dTEQ1TAfWuFHdimpQ3v6r3Rw=="
    private lateinit var getWeather : Call<GetResponse>
    // 1-맑음, 2-구름조금, 3-구름많음,흐림(4), 4-비(1), 5-진눈개비(2),눈(3)
    var weather = ArrayList<Int>()
    var temper = ArrayList<Float>()
    var feel = ArrayList<Float>()
    // 현재 시간에 따라 가져올 처음 시간
    var start:Int = 0
    // 현재 시간에 따라 가져올 마지막 시간
    var end:Int = 0

    fun init(nx:Int, ny:Int){
        // 체감온도 공식
        // 13.12 + {0.6215 * (기온)} - {11.37 * (풍속)^0.16} + {0.3965 * (풍속)^0.16 * (기온)}
//        when(SimpleDateFormat("HH").toString()){
//            "00", "01"->{
//                getWeather = networkService.getWeather(key, SimpleDateFormat("yyyyMMdd").toString(), "0500", nx, ny, 1, 300,"json")
//            }
//            else->{
//
//            }
//        }

        start=9
        end=21
        getWeather = networkService.getWeather(key, "20181127", "0500", nx, ny, 1, 300,"json")

        setWeather(getWeather.execute().body().response.body.items.item, start, end)

//        getWeather.enqueue(object : Callback<GetResponse> {
//            override fun onFailure(call: Call<GetResponse>?, t: Throwable?) {
//                Log.d("asdf","8888")
//            }
//            override fun onResponse(call: Call<GetResponse>?, response: Response<GetResponse>?) {
//                if(response!!.isSuccessful){
                    // weather 저장
//                    Log.d("asdf","1234")
//                    weather.add(1)
//                    weather.add(1)
//                    setWeather(response!!.body().response.body.items.item, 9, 21)
//                       {"baseDate":20181126,
//                        "baseTime":"0500",
//                        "category":"T3H",
//                        "fcstDate":20181126,
//                        "fcstTime":"0900",
//                        "fcstValue":7,
//                        "nx":92,
//                        "ny":131}

                    // temper 저장
                    // feel 저장
//                    var hour = 3
//                    var hourString = ""
//                    for(item in response!!.body().response.body.items.item){
//                        if(hour<10)
//                            hourString = "0" + hour + "00"
//                        else
//                            hourString = hour.toString() + "00"
//
//                        if(item.fcstDate==SimpleDateFormat("yyyyMMdd").toString() && item.fcstTime==hourString){
//                            if(item.category=="T3H")
//                                temper.add(item.fcstValue)
//                            else if(item.category=="PTY"){
//                                when(item.fcstValue.toInt()){
//                                    0 ->{
//
//                                    }
//                                    1 -> weather.add(4)
//                                    2,3 -> weather.add(5)
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//        })
    }
    fun getWeather(time:Int):String{
        // time대의 날씨 상태 return
        return when(weather[time/3-(start/3)]){
            1 -> "sunny"
            2 -> "little cloudy"
            3 -> "cloudy"
            4 -> "rainy"
            else -> "snowy"
        }
    }
    fun setWeather(items: List<Item>, start: Int, end: Int) {
        var hour = start
        var result = -1
        var skyFlag = false
        var ptyFlag = false
        for(item in items){
//            if(item.fcstDate==SimpleDateFormat("yyyyMMdd").format(Date()).toString() && item.fcstTime.toInt()==hour*100 && (item.category=="SKY" || item.category=="PTY")){
            if(item.fcstDate=="20181127" && item.fcstTime.toInt()==hour*100 && item.category=="SKY" || item.category=="PTY"){
                Log.d("asdf","tlqkf"+item.fcstDate+" "+item.fcstTime)
                when(item.category){
                    "SKY" -> {
                        if(result==-1){
                            when(item.fcstValue.toInt()){
                                1 -> result = 1
                                2 -> result = 2
                                3,4 -> result = 3
                            }
                            skyFlag = true
                        }
                    }
                    "PTY" -> {
                        when(item.fcstValue.toInt()){
                            1 -> result = 4
                            2,3 -> result = 5
                        }
                        ptyFlag = true
                    }
                }
            }
            if(skyFlag && ptyFlag) {
                weather.add(result)
                result = -1
                hour += 3
                skyFlag = false
                ptyFlag = false
            }
            if(hour > end)
                break
        }
    }

    fun getTemper(time:Int):Float{
        // time대의 기온 return
//        return temper[time/3-1]
        return 19.4f
    }
    fun getFeel(time:Int):Float{
        // time대의 체감 온도 return
//        return feel[time/3-1]
        return 22.2f
    }
}