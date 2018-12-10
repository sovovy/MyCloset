package com.mycloset.mycloset.ui

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.mycloset.mycloset.R
import com.mycloset.mycloset.ui.record.RecordFragment
import com.mycloset.mycloset.ui.record.RecordItem
import com.mycloset.mycloset.ui.setting.SelectActivity
import com.mycloset.mycloset.ui.setting.SettingFragment
import com.mycloset.mycloset.ui.today.TodayFragment
import com.mycloset.mycloset.util.SharedPreferenceController
import com.mycloset.mycloset.util.TodayWeather
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // 하단탭 버튼이 눌릴 때, 아이콘 이미지 변경과 fragment 교체
    override fun onClick(v: View?) {
        when(v){
            main_today_iv->{
                replaceFragment(TodayFragment())
                main_today_iv.isSelected = true
                main_record_iv.isSelected = false
                main_setting_iv.isSelected = false
            }
            main_record_iv->{
                TodayWeather.flag = true
                replaceFragment(RecordFragment())
                main_today_iv.isSelected = false
                main_record_iv.isSelected = true
                main_setting_iv.isSelected = false
            }
            main_setting_iv->{
                replaceFragment(SettingFragment())
                main_today_iv.isSelected = false
                main_record_iv.isSelected = false
                main_setting_iv.isSelected = true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(TodayWeather.flag == true)
            addFragment(RecordFragment())
        else
            addFragment(TodayFragment())

        main_today_iv.isSelected = true

        main_today_iv.setOnClickListener(this)
        main_record_iv.setOnClickListener(this)
        main_setting_iv.setOnClickListener(this)

        Realm.init(this)
        // db 초기화
//        var config = RealmConfiguration.Builder()
//                .deleteRealmIfMigrationNeeded()
//                .schemaVersion(2)
//                .migration(MyMigration())
//                .build()
//        Realm.setDefaultConfiguration(config)

        // realm을 schemaVersion 2로 build
        var config = RealmConfiguration.Builder().schemaVersion(2).build()
        Realm.setDefaultConfiguration(config)
        Realm.getDefaultInstance()

//        var recordItem:Realm
//        recordItem = Realm.getDefaultInstance()
//        recordItem.beginTransaction()
//        recordItem.where(RecordItem::class.java).findAll().deleteAllFromRealm()
//        recordItem.commitTransaction()
    }

    // Fragment 붙이는 함수
    private fun addFragment(fragment: Fragment){
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.add(R.id.main_fragment_frame,fragment)
        transaction.commit()
    }

    // Fragment 교체
    private fun replaceFragment(fragment: Fragment){
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.main_fragment_frame,fragment)
        transaction.commit()
    }
}