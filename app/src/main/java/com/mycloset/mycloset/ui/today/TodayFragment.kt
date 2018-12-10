package com.mycloset.mycloset.ui.today

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
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
import com.mycloset.mycloset.ui.record.RecordAdapter
import com.mycloset.mycloset.ui.record.RecordItem


class TodayFragment : Fragment(), View.OnClickListener{
    lateinit var columnItems : ArrayList<ColumnItem>
    lateinit var columnAdapter: ColumnAdapter
    lateinit var recordItems : ArrayList<RecordItem>
    lateinit var recordAdapter: RecordAdapter
    val api = WeatherApi()
    // 선택한 column의 체감 온도
    var selectedFeel = 0

    override fun onClick(v: View?) {
        when(v){
            today_add_ib -> startActivity(Intent(activity, AddActivity::class.java))
            today_title_tv -> startActivity(Intent(activity, SelectActivity::class.java))
            else -> {
                // column item이면
                if(today_column_rv.getChildPosition(v)!=-1) {
                    // column 선택시 색상 변경
                    val idx: Int = today_column_rv.getChildAdapterPosition(v)
                    for (i in 0 until columnItems.size) {
                        columnItems[i].selected = false
                    }
                    columnItems[idx].selected = true
                    selectedFeel = columnItems[idx].sensible
                    columnAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_today,container,false)
        // set title by location
        v.today_title_tv.text = SharedPreferenceController.sharedPreferenceController.getGu(context!!)+" ^~^"

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

        // 체감 온도에 맞게 record item 가져오기

        // record RecyclerView 세팅
        recordAdapter = RecordAdapter(recordItems)
        recordAdapter.setOnItemClickListener(this)
        val glm = GridLayoutManager(context, 2)
        v.today_card_rv.layoutManager = glm
        v.today_card_rv.adapter = recordAdapter

        return v
    }
}