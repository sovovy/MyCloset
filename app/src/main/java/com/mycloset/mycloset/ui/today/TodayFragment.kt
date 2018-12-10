package com.mycloset.mycloset.ui.today

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mycloset.mycloset.R
import com.mycloset.mycloset.network.WeatherApi
import com.mycloset.mycloset.ui.add.AddActivity
import com.mycloset.mycloset.ui.setting.SelectActivity
import com.mycloset.mycloset.ui.today.recyclerview.ColumnAdapter
import com.mycloset.mycloset.ui.today.recyclerview.ColumnItem
import com.mycloset.mycloset.util.SharedPreferenceController
import kotlinx.android.synthetic.main.fragment_today.*
import kotlinx.android.synthetic.main.fragment_today.view.*
import android.os.StrictMode
import android.support.v7.widget.GridLayoutManager
import android.text.Html
import android.util.Log
import com.mycloset.mycloset.ui.edit.EditActivity
import com.mycloset.mycloset.ui.record.RecordAdapter
import com.mycloset.mycloset.ui.record.RecordItem
import com.mycloset.mycloset.util.TodayWeather
import io.realm.Realm
import io.realm.RealmResults

class TodayFragment : Fragment(), View.OnClickListener{
    lateinit var recordRealm: Realm
    lateinit var columnItems : ArrayList<ColumnItem>
    lateinit var columnAdapter: ColumnAdapter
    lateinit var recordItems : ArrayList<RecordItem>
    lateinit var recordAdapter: RecordAdapter
    val api = WeatherApi()
    // 선택한 column의 체감 온도
    var selectedFeel = 0

    override fun onClick(v: View?) {
        when(v){
            today_add_ib -> {
                TodayWeather.start = api.start
                TodayWeather.end = api.end
                TodayWeather.weather.removeAll(TodayWeather.weather)
                TodayWeather.temp.removeAll(TodayWeather.temp)
                TodayWeather.feel.removeAll(TodayWeather.feel)
                for(time in api.start .. api.end step 3){
                    TodayWeather.weather.add(api.getWeather(time))
                    TodayWeather.temp.add(api.getTemper(time))
                    TodayWeather.feel.add(api.getFeel(time))
                }
                startActivity(Intent(activity, AddActivity::class.java))
            }

            today_title_tv -> startActivity(Intent(activity, SelectActivity::class.java))
            else -> {
                // column item이면
                if(today_column_rv.indexOfChild(v)!=-1) {
                    // column 선택시 색상 변경
                    val idx: Int = today_column_rv.getChildAdapterPosition(v)
                    TodayWeather.selected = idx
                    for (i in 0 until columnItems.size) {
                        columnItems[i].selected = false
                    }
                    columnItems[idx].selected = true
                    selectedFeel = columnItems[idx].sensible
                    columnAdapter.notifyDataSetChanged()

                    // 체감 온도에 맞게 record item 가져오기
                    // realm 초기화
                    Realm.init(context)
                    recordRealm = Realm.getDefaultInstance()

                    var tempErr = SharedPreferenceController.sharedPreferenceController.getTempErr(v!!.context)
                    val resultsRecord : RealmResults<RecordItem> = recordRealm.where(RecordItem::class.java).greaterThanOrEqualTo("feel",selectedFeel-tempErr).lessThanOrEqualTo("feel",selectedFeel+tempErr).findAll()
//                    between("feel", selectedFeel-tempErr, selectedFeel+tempErr).findAll()

                    // db에 저장된 값이 있으면 record_rv를 띄우고 없으면 record_info_iv를 띄움
                    if(resultsRecord.size>0) {
                        today_card_rv.visibility = View.VISIBLE
                        today_info_iv.visibility = View.GONE
                        today_info_tv.visibility = View.GONE
                    } else {
                        today_card_rv.visibility = View.GONE
                        today_info_iv.visibility = View.VISIBLE
                        today_info_tv.visibility = View.VISIBLE
                    }

                    recordItems.removeAll(recordItems)
                    // db에 저장된 모든 record들을 recordItem arrayList에 저장
                    for(record in resultsRecord) {
                        recordItems.add(RecordItem(record.idx, record.date, record.time, record.weather,
                                record.temper, record.feel, record.outer!!, record.top!!, record.bottom!!, record.memo!!))
                    }
                    recordAdapter.notifyDataSetChanged()
                }else if(today_card_rv.indexOfChild(v)!=-1){    // card item 이면
                    val idx: Int = today_card_rv.indexOfChild(v)
                    val nextIntent = Intent(context, EditActivity::class.java)
                    nextIntent.putExtra("realm idx", idx)
                    startActivity(nextIntent)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_today,container,false)
        // set title by location
        v.today_title_tv.text = SharedPreferenceController.sharedPreferenceController.getGu(context!!)+" ^~^"
        // set info text by tempError
        v.today_info_tv.text = Html.fromHtml("시간을 선택하면 체감 온도에 입었던<br/><br/>과거의 착장이 나옵니다 (온도 오차 ±" + SharedPreferenceController.sharedPreferenceController.getTempErr(context!!) + ")")
        // click listener attach
        v.today_add_ib.setOnClickListener(this)
        v.today_title_tv.setOnClickListener(this)

        // recyclerView
        columnItems = ArrayList()
        recordItems = ArrayList()

        // 안드로이드 main thread에서 네트워크(동기적) 통신을 할 수 있도록
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())

        // api data get init
        api.init(SharedPreferenceController.sharedPreferenceController.getX(context as Context), SharedPreferenceController.sharedPreferenceController.getY(context as Context))

        // api 클래스에 정리된 데이터를 가져오기
        for(time in api.start .. api.end step 3){
            columnItems.add(ColumnItem(time, api.getWeather(time), api.getTemper(time), api.getFeel(time), false))
        }
        // column RecyclerView 세팅
        columnAdapter = ColumnAdapter(columnItems)
        columnAdapter.setOnItemClickListener(this)
        val llm = LinearLayoutManager(context)
        llm.orientation = RecyclerView.HORIZONTAL
        v.today_column_rv.layoutManager = llm
        v.today_column_rv.adapter = columnAdapter

        // record RecyclerView 세팅
        recordAdapter = RecordAdapter(recordItems)
        recordAdapter.setOnItemClickListener(this)
        val glm = GridLayoutManager(context, 2)
        v.today_card_rv.layoutManager = glm
        v.today_card_rv.adapter = recordAdapter

        return v
    }
}