package com.example.noteapp

import android.app.SearchManager
import android.app.SearchableInfo
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.SearchView
import android.widget.SearchView.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.Slider
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    var dataSet:ArrayList<Note>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dataSet=ArrayList<Note>()
        loadNotes("%")

    }

    override fun onRestart() {
        super.onRestart()
        loadNotes("%")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)

        val sv:SearchView= menu!!.findItem(R.id.notes_bar_search).actionView as SearchView
        val sm=getSystemService(SEARCH_SERVICE) as SearchManager
        sv.setSearchableInfo(sm.getSearchableInfo(componentName))
        sv.setOnQueryTextListener(object :OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                loadNotes("%$p0%")
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                loadNotes("%$p0%")
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.goToAddNoteAct->{
                var intent=Intent(this,AddNoteActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    fun loadNotes(title:String){
        val dbMng=DbManager(this)
        val cursor=dbMng.query("title like ?", arrayOf(title),"id DESC")
        dataSet!!.clear()
        try {
            cursor.moveToFirst()
            do {
                val id=cursor.getInt(cursor.getColumnIndex("id"))
                val title=cursor.getString(cursor.getColumnIndex("title"))
                val desc=cursor.getString(cursor.getColumnIndex("description"))

                dataSet!!.add(Note(id,title,desc))
            }while (cursor.moveToNext())
        }catch (ex:Exception){}
        val rv=findViewById<RecyclerView>(R.id.rvNote)
        rv.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rv.adapter=NoteRecyclerView(dataSet!!)
    }
}