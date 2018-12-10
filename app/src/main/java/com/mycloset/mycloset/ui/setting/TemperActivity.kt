package com.mycloset.mycloset.ui.setting

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mycloset.mycloset.R
import com.mycloset.mycloset.ui.record.RecordFragment
import com.mycloset.mycloset.util.SharedPreferenceController
import kotlinx.android.synthetic.main.activity_temper.*

class TemperActivity : AppCompatActivity(), View.OnClickListener{
    var temp : Int = 3

    override fun onClick(v: View?) {
        when(v) {
            temper_errDecrement_bt -> {
                if (temp > 0){
                    temp -= 1
                    temper_errPicker_tv.text = temp.toString() + "℃"
                    SharedPreferenceController.sharedPreferenceController.setTempErr(this, temp)
                }
            }
            temper_errIncrement_bt -> {
                if(temp < 20){
                    temp += 1
                    temper_errPicker_tv.text = temp.toString() + "℃"
                    SharedPreferenceController.sharedPreferenceController.setTempErr(this, temp)
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temper)

        temp = SharedPreferenceController.sharedPreferenceController.getTempErr(this)
        temper_errPicker_tv.text = temp.toString()+"℃"

        temper_errDecrement_bt.setOnClickListener(this)
        temper_errIncrement_bt.setOnClickListener(this)
    }

}
