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
    lateinit var recordItem : RecordItem
    lateinit var recordRealm: Realm
    lateinit var recordAdapter: RecordAdapter
    lateinit var recordItems: ArrayList<RecordItem>

    override fun onClick(v: View?) {
        val idx: Int = recordItems.size-1 - record_rv.indexOfChild(v)
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

        // test) 초기화시키고 DB에 임시값
        recordRealm.beginTransaction()
        recordRealm.where(RecordItem::class.java!!).findAll().deleteAllFromRealm()
        recordRealm.commitTransaction()

        insertPeopleList(0,"2018-04-19",18,"little cloudy",14,13,"초록색 뽀글이","다홍 맨투맨","아디다스 레깅스","조금 덥")
        insertPeopleList(1,"2018-11-25",9,"sunny",5,2,"베이지 패딩","검성 줄무늬티","청바지","다리 춥")
        insertPeopleList(2,"2018-11-26",13,"little cloudy",11,7,"옥색 패딩","갈색 줄무늬티","스키니진","상체 추움")
        insertPeopleList(3,"2018-11-27",13,"rainy",9,7,"아빠 야구잠바","줄무늬 반폴라","와이드 청바지","적당쓰")
        insertPeopleList(4,"2018-11-28",8,"rainy",13,11,"","흰색 뽀글이","아디다스 레깅스","밤에 추워서 겉옷 챙겨야댐")
        insertPeopleList(5,"2018-11-29",10,"sunny",4,3,"초록색 뽀글이","베이지색 맨투맨","검정색 추리닝","편안하고 적당")
        insertPeopleList(6,"2018-11-30",20,"snowy",-5,-7,"초록색 뽀글이","베이지색 줄무늬셔츠+조끼스웨터","갈색 골덴 치마","겨울에는 바지 입자")
        insertPeopleList(7,"2018-12-01",12,"sunny",-4,-10,"흰색 패딩","초록색 맨투맨","스키니진","넘나 추움")
        insertPeopleList(8,"2018-12-01",17,"cloudy",0,-1,"옥색 패딩","흰색 목폴라티","청바지","따뜻")
        insertPeopleList(9,"2018-12-02",9,"little cloudy",1,-2,"흰색 패딩","보라색 니트","벨벳 추리닝","따뜻")
        insertPeopleList(10,"2018-12-03",13,"sunny",6,3,"흰색 패딩","옥색 맨투맨","아이보리 골덴 바지","맨투맨안에 히트택 입을껄")
        insertPeopleList(11,"2018-12-04",10,"sunny",2,3,"초록색 뽀글이","검정 반폴라 맨투맨","진갈색 바지","밤에 추움")
        insertPeopleList(12,"2018-12-05",9,"little cloudy",-9,-16,"학교 돕바","푸른색 맨투맨","갈색 골덴","바지	내복 입었으나 추움")
        insertPeopleList(13,"2018-12-06",14,"cloudy",4,2,"아디다스 롱패딩","남색 체크셔츠","검정 슬랙스","다리 춥")
        insertPeopleList(14,"2018-12-07",19,"little cloudy",2,-1,"옥색 패딩","베이지 줄무늬티","스키니진","춥춥")
        insertPeopleList(15,"2018-12-08",15,"rainy",7,5,"아디다스 롱패딩","초록색기모 맨투맨","검정 기모바지","적당쓰")
        insertPeopleList(16,"2018-12-09",7,"snowy",-3,-6,"아디다스 롱패딩","슬리데린 맨투맨","융기모 추리닝","따뜻행 *^~^*v")
        insertPeopleList(17,"2018-12-10",13,"sunny",3,1,"학교 돕바","다홍 맨투맨","아디다스 추리닝","내복입음. 아주 적당")
        insertPeopleList(18,"2018-12-11",13,"cloudy",1,-3,"검정색 롱패딩","와사비 가디건","벨벳 추리닝","와사비 너무 튐")
        insertPeopleList(19,"2018-12-12",9,"sunny",0,-5,"검정색 롱패딩","회색 니트","레깅스","다리 춥")


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
        recordAdapter.setOnItemClickListener(this)
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

    private fun insertPeopleList(idx:Int = 0, date:String = " ", time:Int = 0, weather:String = " ",
                                 temper:Int = 0, feel:Int = 0, outer:String? = null,
                                 top:String? = null, bottom:String? = null, memo:String? = null) {
        recordItem = RecordItem()
        recordItem.idx = idx
        recordItem.date = date
        recordItem.time = time
        recordItem.temper = temper
        recordItem.feel = feel
        recordItem.outer = outer
        recordItem.top = top
        recordItem.bottom = bottom
        recordItem.memo = memo
        recordRealm.beginTransaction()
        recordRealm.copyToRealm(recordItem)
        recordRealm.commitTransaction()
    }
}