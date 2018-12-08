package com.mycloset.mycloset.ui.record

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mycloset.mycloset.R
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_record.*
import kotlinx.android.synthetic.main.fragment_record.view.*

class RecordFragment : Fragment() {

    lateinit var recordRealm: Realm
    lateinit var recordAdapter: RecordAdapter
    lateinit var recordItems: ArrayList<RecordItem>

//    fun init() {
//        Realm.init(this.context)    // ?
//        recordRealm = Realm.getDefaultInstance()
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_record,container,false)

        // realm 초기화
        Realm.init(context)
        recordRealm = Realm.getDefaultInstance()

        recordItems = ArrayList()

        var resultsRecord : RealmResults<RecordItem> = recordRealm.where(RecordItem::class.java).findAll()
        Log.d("resoultsRecord size: ",resultsRecord.size.toString())

        // db에 저장된 값이 있으면 record_rv를 띄우고 없으면 record_info_iv를 띄움
        if(resultsRecord.size>0) {
            v.record_rv.visibility = View.VISIBLE
            v.record_info_iv.visibility = View.GONE
            v.record_info_tv.visibility = View.GONE
        } else {
            v.record_rv.visibility = View.GONE
            v.record_info_iv.visibility = View.VISIBLE
            v.record_info_tv.visibility = View.VISIBLE
        }

        // db에 저장된 모든 record들을 recordItem arrayList에 저장
        for(record in resultsRecord) {
            recordItems.add(RecordItem(record.idx, record.date, record.time, record.weather, record.temper, record.feel, record.outer!!, record.top!!, record.bottom!!, record.memo!!))
        }


        recordAdapter = RecordAdapter(recordItems)
        val glm = GridLayoutManager(context,2)
        glm.orientation = RecyclerView.VERTICAL
        v.record_rv.layoutManager = glm
        v.record_rv.adapter = recordAdapter

        return v
    }
}