package com.mycloset.mycloset.ui.record

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RecordItem(idx:Int = 0, date:String = " ", time:Int = 0, weather:String = " ",
                      temper:Float = 0.0f, feel:Float = 0.0f, outer:String? = null,
                      top:String? = null, bottom:String? = null, memo:String? = null) : RealmObject() {
    //var realmList : RealmList<String> = RealmList()
    @PrimaryKey var idx: Int = idx           // 카드 idx
    var date: String = date      // 오늘 날짜
    var time: Int = time          // 기록된 시간,
    var weather: String = weather   // 날씨 상태(아이콘으로 표현)
    var temper: Float = temper   // 기온
    var feel: Float = feel     // 체감 온도
    var outer: String? = outer  // 겉옷
    var top: String? = top    // 상의
    var bottom: String? = bottom // 하의
    var memo: String? = memo   // 메모
}