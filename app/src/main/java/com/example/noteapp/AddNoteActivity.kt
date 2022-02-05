package com.example.noteapp

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import java.lang.Exception

class AddNoteActivity : AppCompatActivity() {
    var id=0
    var etTitle:EditText?=null
    var etDesc:EditText?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        etTitle = findViewById<EditText>(R.id.etTitle)
        etDesc = findViewById<EditText>(R.id.etDescription)
        try {
            val b:Bundle= intent.extras!!
            id=b.getInt("id",0)
            etTitle!!.setText(b.getString("title"))
            etDesc!!.setText(b.getString("desc"))
        }catch (ex:Exception){}


    }

    fun saveNote(view: View) {
        val dbMng = DbManager(this)
        val values = ContentValues()
        values.put("title", etTitle!!.text.toString())
        values.put("description", etDesc!!.text.toString())
        if(id==0) {
            dbMng.insert(values)
            finish()
        }else{
            dbMng.update(values,"id=?", arrayOf(id.toString()))
            finish()
        }
    }
}