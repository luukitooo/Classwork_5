package com.example.classwork_5.viewmodel

import androidx.lifecycle.ViewModel
import com.example.classwork_5.model.Field
import com.example.classwork_5.utils.FieldKeys
import com.example.classwork_5.utils.data
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import java.lang.Exception

class MainViewModel: ViewModel() {

    private val _dataFlow = MutableStateFlow<List<Field>>(emptyList())
    val dataFlow get() = _dataFlow.asStateFlow()

    suspend fun getData() {
        val resultList = mutableListOf<Field>()
        val dataList = JSONArray(data)
        for (x in 0 until dataList.length()) {
            val objectList = dataList.getJSONArray(x)
            for (y in 0 until objectList.length()) {
                val obj = objectList.getJSONObject(y)
                with(obj) {
                    resultList.add(
                        Field(
                        getInt(FieldKeys.FIELD_ID.value),
                        getString(FieldKeys.HINT.value),
                        getString(FieldKeys.FIELD_TYPE.value),
                        try { getString(FieldKeys.KEYBOARD.value) } catch (e: Exception) { null },
                        getBoolean(FieldKeys.REQUIRED.value),
                        getBoolean(FieldKeys.IS_ACTIVE.value),
                        getString(FieldKeys.ICON.value)
                    )
                    )
                }
            }
        }
        _dataFlow.emit(resultList)
    }

}