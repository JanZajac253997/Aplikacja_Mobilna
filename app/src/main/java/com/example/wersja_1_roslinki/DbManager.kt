package com.example.wersja_1_roslinki

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class DbManager {

    val dbName="MyNotes"
    val dbTable="Notes"
    val colID="ID"
    val colNazwa="dbNazwa"
    val colt_low="dbt_low"
    val colt_high="dbt_high"
    val colhum_low="dbhum_low"
    val colhum_high="dbhum_high"
    val colhum_gleby_low="dbhum_gleby_low"
    val colhum_gleby_high="dbhum_gleby_high"
    val dbVersion=1



    val sqlCreateTable="CREATE TABLE IF NOT EXISTS "+ dbTable +" ("+ colID +" INTEGER PRIMARY KEY,"+ colNazwa+ " TEXT, " +
            colt_low +" INTEGER, " + colt_high+ " INTEGER, " +
            colhum_low+ " INTEGER, "+ colhum_high+ " INTEGER, "+
            colhum_gleby_low+ " INTEGER, " + colhum_gleby_high+ " INTEGER);"

    var sqlDB:SQLiteDatabase?=null

    constructor(context:Context){
        var db=DatabaseHelperNotes(context)
        sqlDB=db.writableDatabase
    }

    inner class DatabaseHelperNotes: SQLiteOpenHelper{
        var context:Context?=null
        constructor(context: Context):super(context,dbName,null,dbVersion){
            this.context=context
        }
        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL(sqlCreateTable)
            Toast.makeText(this.context," database is created", Toast.LENGTH_SHORT).show()
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0!!.execSQL("Drop table IF EXISTS " +dbTable)
        }


    }


    fun Insert(value:ContentValues):Long{

        val ID = sqlDB!!.insert(dbTable,"",value)
        return ID
    }

    fun Query(projection:Array<String>,selection:String,selectionArgs:Array<String>,SortOrder:String): Cursor {

        val qb=SQLiteQueryBuilder()
        qb.tables=dbTable
        val cursor=qb.query(sqlDB,projection,selection,selectionArgs,null,null,SortOrder)
        return cursor
    }

    fun Delete(selection:String,selectionArgs:Array<String>):Int{

        val count=sqlDB!!.delete(dbTable,selection,selectionArgs)
        return count
    }

    fun Update(values:ContentValues,selection:String,selectionargs:Array<String>):Int{

        val count=sqlDB!!.update(dbTable,values,selection,selectionargs)
        return count
    }

}