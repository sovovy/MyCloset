package com.mycloset.mycloset.ui.today

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
import com.mycloset.mycloset.ui.today.recyclerview.ColumnAdapter
import com.mycloset.mycloset.ui.today.recyclerview.ColumnItem
import com.mycloset.mycloset.util.SharedPreferenceController
import kotlinx.android.synthetic.main.fragment_today.*
import kotlinx.android.synthetic.main.fragment_today.view.*

class TodayFragment : Fragment(), View.OnClickListener{
    lateinit var columnItems : ArrayList<ColumnItem>
    lateinit var columnAdapter: ColumnAdapter
    val api = WeatherApi()

    override fun onClick(v: View?) {
        when(v){
            today_add_ib ->{
                startActivity(Intent(activity, AddActivity::class.java))
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_today,container,false)
        // click listener attach
        v.today_add_ib.setOnClickListener(this)

        // recyclerView
        columnItems = ArrayList()

        // api data get init
        api.init(92, 131)

        // 3:00~24:00 data attach
        for(time in 3 .. 24 step 3){
            columnItems.add(ColumnItem(time, api.getWeather(time), api.getTemper(time), api.getFeel(time)))
        }

        columnAdapter = ColumnAdapter(columnItems)
        val llm = LinearLayoutManager(context)
        llm.orientation = RecyclerView.HORIZONTAL
        v.today_column_rv.layoutManager = llm
        v.today_column_rv.adapter = columnAdapter

        return v
    }
}