package com.mycloset.mycloset.ui.edit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mycloset.mycloset.R
import com.mycloset.mycloset.ui.record.RecordItem
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.fragment_record.view.*

class EditActivity : AppCompatActivity() {

    lateinit var recordRealm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        // intent로 넘어온 idx 받기(실제 realm idx는 아님;;)
        val realmIdx:Int = intent.getIntExtra("realm idx",0)
//        val recordItemArray: ArrayList<RecordItem> = intent.getParcelableArrayListExtra<RecordItem>("recordItems")


        // edit.xml에 cardView[idx]에 해당하는 값 띄우기
        Realm.init(this)
        recordRealm = Realm.getDefaultInstance()
        val resultsRecord : RealmResults<RecordItem> = recordRealm.where(RecordItem::class.java).findAll()

        for(record in resultsRecord) {
            if(record.idx == realmIdx) {
                edit_time_tv.text = record.date

                when(record.weather){
                    "sunny"->{
                        edit_weather_iv.setImageResource(R.drawable.weather_sun)
                    }
                    "cloud"->{
                        edit_weather_iv.setImageResource(R.drawable.weather_cloud)
                    }
                    "cloud2"->{
                        edit_weather_iv.setImageResource(R.drawable.weather_cloud_2)
                    }
                    "lighting"->{
                        edit_weather_iv.setImageResource(R.drawable.weather_lighting)
                    }
                    "rain"->{
                        edit_weather_iv.setImageResource(R.drawable.weather_rain)
                    }
                    "snow"->{
                        edit_weather_iv.setImageResource(R.drawable.weather_snow)
                    }
                }
                edit_temper_tv.text = record.temper.toString()
                edit_effectiveTemper_tv.text = record.temper.toString()
                edit_outer_et.setText(record.outer)
                edit_top_et.setText(record.top)
                edit_bottom_et.setText(record.bottom)
                edit_memo_et.setText(record.memo)
            }
        }
    }

    // edit_check_ib button을 누른 경우 db에 수정한 값이 저장됨
    fun onClick(v: View?) {
        when(v) {
            edit_check_ib -> Realm.getDefaultInstance().use { realm ->

                // 현재 activity에 있는 값으로 update

                realm.executeTransaction { _ ->
                    /*
                    person.name = "Senior Person"
                    person.age = 99
                    showStatus(person.name + " got older: " + person.age)
                    */
                }

            }

        }
    }
}
