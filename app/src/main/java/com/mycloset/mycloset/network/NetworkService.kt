package com.mycloset.mycloset.network

import retrofit2.Call
import retrofit2.http.*

interface NetworkService {
    @GET("/service/SecndSrtpdFrcstInfoService2/ForecastSpaceData")
    fun getWeather(@Query("serviceKey")key:String, @Query("base_date")date:String,
                   @Query("base_time")time:String, @Query("nx")nx:Int, @Query("ny")ny:Int,
                   @Query("pageNo")page:Int, @Query("numOfRows")row:Int, @Query("_type")type:String):Call<GetResponse>
}