package com.example.lab1_todo_app

data class Todo(val id:String, val title:String, val content:String) {

    constructor():this("","","")

    fun todoHashMap(): HashMap<String,Any>{
        val hashMap = HashMap<String, Any>()
        hashMap["id"]=id
        hashMap["title"] = title
        hashMap["content"] = content
        return hashMap
    }
}