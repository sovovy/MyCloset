package com.mycloset.mycloset.util

import android.app.Activity
import android.content.Context

class SharedPreferenceController private constructor() {
    // 지역 설정 값
    fun setRegion(context: Context, token: String) {
        val pref = context.getSharedPreferences("REGION", Activity.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("REGION", token)
        editor.commit()
    }

    fun getRegion(context: Context): String {
        val pref = context.getSharedPreferences("REGION", Activity.MODE_PRIVATE)
        return pref.getString("REGION","")
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
