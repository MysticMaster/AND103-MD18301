package com.example.lab5_ph31267

import android.content.Context
import android.nfc.Tag
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

class VolleyFn() {
    //ham doc du lieu tu API
    fun getAllData(
        context: Context,
        personList: MutableList<Person>,

    ) {
        //1. Tao request
        val queue = Volley.newRequestQueue(context);
        //2. truyen url
        val url = "https://hungnttg.github.io/array_json_new.json";
        //3. goi request
        //mang cua cac doi tuong -> goi mang truoc, doi tuong sau
        //JsonArrayRequest(url,thanhcong,thatbai);
        val request = JsonArrayRequest(url, { response ->

            for (i in 0 until response.length()) {
                try {
                    val person = response.getJSONObject(i)
                    val id = person.getString("id")
                    val name = person.getString("name")
                    val email = person.getString("email")
                    val gender = person.getString("gender")
                    val newPerson = Person(id, name, email, gender)
                    personList.add(newPerson)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }, { error -> Toast.makeText(context, "Lỗi: " + error, Toast.LENGTH_LONG) })
        //b4. thuc thi request
        queue.add(request)
    }
}