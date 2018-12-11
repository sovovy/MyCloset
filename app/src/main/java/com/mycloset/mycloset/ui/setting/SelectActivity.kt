package com.mycloset.mycloset.ui.setting

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.mycloset.mycloset.R
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream

import jxl.Workbook
import jxl.Sheet
import jxl.read.biff.BiffException
import kotlinx.android.synthetic.main.activity_select.*

class SelectActivity :AppCompatActivity() {

    private val get by lazy{intent.extras["Go"]}

    var excel : ListView ?= null
    var arrayAdapter : ArrayAdapter<String> ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        excel = findViewById(R.id.select_si_lv)
        arrayAdapter = ArrayAdapter(this, R.layout.item_listview)

        getExcel()
    }

    private fun getExcel(){

        var workbook : Workbook
        var inputStream : InputStream
        var sheet : Sheet

        try {
            inputStream = baseContext.resources.assets.open("xyyy.xls")
            workbook = Workbook.getWorkbook(inputStream)
            sheet = workbook.getSheet(0)

            var colTotal = sheet.getColumns()
            var maxrow : Int = sheet.getColumn(colTotal-1).size

            var compare : String = ""
            for(row in 1 until maxrow step 1){
                var excelload: String = sheet.getCell(0, row).contents as String
                if(excelload != compare) {
                    arrayAdapter!!.add(excelload)
                    compare = excelload
                }
            }
        }

        catch (e : IOException) {
            e.printStackTrace();
            Log.d("asdf","5678")
        }
        catch (e : BiffException){
            e.printStackTrace()
            Log.d("asdf","1234")
        }
        finally {
            excel!!.setAdapter(arrayAdapter)

            select_si_lv.setOnItemClickListener{ adapterView, view, position, l ->
                val siName = select_si_lv.getItemAtPosition(position).toString()
                val goGu = Intent(this, SelectGuActivity::class.java)
                goGu.putExtra("siName", siName)
                startActivity(goGu)
                finish()
            }
        }
    }
}