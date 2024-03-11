package com.example.lab1_todo_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvToDoList = findViewById<RecyclerView>(R.id.rvToDoList)
        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)
        val todoList = mutableListOf<Todo>()
        val toDoAdapter = ToDoAdapter(this,todoList)

        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvToDoList.layoutManager = manager
        rvToDoList.adapter = toDoAdapter

        val firestore = FirebaseFirestore.getInstance()

        firestore.collection("TODO").get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents){
                    val todo = document.toObject(Todo::class.java)
                    if (todo != null) {
                        todoList.add(todo)
                    }
                }
                toDoAdapter.notifyDataSetChanged()
            }

        fabAdd.setOnClickListener{
            val builder = AlertDialog.Builder(this)

            val view = LayoutInflater.from(this).inflate(R.layout.add_or_edit_todo,null)
            builder.setView(view)

            val tvFunction = view.findViewById<TextView>(R.id.tvFunction)
            val edtTitle = view.findViewById<EditText>(R.id.edtTitle)
            val edtContent = view.findViewById<EditText>(R.id.edtContent)
            val btnAdd = view.findViewById<Button>(R.id.btnAdd)
            tvFunction.text = "Thêm nhắc nhở"
            btnAdd.text = "Thêm"

            val dialog = builder.create()
            btnAdd.setOnClickListener{
                if(edtTitle.text.toString().isEmpty()){
                    Toast.makeText(this,"Title Null",Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                if(edtContent.text.toString().isEmpty()){
                    Toast.makeText(this,"Content Null",Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                val todo = Todo(UUID.randomUUID().toString(),
                    edtTitle.text.toString(),
                    edtContent.text.toString())

                firestore.collection("TODO").document(todo.id)
                    .set(todo.todoHashMap())
                    .addOnSuccessListener {
                        Toast.makeText(this,"Thành công",Toast.LENGTH_LONG).show()
                        dialog.dismiss()
                        todoList.add(todo)
                        toDoAdapter.notifyDataSetChanged()
                    }
                    .addOnFailureListener{e->
                        Toast.makeText(this,"Thất bại",Toast.LENGTH_LONG).show()
                    }
            }

            dialog.show()
        }

    }
}