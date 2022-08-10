package com.example.classwork_5.model

data class Field(
    val fieldId: Int,
    val hint: String,
    val fieldType: String,
    val keyboard: String?,
    val required: Boolean,
    val isActive: Boolean,
    val icon: String
)
