package com.example.lab5_ph31267

data class Person(val id:String, val name: String, val email: String, val gender: String){
    constructor():this("","","","")
}