package com.mycloset.mycloset.ui.today

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mycloset.mycloset.R
import com.mycloset.mycloset.ui.add.AddActivity
import kotlinx.android.synthetic.main.fragment_today.view.*

class TodayFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_today,container,false)
        v.today_add_ib.setOnClickListener {
            startActivity(Intent(activity, AddActivity::class.java))
        }
        return v
    }
}