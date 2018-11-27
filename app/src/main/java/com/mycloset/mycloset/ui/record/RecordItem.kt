package com.mycloset.mycloset.ui.record

data class RecordItem(
        var idx: Int = 0,           // 카드 idx
        var date: String = "",      // 오늘 날짜
        var time: Int = 0,          // 기록된 시간,
        var weather: String = "",   // 날씨 상태(아이콘으로 표현)
        var temper: Float = 0.0f,   // 기온
        var feel: Float = 0.0f,     // 체감 온도
        var outer: String? = null,  // 겉옷
        var top: String? = null,    // 상의
        var bottom: String? = null, // 하의
        var memo: String? = null    // 메모
)