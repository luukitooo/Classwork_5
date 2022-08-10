package com.example.classwork_5.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.classwork_5.utils.FieldHints
import com.example.classwork_5.viewmodel.MainViewModel
import com.example.classwork_5.databinding.ActivityMainBinding
import com.example.classwork_5.model.User
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val fieldAdapter by lazy { FieldAdapter() }

    private val viewModel: MainViewModel by viewModels()

    private val usersMap = mutableMapOf<Int, User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        onClickListeners()

        observers()

    }

    private fun init() {
        binding.recyclerView.apply {
            adapter = fieldAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        lifecycleScope.launch {
            viewModel.getData()
        }
    }

    private fun observers() {
        lifecycleScope.launch {
            viewModel.dataFlow.collect {
                fieldAdapter.submitList(it)
            }
        }
    }

    private fun onClickListeners() {
        binding.registerButton.setOnClickListener {
            if (!areFieldsEmpty()) {
                usersMap[usersMap.size] = getUser()
                d("myUsersMap", usersMap[usersMap.size - 1].toString())
            }
        }
    }

    private fun getUser(): User {
        val user = User()
        for (i in 0 until fieldAdapter.currentList.size) {
            when(val viewHolder = binding.recyclerView.findViewHolderForAdapterPosition(i)) {
                is FieldAdapter.EditTextViewHolder -> {
                    val editText = viewHolder.itemView as AppCompatEditText
                    when(editText.hint.toString()) {
                        FieldHints.USERNAME.value -> user.username = editText.text.toString()
                        FieldHints.EMAIL.value -> user.email = editText.text.toString()
                        FieldHints.PHONE.value -> user.phone = editText.text.toString().toInt()
                        FieldHints.FULL_NAME.value -> user.fullName = editText.text.toString()
                        FieldHints.JEMALI.value -> user.jemali = editText.text.toString()
                    }
                }
                is FieldAdapter.DateViewHolder -> {
                    val datePicker = viewHolder.itemView as DatePicker
                    user.birthday = "${datePicker.dayOfMonth}/${datePicker.month}/${datePicker.year}"
                }
                is FieldAdapter.GenderViewHolder -> {
                    val spinner = viewHolder.itemView as Spinner
                    user.gender = spinner.selectedItem.toString()
                }
            }
        }
        return user
    }

    private fun areFieldsEmpty(): Boolean {
        for (i in 0 until fieldAdapter.currentList.size) {
            when(val viewHolder = binding.recyclerView.findViewHolderForAdapterPosition(i)) {
                is FieldAdapter.EditTextViewHolder -> {
                    if ((viewHolder.itemView as AppCompatEditText).text?.isEmpty()!! && fieldAdapter.currentList[i].required) {
                        Toast.makeText(
                            this,
                            "${fieldAdapter.currentList[i].hint} is empty!",
                            Toast.LENGTH_SHORT
                        ).show()
                        return true
                    }
                }
            }
        }
        return false
    }
}