package com.mycloset.mycloset.network

import retrofit2.Call
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.pow

class WeatherApi {
    private val networkService  = ApplicationController.instance.networkService
    private val key = "HU4E5Cp/oX7HkuqQaBh8bQSicggj6Y7ZZf5rg/osV8HOI/s0at+/xSvjRhL6U+dTEQ1TAfWuFHdimpQ3v6r3Rw=="
    private lateinit var getWeather : Call<GetResponse>
    // 1-맑음, 2-구름조금, 3-구름많음,흐림(4), 4-비(1), 5-진눈개비(2),눈(3)
    private var weather = ArrayList<Int>()
    // 기온
    private var temper = ArrayList<Int>()
    // 체감온도
    private var feel = ArrayList<Int>()

    // 가져올 처음 시간
    var start:Int = 3
    // 가져올 마지막 시간
    var end:Int = 21

    /* 초기 설정 */
    fun init(nx:Int, ny:Int){
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        // 오늘 날짜 string
        val today = SimpleDateFormat("yyyyMMdd").format(Date()).toString()
        // 어제 날짜 string
        val yesterday = SimpleDateFormat("yyyyMMdd").format(cal.time).toString()
        // 현재 시간 '시
        var hour = SimpleDateFormat("kk").format(Date()).toInt()         // 01-24
        // 현재 시간 '분
        val minute = SimpleDateFormat("mm").format(Date()).toInt()
        // API 결과 body의 item
        var result : List<Item>
        // 현재 시간이 24시면 0으로 표기
        if(hour==24) hour=0

        /* 시간에 따라 통신 시간대 변경 */
        if(hour in 0..1 || (hour==2 && minute>=0 && minute < 10)){
            // 현재 시간이 00시~2시 9분
            // : 어제 날짜 2300에서 0300 ~ 2100
            getWeather = networkService.getWeather(key, yesterday, "2300", nx, ny, 1, 100,"json")
            result = getWeather.execute().body().response.body.items.item
            setWeather(result, 3, 21)
        }else if(hour in 2..4 || (hour==5 && minute>=0 && minute < 10)){
            // 현재 시간이 2시10분 ~ 5시 9분
            // : 어제 날짜 2300에서 0300
            // : 오늘 날짜 0200에서 0600~2100
            getWeather = networkService.getWeather(key, yesterday, "2300", nx, ny, 1, 15,"json")
            setWeather(getWeather.execute().body().response.body.items.item, 3, 4)
            getWeather = networkService.getWeather(key, today, "0200", nx, ny, 1, 100,"json")
            setWeather(getWeather.execute().body().response.body.items.item, 6, 21)
        }else if(hour in 5..22 || (hour==23 && minute>=0 && minute < 10)){
            // 현재 시간이 5시 10분 ~ 23시 9분
            // : 어제 날짜 2300에서 0300~0600
            // : 오늘 날짜 0500에서 0900~2100
            getWeather = networkService.getWeather(key, yesterday, "2300", nx, ny, 1, 30,"json")
            setWeather(getWeather.execute().body().response.body.items.item, 3, 6)
            getWeather = networkService.getWeather(key, today, "0500", nx, ny, 1, 100,"json")
            setWeather(getWeather.execute().body().response.body.items.item, 9, 21)
        }else{
            // 현재 시간이 11시 10분 ~ 23시 59분
            // : 오늘 날짜 0200에서 0300~0600
            // : 오늘 날짜 0500에서 0900~2100
            getWeather = networkService.getWeather(key, today, "0200", nx, ny, 1, 30,"json")
            setWeather(getWeather.execute().body().response.body.items.item, 3, 6)
            getWeather = networkService.getWeather(key, today, "0500", nx, ny, 1, 100,"json")
            setWeather(getWeather.execute().body().response.body.items.item, 9, 21)
        }
    }

    /* 전달받은 item들을 분석하여 weather, temper, feel 세팅 */
    private fun setWeather(items: List<Item>, start: Int, end: Int) {
        // 각 변수별 현재까지 정보 가져온 시간대
        var hourWeather = start
        var hourTemper = start
        var hourFeel = start
        // 날씨 상태를 위한 결과 변수
        var result = -1
        // sky category가 나왔는 지
        var skyFlag = false
        // pty category가 나왔는 지
        var ptyFlag = false
        for(item in items){
            // 날씨 상태 저장
            if(item.fcstDate==SimpleDateFormat("yyyyMMdd").format(Date()).toString() && item.fcstTime.toInt()==hourWeather*100 && item.category=="SKY" || item.category=="PTY" && hourWeather <= end){
                when(item.category){
                    "SKY" -> {
                        if(result==-1){
                            when(item.fcstValue.toInt()){
                                1 -> result = 1
                                2 -> result = 2
                                3,4 -> result = 3
                            }
                        }
                        skyFlag = true
                    }
                    "PTY" -> {
                        when(item.fcstValue.toInt()){
                            1 -> result = 4
                            2,3 -> result = 5
                        }
                        ptyFlag = true
                    }
                }
            // 현재 기온 저장
            }else if(item.fcstDate==SimpleDateFormat("yyyyMMdd").format(Date()).toString() && item.fcstTime.toInt()==hourTemper*100 && item.category=="T3H" && hourTemper <= end){
                temper.add(item.fcstValue.toInt())
                hourTemper += 3
            // 체감 기온 저장
            }else if(item.fcstDate==SimpleDateFormat("yyyyMMdd").format(Date()).toString() && item.fcstTime.toInt()==hourFeel*100 && item.category=="WSD" && hourFeel <= end){
                val temp = temper[item.fcstTime.toInt()/300-(start/3)]
                val wind = item.fcstValue
                feel.add(feelFormula(temp.toFloat(), wind).toInt())
                hourFeel += 3
            }
            // sky, pty 둘 다 나왔으면 관련 변수 초기화
            if(skyFlag && ptyFlag) {
                weather.add(result)
                result = -1
                hourWeather += 3
                skyFlag = false
                ptyFlag = false
            }
            // 모든 변수들의 가져온 시간대가 end를 벗어나면 for문 탈출
            if(hourWeather > end && hourTemper > end && hourFeel > end)
                break
        }
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

    fun getTemper(time:Int):Int{
        // time대의 기온 return
        return temper[time/3-(start/3)]
    }

    fun getFeel(time:Int):Int{
        // time대의 체감 온도 return
        return feel[time/3-(start/3)]
    }

    private fun feelFormula(temp:Float, wind:Float):Float{
        // 체감온도 공식
        (13.12 + 0.6215 * temp - 11.37 * (wind*3.6).pow(0.16) + 0.3965 * (wind*3.6).pow(0.16) * temp).toFloat().let { result ->
            return (Math.round(Math.round(Math.round(result * 1000.0) / 1000.0 * 100.0) / 100.0 * 1.0) / 1.0).toFloat()
        }
    }
}