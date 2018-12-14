package com.example.toni.navegadoradgp

import android.content.ContentValues
import android.content.Intent
import android.os.Build.ID
import android.os.Bundle
import android.os.StrictMode
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase

    private val mOnNavigationItemSelectedListener = OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navegador -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.historial -> {
                val Historial = Intent(applicationContext, Main3Activity::class.java)
                startActivity(Historial)
                return@OnNavigationItemSelectedListener true
            }
            R.id.pestana -> {
                val Pestana = Intent(applicationContext, Main2Activity::class.java)
                startActivity(Pestana)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        database = FirebaseDatabase.getInstance()
        dbReference = database.getReference("Direccion")
        wb.requestFocus()
        wb.loadUrl("https://www.google.es")
    }

    fun busqueda(view: View) {
        visitarWeb()
    }

    fun visitarWeb() {
        wb.setWebViewClient(WebViewClient())
        var s = et.getText().toString()

        if (s.contains(".com") || s.contains(".es") || s.contains(".net")) {
            alta(s);
            wb.loadUrl("https://www."+s)
        }else{
            alta(s)
            wb.loadUrl("https://www.google.es/search?q="+s)
        }

    }

    fun alta(s: String) {
        if (et.length()==0)
            return
        val direccion=Direccion(s)
        dbReference.child("Datos_historial").push().setValue(direccion)
        et.setText(s)
    }

    fun refrescar(view: View){
        visitarWeb();
    }


}