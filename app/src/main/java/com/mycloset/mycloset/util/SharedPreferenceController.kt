package com.mycloset.mycloset.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v4.content.ContextCompat.startActivity
import com.mycloset.mycloset.ui.setting.SelectActivity

class SharedPreferenceController private constructor() {
    // 첫 실행 판별
    fun setFirst(context: Context, flag: Boolean) {
        val pref = context.getSharedPreferences("FIRST", Activity.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean("FIRST", flag)
        editor.commit()
    }
    fun getFirst(context: Context): Boolean {
        val pref = context.getSharedPreferences("FIRST", Activity.MODE_PRIVATE)
        return pref.getBoolean("FIRST", false)
    }

    // 지역 설정 값
    fun setX(context: Context, x: Int) {
        val pref = context.getSharedPreferences("X", Activity.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putInt("X", x)
        editor.commit()
    }

    fun getX(context: Context): Int {
        val pref = context.getSharedPreferences("X", Activity.MODE_PRIVATE)
        return pref.getInt("X",0)
    }

    fun setY(context: Context, y: Int) {
        val pref = context.getSharedPreferences("Y", Activity.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putInt("Y", y)
        editor.commit()
    }

    fun getY(context: Context): Int {
        val pref = context.getSharedPreferences("Y", Activity.MODE_PRIVATE)
        return pref.getInt("Y",0)
    }
    // 동네 이름
    fun setGu(context: Context, name: String) {
        val pref = context.getSharedPreferences("GU", Activity.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("GU", name)
        editor.commit()
    }

    fun getGu(context: Context): String {
        val pref = context.getSharedPreferences("GU", Activity.MODE_PRIVATE)
        return pref.getString("GU","")
    }

    fun setTempErr(context: Context, err : Int){
        val pref = context.getSharedPreferences("ERR", Activity.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putInt("ERR", err)
        editor.commit()
    }

    fun getTempErr(context: Context) : Int {
        val pref = context.getSharedPreferences("ERR", Activity.MODE_PRIVATE)
        return pref.getInt("ERR", 3)
    }

    companion object {
        private var SINGLETON_INSTANCE: SharedPreferenceController? = null

        val sharedPreferenceController: SharedPreferenceController
            get() {
                if (SINGLETON_INSTANCE == null) {
                    SINGLETON_INSTANCE = SharedPreferenceController()
                }
                return SINGLETON_INSTANCE!!
            }
    }
}
