package com.mycloset.mycloset.ui.record

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import com.mycloset.mycloset.R
import com.mycloset.mycloset.ui.edit.EditActivity
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_record.*
import kotlinx.android.synthetic.main.fragment_record.view.*

class RecordFragment : Fragment(), View.OnClickListener {
    lateinit var recordRealm: Realm
    lateinit var recordAdapter: RecordAdapter
    lateinit var recordItems: ArrayList<RecordItem>

    override fun onClick(v: View?) {
        val idx: Int = record_rv.getChildAdapterPosition(v)
        val nextIntent = Intent(context, EditActivity::class.java)
        Log.d("cardView idx", idx.toString())
        nextIntent.putExtra("realmdb idx", idx)
//                nextIntent.putExtra("cardView",recordItems[v.record_rv.indexOfChild(view)])
        startActivity(nextIntent)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_record,container,false)

        // realm 초기화
        Realm.init(context)
        recordRealm = Realm.getDefaultInstance()

        recordItems = ArrayList()

        var resultsRecord : RealmResults<RecordItem> = recordRealm.where(RecordItem::class.java).findAll()
        Log.d("resultsRecord size: ",resultsRecord.size.toString())

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
            recordItems.add(RecordItem(record.idx, record.date, record.time, record.weather,
                    record.temper, record.feel, record.outer!!, record.top!!, record.bottom!!, record.memo!!))
        }


        recordAdapter = RecordAdapter(recordItems)
        val glm = GridLayoutManager(context,2)
        glm.orientation = RecyclerView.VERTICAL
        v.record_rv.layoutManager = glm
        v.record_rv.adapter = recordAdapter


        // card를 선택하면 editActivity로 이동
        v.record_rv.addOnItemTouchListener(RecyclerTouchListener(context!!, v.record_rv, object : ClickListener {

            override fun onClick(view: View, position: Int) {

            }

        }))

        return v
    }

    interface ClickListener {
        fun onClick(view: View, position: Int)

//        fun onLongClick(view: View?, position: Int)
    }

    internal class RecyclerTouchListener(context: Context, recyclerView: RecyclerView, private val clickListener: ClickListener?)
        : RecyclerView.OnItemTouchListener {

        private val gestureDetector: GestureDetector

        init {
            gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    return true
                }

//                override fun onLongPress(e: MotionEvent) {
//                    val child = recyclerView.findChildViewUnder(e.x, e.y)
//                    if (child != null && clickListener != null) {
//                        clickListener.onLongClick(child, recyclerView.getChildPosition(child))
//                    }
//                }
            })
        }

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {

            val child = rv.findChildViewUnder(e.x, e.y)
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child))
            }
            return false
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

        }
    }
}