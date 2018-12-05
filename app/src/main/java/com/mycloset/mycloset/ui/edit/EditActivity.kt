package com.mycloset.mycloset.ui.edit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mycloset.mycloset.R
import com.mycloset.mycloset.ui.record.RecordItem
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
    }

    // edit_check_ib button을 누른 경우 db에 수정한 값이 저장됨
    fun onClick(v: View?) {
        when(v) {
            edit_check_ib -> Realm.getDefaultInstance().use { realm ->

                // 현재 activity에 있는 값으로 update

                realm.executeTransaction { _ ->
                    /*
                    person.name = "Senior Person"
                    person.age = 99
                    showStatus(person.name + " got older: " + person.age)
                    */
                }

            }

        }
    }
}
