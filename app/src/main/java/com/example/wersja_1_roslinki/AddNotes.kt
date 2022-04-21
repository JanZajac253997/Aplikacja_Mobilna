package com.example.wersja_1_roslinki

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_notes.*
import java.lang.Exception

class AddNotes : AppCompatActivity() {
    val dbTable="Notes"
    var id=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
    }

    fun buAdd(view: View){
        var dbManager=DbManager(this)

        var values = ContentValues()
        values.put("dbNazwa",etTitle.text.toString())

        values.put("dbt_low",temp_niska.text.toString())
        values.put("dbt_high",temp_wys.text.toString())

        values.put("dbhum_low",wilg_niska.text.toString())
        values.put("dbhum_high",wilg_wys.text.toString())

        values.put("dbhum_gleby_low",wilg_pow_niska.text.toString())
        values.put("dbhum_gleby_high",wilg_pow_wys.text.toString())

        val ID=dbManager.Insert(values)
        if(ID>0){
            Toast.makeText(this,"notka dodana", Toast.LENGTH_LONG).show()
            finish()
        }else{
            Toast.makeText(this,"nie da sie dodac notki", Toast.LENGTH_LONG).show()
        }
    }
}