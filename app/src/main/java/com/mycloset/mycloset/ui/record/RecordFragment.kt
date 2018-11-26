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
import kotlinx.android.synthetic.main.fragment_record.*
import kotlinx.android.synthetic.main.fragment_record.view.*

class RecordFragment : Fragment() {

    lateinit var recordRealm: Realm
    lateinit var recordAdapter: RecordAdapter
    lateinit var recordItems: ArrayList<RecordItem>
    


    fun init() {
        Realm.init(this.context)    // ?
        recordRealm = Realm.getDefaultInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_record,container,false)
        //Log.d("asd",recordItems.size.toString())

        Realm.init(this.context)   // this.context 아님

        recordItems = ArrayList()
        recordItems.add(RecordItem(0,"2018-12-22",3,"sunny",19.4f,21.4f,"g","gg","g","ggg"))
        recordItems.add(RecordItem(0,"2018-12-22",3,"cloud",19.4f,21.4f,"g","gg","g","ggg"))
        recordItems.add(RecordItem(0,"2018-12-22",3,"cloud2",19.4f,21.4f,"g","gg","g","ggg"))
        recordItems.add(RecordItem(0,"2018-12-22",3,"lighting",19.4f,21.4f,"g","gg","g","ggg"))
        recordItems.add(RecordItem(0,"2018-12-22",3,"rain",19.4f,21.4f,"g","gg","g","ggg"))
        recordItems.add(RecordItem(0,"2018-12-22",3,"snow",19.4f,21.4f,"g","gg","g","ggg"))
        recordItems.add(RecordItem(0,"2018-12-22",3,"sunny",19.4f,21.4f,"g","gg","g","ggg"))
        recordItems.add(RecordItem(0,"2018-12-22",3,"sunny",19.4f,21.4f,"g","gg","g","ggg"))
        recordItems.add(RecordItem(0,"2018-12-22",3,"sunny",19.4f,21.4f,"g","gg","g","ggg"))
        recordItems.add(RecordItem(0,"2018-12-22",3,"sunny",19.4f,21.4f,"g","gg","g","ggg"))
        recordItems.add(RecordItem(0,"2018-12-22",3,"sunny",19.4f,21.4f,"g","gg","g","ggg"))
        recordItems.add(RecordItem(0,"2018-12-22",3,"sunny",19.4f,21.4f,"g","gg","g","ggg"))




        recordAdapter = RecordAdapter(recordItems)
        val glm = GridLayoutManager(context,2, GridLayoutManager.HORIZONTAL, false)
        glm.orientation = RecyclerView.VERTICAL
        v.record_rv.layoutManager = glm
        v.record_rv.adapter = recordAdapter


        return v
    }
}