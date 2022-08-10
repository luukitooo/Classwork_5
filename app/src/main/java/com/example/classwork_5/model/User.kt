package com.example.classwork_5.model

data class User(
    var username: String = "Guest",
    var email: String = "None",
    var phone: Int = 0,
    var fullName: String = "None",
    var jemali: String = "None",
    var birthday: String = "00/00/0000",
    var gender: String = "Unknown"
)