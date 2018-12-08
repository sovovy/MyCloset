package com.mycloset.mycloset.ui.add

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.View
import android.widget.TextView
import com.mycloset.mycloset.R
import com.mycloset.mycloset.ui.MainActivity
import com.mycloset.mycloset.ui.record.RecordItem
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_add.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AddActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var realm: Realm

    @RequiresApi(Build.VERSION_CODES.O)
    val now = LocalDateTime.now()
    @RequiresApi(Build.VERSION_CODES.O)
    var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    @RequiresApi(Build.VERSION_CODES.O)
    val formatted = now.format(formatter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        add_check_ib.setOnClickListener(this)
        Realm.init(this)
    }


    // add_check_ib ImageButton을 누른 경우 db에 새로 저장
    override fun onClick(v: View?) {
        when(v) {

            add_check_ib -> Realm.getDefaultInstance().use { realm ->
                realm.executeTransaction { realm ->
                    // Add a recordItem
                    val recordItem = RecordItem(realm.where(RecordItem::class.java).findAll().size,
                            formatted,   // 오늘 날짜
                            add_timePicker_tv.text.toString().toInt(),
                            "sunny",
                            add_temper_tv.text.toString().substring(0,add_temper_tv.text.length-1).toInt(),
                            add_effectiveTemper_tv.text.toString().substring(0,add_effectiveTemper_tv.text.length-1).toInt(),
                            add_outer_et.text.toString(),
                            add_top_et.text.toString(),
                            add_bottom_et.text.toString(),
                            add_memo_et.text.toString())

//                    realm.beginTransaction()
                    realm.copyToRealm(recordItem)   // realm에 recordItem 복사
//                    realm.commitTransaction()
                }

                val goMain = Intent(this, MainActivity::class.java)
                startActivity(goMain)

                /*
                realm.executeTransaction {

                    var record1 = RecordItem()

                    record1.date = "2018-12-02"    // -> 추가
                    record1.time = 3               // add_timePicker_tv
                    record1.weather = "sunny"      // add_weather_iv -> 추가
                    record1.temper = 3.5f          // add_temper_tv
                    record1.feel = 1.3f            // add_effectiveTemper_tv
                    record1.outer = "흰색패딩"       // add_outer_et
                    record1.top = "맨투맨"          // add_top_et
                    record1.bottom = "츄리닝바지"    // add_bottom_et
                    record1.memo = "넘나 추움"       // add_memo_et

                    //recordItems.add(record1)

                    realm.copyToRealm(record1)
                }
                */
            }
        }
    }

    // 시간대별로 일치하는 기록들 검색
    private fun basicQuery(realm: Realm) {
        Log.d("Number of record: ${realm.where<RecordItem>().count()}","abc")

        val time = 99
        val results = realm.where<RecordItem>().equalTo("time", time).findAll()

        Log.d("Size of result set: ", results.size.toString())
    }

}