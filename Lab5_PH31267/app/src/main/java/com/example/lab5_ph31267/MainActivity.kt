package com.example.lab5_ph31267

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    var context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnShowData = findViewById<Button>(R.id.btnShowData)
        val rvPerson = findViewById<RecyclerView>(R.id.rvPerson)
        val personList = mutableListOf<Person>()
        val personAdapter= PersonAdapter(context, personList)

        val manager  = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        rvPerson.layoutManager = manager
        rvPerson.adapter = personAdapter

        val fn = VolleyFn()
        btnShowData!!.setOnClickListener {
            fn.getAllData(context, personList)
            personAdapter.notifyDataSetChanged()
        }
    }
}