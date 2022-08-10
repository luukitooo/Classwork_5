package com.example.classwork_5.view

import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.classwork_5.utils.FieldUtils
import com.example.classwork_5.databinding.ItemFieldChooserDateBinding
import com.example.classwork_5.databinding.ItemFieldChooserGenderBinding
import com.example.classwork_5.databinding.ItemFieldInputBinding
import com.example.classwork_5.model.Field

class FieldAdapter: ListAdapter<Field, RecyclerView.ViewHolder>(FieldItemCallback) {

    inner class EditTextViewHolder(private val binding: ItemFieldInputBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val field = getItem(adapterPosition)
            binding.root.apply {
                hint = field.hint
                inputType = if (field.keyboard == FieldUtils.TEXT_INPUT_TYPE.value)
                    InputType.TYPE_CLASS_TEXT else InputType.TYPE_CLASS_NUMBER
                isActivated = field.isActive
            }
        }
    }

    inner class DateViewHolder(private val binding: ItemFieldChooserDateBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val field = getItem(adapterPosition)
            binding.root.apply {
                isActivated = field.isActive
            }
        }
    }

    inner class GenderViewHolder(private val binding: ItemFieldChooserGenderBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val field = getItem(adapterPosition)
            binding.root.apply {
                isActivated = field.isActive
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            FIELD_EDITTEXT -> EditTextViewHolder(ItemFieldInputBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            FIELD_CHOOSER_DATE -> DateViewHolder(ItemFieldChooserDateBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> GenderViewHolder(ItemFieldChooserGenderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is EditTextViewHolder -> holder.bind()
            is DateViewHolder -> holder.bind()
            is GenderViewHolder -> holder.bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        val field = getItem(position)
        if (field.fieldType == FieldUtils.FIELD_TYPE_TEXT.value)
            return FIELD_EDITTEXT
        else if (field.fieldType == FieldUtils.FIELD_TYPE_CHOOSER.value && field.hint == BIRTHDAY)
            return FIELD_CHOOSER_DATE
        else (field.fieldType == FieldUtils.FIELD_TYPE_CHOOSER.value && field.hint == GENDER)
            return FIELD_CHOOSER_GENDER
    }

    companion object {
        const val FIELD_EDITTEXT = 1
        const val FIELD_CHOOSER_DATE = 2
        const val FIELD_CHOOSER_GENDER = 3

        // Hints
        const val BIRTHDAY = "Birthday"
        const val GENDER = "Gender"
    }

    private object FieldItemCallback: DiffUtil.ItemCallback<Field>() {
        override fun areItemsTheSame(oldItem: Field, newItem: Field): Boolean {
            return oldItem.fieldId == newItem.fieldId
        }

        override fun areContentsTheSame(oldItem: Field, newItem: Field): Boolean {
            return oldItem == newItem
        }
    }
}