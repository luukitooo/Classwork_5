package com.example.classwork_5.utils

enum class FieldUtils(val value: String) {
    TEXT_INPUT_TYPE("text"),
    NUMBER_INPUT_TYPE("number"),
    FIELD_TYPE_TEXT("input"),
    FIELD_TYPE_CHOOSER("chooser")
}

enum class FieldKeys(val value: String) {
    FIELD_ID("field_id"),
    HINT("hint"),
    FIELD_TYPE("field_type"),
    KEYBOARD("keyboard"),
    REQUIRED("required"),
    IS_ACTIVE("is_active"),
    ICON("icon")
}

enum class FieldHints(val value: String) {
    USERNAME("UserName"),
    EMAIL("Email"),
    PHONE("phone"),
    FULL_NAME("FullName"),
    JEMALI("Jemali"),
    BIRTHDAY("Birthday"),
    GENDER("Gender")
}