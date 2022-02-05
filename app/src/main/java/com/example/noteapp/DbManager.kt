package com.example.noteapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbManager {
    val dbname="db_notes"
    val notesTable="notes"
    val id="id"
    val title="title"
    val version=1;
    val description="description"
    val createTable="CREATE TABLE $notesTable ($id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " $title TEXT, $description TEXT)"
    var sqlDb:SQLiteDatabase?=null
    constructor(context:Context){
        sqlDb=noteDbHelper(context).writableDatabase
    }
    inner class noteDbHelper(val context:Context): SQLiteOpenHelper(context,dbname,null,version) {

        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL(createTable)
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0!!.execSQL("DROP TABLE $notesTable")
        }
    }
    fun insert(values:ContentValues):Long{
        val i=sqlDb!!.insert(notesTable,"",values)
        return i
    }
    fun query(selection:String,selectionArgs:Array<String>,order:String):Cursor{
        var cursor:Cursor=sqlDb!!.query(notesTable,null,selection,selectionArgs,null,null,order)
        return cursor
    }
    fun delete(clause:String,Clauseargs:Array<String>):Int{
        val i=sqlDb!!.delete(notesTable,clause,Clauseargs)
        return i
    }
    fun update(values: ContentValues,clause: String,clauseArgs:Array<String>):Int{
        val i=sqlDb!!.update(notesTable,values,clause,clauseArgs)
        return i
    }

}