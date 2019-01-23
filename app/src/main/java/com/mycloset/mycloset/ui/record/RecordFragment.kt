package com.mycloset.mycloset.ui.record

import android.app.AlertDialog
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
        val idx: Int = recordItems[record_rv.getChildAdapterPosition(v)].idx
        val nextIntent = Intent(context, EditActivity::class.java)
        Log.d("cardView idx", idx.toString())
        nextIntent.putExtra("realm idx", idx)
        startActivity(nextIntent)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_record,container,false)

        // realm 초기화
        Realm.init(context)
        recordRealm = Realm.getDefaultInstance()
        // swipe refresh 설정
        v.record_sl.setColorSchemeColors(resources.getColor(R.color.colorPrimaryDark))
        v.record_sl.setOnRefreshListener {
            refreshData(v)
            v.record_sl.isRefreshing = false
        }
        recordItems = ArrayList()

        val resultsRecord : RealmResults<RecordItem> = recordRealm.where(RecordItem::class.java).findAll()
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
        for(idx in resultsRecord.size-1 downTo 0) {
            recordItems.add(RecordItem(resultsRecord[idx]!!.idx, resultsRecord[idx]!!.date, resultsRecord[idx]!!.time, resultsRecord[idx]!!.weather,
                    resultsRecord[idx]!!.temper, resultsRecord[idx]!!.feel, resultsRecord[idx]!!.outer, resultsRecord[idx]!!.top, resultsRecord[idx]!!.bottom, resultsRecord[idx]!!.memo))
        }


        recordAdapter = RecordAdapter(recordItems)
        // item click listener 설정. 수정, 삭제.
        recordAdapter.setOnItemClickListener(this , View.OnLongClickListener {
            var result = recordRealm.where(RecordItem::class.java)
                    .equalTo("idx", recordItems[record_rv.getChildAdapterPosition(it)].idx)
                    .findAll()
            val builder = AlertDialog.Builder(context)
            builder.setMessage("기록을 지우시겠습니까?")
                    .setPositiveButton("넹", { _, _ ->
                        if(result.isNotEmpty()){
                            recordRealm.beginTransaction()
                            result.deleteAllFromRealm()
                            recordRealm.commitTransaction()
                            refreshData(v)
                        }
                    })
                    .setNegativeButton("아니용", {_, _ ->

                    })
            val alert = builder.create()
            alert.show()

            true
        })
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
    // data refresh
    private fun refreshData(v: View){
        recordItems = ArrayList()
        val resultsRecord : RealmResults<RecordItem> = recordRealm.where(RecordItem::class.java).findAll()
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
        for(idx in resultsRecord.size-1 downTo 0) {
            recordItems.add(RecordItem(resultsRecord[idx]!!.idx, resultsRecord[idx]!!.date, resultsRecord[idx]!!.time, resultsRecord[idx]!!.weather,
                    resultsRecord[idx]!!.temper, resultsRecord[idx]!!.feel, resultsRecord[idx]!!.outer, resultsRecord[idx]!!.top, resultsRecord[idx]!!.bottom, resultsRecord[idx]!!.memo))
        }

        recordAdapter.notifyDataSetChanged()
    }
}