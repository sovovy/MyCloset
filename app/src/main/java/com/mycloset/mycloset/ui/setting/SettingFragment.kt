package com.mycloset.mycloset.ui.setting

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mycloset.mycloset.R
import com.mycloset.mycloset.R.id.setting_location_constraint
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_setting.view.*

class SettingFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_setting,container,false)

        v.setting_location_constraint.setOnClickListener {
            startActivity(Intent(activity, SelectActivity::class.java))
        }
        return v
    }

}