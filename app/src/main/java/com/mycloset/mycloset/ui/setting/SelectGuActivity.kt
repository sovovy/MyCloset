package com.mycloset.mycloset.ui.setting

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.mycloset.mycloset.R
import com.mycloset.mycloset.ui.MainActivity
import com.mycloset.mycloset.util.SharedPreferenceController
import jxl.Sheet
import jxl.Workbook
import kotlinx.android.synthetic.main.activity_select.*
import kotlinx.android.synthetic.main.activity_select_gu.*
import java.io.InputStream

class SelectGuActivity : AppCompatActivity() {

    private val siName by lazy{intent.extras["siName"]as String}

    var gu : ListView?= null
    var arrayAdapter : ArrayAdapter<String> ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_gu)

        gu = findViewById(R.id.select_gu_lv)
        arrayAdapter = ArrayAdapter(this, R.layout.item_listview)

        var workbook : Workbook
        var sheet : Sheet

        var inputStream = baseContext.resources.assets.open("xyyy.xls")
        workbook = Workbook.getWorkbook(inputStream)
        sheet = workbook.getSheet(0)

        var colTotal = sheet.getColumns()
        var maxrow : Int = sheet.getColumn(colTotal-1).size


        for(row in 1 until maxrow step 1){
            if(siName == (sheet.getCell(0,row).contents as String)) {
                var excelload: String = sheet.getCell(1, row).contents as String
                arrayAdapter!!.add(excelload)
            }
        }
        gu!!.setAdapter(arrayAdapter)

        select_gu_lv.setOnItemClickListener{ adapterView, view, position, l ->
            val guName = select_gu_lv.getItemAtPosition(position).toString()
            var X : Int = 0
            var Y : Int = 0
            for(row in 1 until maxrow step 1){
                if(guName == (sheet.getCell(1,row).contents as String)) {
                    X = sheet.getCell(2,row).contents.toInt()
                    Y = sheet.getCell(3,row).contents.toInt()
                    break
                }
            }

            SharedPreferenceController.sharedPreferenceController.setX(this, X)
            SharedPreferenceController.sharedPreferenceController.setY(this, Y)

            SharedPreferenceController.sharedPreferenceController.setGu(this, siName+" "+guName)

            val goMain = Intent(this, MainActivity::class.java)
            startActivity(goMain)
            finish()
        }
    }
}
