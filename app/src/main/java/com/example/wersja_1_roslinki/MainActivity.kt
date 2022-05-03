package com.example.wersja_1_roslinki

import android.annotation.SuppressLint
import android.app.PendingIntent.getActivity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.BaseAdapter
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import com.example.wersja_1_roslinki.R.layout.notatka
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_strona_w.*
import kotlinx.android.synthetic.main.notatka.view.*



class MainActivity : AppCompatActivity() {

    var listNotes=ArrayList<Notki>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //listNotes.add(Notki(1,"tytul1",1,3,1,2,0,6))
        //listNotes.add(Notki(2,"tytul2",1,3,1,2,0,6))
        //listNotes.add(Notki(3,"tytul3",1,3,1,2,0,6))
        //Toast.makeText(baseContext, "test1", LENGTH_SHORT).show();

        LoadQuery("%")
    }


    override fun onResume(){
        super.onResume()
        LoadQuery("%")
    }

    @SuppressLint("Range")
    fun LoadQuery(title:String){
        var dbManager=DbManager(this)
        val projections=arrayOf("ID","dbNazwa","dbt_low","dbt_high","dbhum_low","dbhum_high","dbhum_gleby_low","dbhum_gleby_high")
        val selectionArgs=arrayOf(title)
        val cursor=dbManager.Query(projections!!,"dbNazwa like ?",selectionArgs,"dbNazwa")

        listNotes.clear()
        if(cursor.moveToFirst()){
            do{
                val ID=cursor.getInt(cursor.getColumnIndex("ID"))
                val dbNazwa=cursor.getString(cursor.getColumnIndex("dbNazwa"))

                val dbt_low=cursor.getInt(cursor.getColumnIndex("dbt_low"))
                val dbt_high=cursor.getInt(cursor.getColumnIndex("dbt_high"))

                val dbhum_low=cursor.getInt(cursor.getColumnIndex("dbhum_low"))
                val dbhum_high=cursor.getInt(cursor.getColumnIndex("dbhum_high"))

                val dbhum_gleby_low=cursor.getInt(cursor.getColumnIndex("dbhum_gleby_low"))
                val dbhum_gleby_high=cursor.getInt(cursor.getColumnIndex("dbhum_gleby_high"))

                listNotes.add(Notki(ID,dbNazwa,dbt_low,dbt_high,dbhum_low,dbhum_high,dbhum_gleby_low,dbhum_gleby_high))
            }while (cursor.moveToNext())
        }
        var myNotesAdapter = AdapterNotatek(this, listNotes)
        lvNotes.adapter=myNotesAdapter
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

            menuInflater.inflate(R.menu.main_menu,menu)

            val sv=menu!!.findItem(R.id.app_bar_search).actionView as SearchView
            val sm=getSystemService(Context.SEARCH_SERVICE) as SearchManager

            sv.setSearchableInfo(sm.getSearchableInfo(componentName))
            sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    //Toast.makeText(applicationContext,query,Toast.LENGTH_SHORT).show()

                    LoadQuery("%"+ query +"%")

                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }
            })

            return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.addNote->{
                //idz do dodania notki
                var intent = Intent(this,AddNotes::class.java)
                startActivity(intent)
            }

            R.id.internet->{

                var intent = Intent(this,strona_internetowa::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class AdapterNotatek:BaseAdapter{
        var listNotesadapter=ArrayList<Notki>()
        var context:Context?=null
        constructor(context:Context, listNotesAdapter:ArrayList<Notki>):super(){
            //Toast.makeText(baseContext, "constructor", LENGTH_SHORT).show();
            this.listNotesadapter=listNotes
            this.context=context;
        }

        override fun getCount(): Int {  //zwraca 0???
            //Toast.makeText(baseContext, listNotesadapter.size.toString(), LENGTH_SHORT).show();
            return listNotesadapter.size;
        }

        override fun getItem(p0: Int): Any {
            return listNotesadapter[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }



        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            //Toast.makeText(baseContext, "test3", LENGTH_LONG).show();
            var myView=layoutInflater.inflate(R.layout.notatka,null)
            var moja_notatka=listNotesadapter[p0]
            myView.nazwa_notatki.text = moja_notatka.NoteName
            myView.t_low.text = moja_notatka.NoteTemp_low.toString()
            myView.t_high.text = moja_notatka.NoteTemp_high.toString()
            myView.h1_low.text = moja_notatka.NoteHum_low.toString()
            myView.h1_high.text = moja_notatka.NoteHum_high.toString()
            myView.h2_low.text = moja_notatka.NoteHum_gleby_low.toString()
            myView.h2_high.text = moja_notatka.NoteHum_gleby_high.toString()
            //Toast.makeText(baseContext, "test4", LENGTH_LONG).show();

            myView.ivDelete.setOnClickListener(View.OnClickListener {
                var dbManager=DbManager(this.context!!)
                val selectionArgs = arrayOf(moja_notatka.ID.toString())
                dbManager.Delete("ID=?", selectionArgs)
                LoadQuery("%")
            })
            myView.ivEdit.setOnClickListener( View.OnClickListener {
                GoToUpdate(moja_notatka)

            })

            return myView
        }
    }

    fun GoToUpdate(note:Notki){
        var intent=Intent(this,AddNotes::class.java)
        intent.putExtra("ID",note.ID)
        intent.putExtra("dbNazwa",note.NoteName)

        intent.putExtra("dbt_low",note.NoteTemp_low)
        intent.putExtra("dbt_high",note.NoteTemp_high)

        intent.putExtra("dbhum_low",note.NoteHum_low)
        intent.putExtra("dbhum_high",note.NoteHum_high)

        intent.putExtra("dbhum_gleby_low",note.NoteHum_gleby_low)
        intent.putExtra("dbhum_gleby_high",note.NoteHum_gleby_high)

        startActivity(intent)
    }




}