package com.mycloset.mycloset.ui.record

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RecordItem(idx:Int = 0, date:String = " ", time:Int = 0, weather:String = " ",
                      temper:Int = 0, feel:Int = 0, outer:String? = null,
                      top:String? = null, bottom:String? = null, memo:String? = null) : RealmObject() {

    @PrimaryKey var idx: Int = idx      // 카드 idx
    var date: String = date             // 오늘 날짜
    var time: Int = time                // 기록된 시간,
    var weather: String = weather       // 날씨 상태(아이콘으로 표현)
    var temper: Int = temper            // 기온
    var feel: Int = feel                // 체감 온도
    var outer: String? = outer          // 겉옷
    var top: String? = top              // 상의
    var bottom: String? = bottom        // 하의
    var memo: String? = memo            // 메모

//    constructor(parcel: Parcel) : this(
//            TODO("idx"),
//            TODO("date"),
//            TODO("time"),
//            TODO("weather"),
//            TODO("temper"),
//            TODO("feel"),
//            TODO("outer"),
//            TODO("top"),
//            TODO("bottom"),
//            TODO("memo")) {
//        idx = parcel.readInt()
//        date = parcel.readString()
//        time = parcel.readInt()
//        weather = parcel.readString()
//        temper = parcel.readInt()
//        feel = parcel.readInt()
//        outer = parcel.readString()
//        top = parcel.readString()
//        bottom = parcel.readString()
//        memo = parcel.readString()
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeInt(idx)
//        parcel.writeString(date)
//        parcel.writeInt(time)
//        parcel.writeString(weather)
//        parcel.writeInt(temper)
//        parcel.writeInt(feel)
//        parcel.writeString(outer)
//        parcel.writeString(top)
//        parcel.writeString(bottom)
//        parcel.writeString(memo)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<RecordItem> {
//        override fun createFromParcel(parcel: Parcel): RecordItem {
//            return RecordItem(parcel)
//        }
//
//        override fun newArray(size: Int): Array<RecordItem?> {
//            return arrayOfNulls(size)
//        }
//    }
}