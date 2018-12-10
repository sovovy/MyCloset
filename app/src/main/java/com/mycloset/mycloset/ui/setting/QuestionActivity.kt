package com.mycloset.mycloset.ui.setting

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mycloset.mycloset.R
import kotlinx.android.synthetic.main.activity_question.*

class QuestionActivity : AppCompatActivity() {
    var cnt = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        question_ayoungi.setOnClickListener {
            cnt++
            if(cnt==10)
                dvlp.visibility = View.VISIBLE
        }
    }
}
