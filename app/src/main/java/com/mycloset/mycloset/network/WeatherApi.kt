package com.mycloset.mycloset.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class WeatherApi {
    private val networkService  = ApplicationController.instance.networkService
    private lateinit var getWeather : Call<GetResponse>
    private lateinit var weather : List<String>
    private lateinit var temper : List<Float>
    private lateinit var feel : List<Float>

    fun init(nx:Int, ny:Int){
        // api 이용하여 데이터 셋팅까지
        // 필요한 것만 뽑아서 저장
        // 하루 전날 2300 발표자에서 오늘자 0300 데이터 뽑기
        // 체감온도 공식
        // 13.12 + {0.6215 * (기온)} - {11.37 * (풍속)^0.16} + {0.3965 * (풍속)^0.16 * (기온)}
        getWeather = networkService.getWeather("HU4E5Cp/oX7HkuqQaBh8bQSicggj6Y7ZZf5rg/osV8HOI/s0at+/xSvjRhL6U+dTEQ1TAfWuFHdimpQ3v6r3Rw=="
                , SimpleDateFormat("yyyyMMdd").toString(), "0500", nx, ny, 1, 300,"json")
        getWeather.enqueue(object : Callback<GetResponse> {
            override fun onFailure(call: Call<GetResponse>?, t: Throwable?) {
            }
            override fun onResponse(call: Call<GetResponse>?, response: Response<GetResponse>?) {
                if(response!!.isSuccessful){
//                    response!!.body().response.body.items.item[0].toString()
                }
            }

        })
    }
    fun getWeather(time:Int):String{
        // time대의 날씨 상태 return
//        return weather[time/3-1]
        return "sunny"
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