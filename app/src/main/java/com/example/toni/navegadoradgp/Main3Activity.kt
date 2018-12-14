package com.example.toni.navegadoradgp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.view.Window
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.android.synthetic.main.main3_activity.*

class Main3Activity : AppCompatActivity() {

    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main3_activity)
        database = FirebaseDatabase.getInstance()
        dbReference = database.getReference("Direccion")

        val menuListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                tv.text = ""
                println("data change")
                for (objj  in dataSnapshot.children){
                    val registro=objj.getValue()
                    try {
                        var reg : String = registro.toString()
                        reg = reg.substring(11,reg.length-1)
                        tv.text = tv.text.toString()+reg + "\n" + "\n"
                    }
                    catch (e: Exception)
                    {
                        tv.text=""
                    }

                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }

        dbReference.child("Datos_historial").addValueEventListener(menuListener)

    }

    fun borrar (view: View){
        dbReference.child("Datos_historial")
        dbReference.removeValue()
    }
}
