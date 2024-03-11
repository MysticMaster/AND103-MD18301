package com.example.lab1_todo_app

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class ToDoAdapter(
    private val context: Context,
    private val dataList: MutableList<Todo>
) : RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvContent: TextView = itemView.findViewById(R.id.tvContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.todo_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.tvTitle.text = item.title
        holder.tvContent.text = item.content

        val firestore = FirebaseFirestore.getInstance()

        holder.itemView.setOnClickListener {
            val builder = androidx.appcompat.app.AlertDialog.Builder(context)

            val view = LayoutInflater.from(context).inflate(R.layout.add_or_edit_todo,null)
            builder.setView(view)

            val tvFunction = view.findViewById<TextView>(R.id.tvFunction)
            val edtTitle = view.findViewById<EditText>(R.id.edtTitle)
            val edtContent = view.findViewById<EditText>(R.id.edtContent)
            val btnAdd = view.findViewById<Button>(R.id.btnAdd)
            tvFunction.text = "Cập nhật nhắc nhở"
            btnAdd.text = "Cập nhật"

            edtTitle.setText(item.title)
            edtContent.setText(item.content)

            val dialog = builder.create()
            btnAdd.setOnClickListener{
                if(edtTitle.text.toString().isEmpty()){
                    Toast.makeText(context,"Title Null",Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                if(edtContent.text.toString().isEmpty()){
                    Toast.makeText(context,"Content Null",Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                val todo = Todo(
                    item.id,
                    edtTitle.text.toString(),
                    edtContent.text.toString())

                firestore.collection("TODO").document(todo.id)
                    .update(todo.todoHashMap())
                    .addOnSuccessListener {
                        Toast.makeText(context,"Thành công",Toast.LENGTH_LONG).show()
                        dialog.dismiss()
                        dataList[position] = todo
                        notifyItemChanged(position)
                    }
                    .addOnFailureListener{e->
                        Toast.makeText(context,"Thất bại",Toast.LENGTH_LONG).show()
                    }
            }

            dialog.show()
        }

        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(context)
                .setTitle("Xác nhận xóa ")
                .setMessage("Chắc chắn xóa lời nhắc này?")
                .setPositiveButton("Xác nhận") { dialog, which ->
                    firestore.collection("TODO")
                        .document(item.id)
                        .delete()
                        .addOnSuccessListener {
                            Toast.makeText(context,"Đã xóa",Toast.LENGTH_LONG).show()
                            dataList.removeAt(position)
                            notifyDataSetChanged()
                        }
                        .addOnFailureListener{e->
                            Toast.makeText(context,"Xóa lỗi",Toast.LENGTH_LONG).show()
                        }
                }.setNegativeButton("Hủy"){diglog, which ->

                }.show()
            false
        }
    }
}