package com.mycloset.mycloset.network

data class GetResponse(
        val response: Response
)

data class Response(
        val body: Body,
        val header: Header
)

data class Body(
        val items: Items,
        val numOfRows: Int,
        val pageNo: Int,
        val totalCount: Int
)

data class Items(
        val item: List<Item>
)

data class Item(
        val baseDate: String,
        val baseTime: String,
        val category: String,
        val fcstDate: String,
        val fcstTime: String,
        val fcstValue: Float,
        val nx: Int,
        val ny: Int
)

data class Header(
        val resultCode: String,
        val resultMsg: String
)