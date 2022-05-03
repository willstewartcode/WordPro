package com.example.wordpro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.example.wordpro.databinding.ActivityMainBinding

// Array that holds all of the edit texts that users put guesses into
val GUESSBOXES = mutableListOf<EditText>()

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val LINE1 = mutableListOf<EditText>(binding.edittextLine11, binding.edittextLine12,
                                            binding.edittextLine13, binding.edittextLine14,
                                            binding.edittextLine15)

        LINE1.forEachIndexed { index, editText ->
            if (index < LINE1.size) {
                editText.addTextChangedListener {
                    if (editText.text.toString().trim().length == 1) {
                        LINE1[index + 1].requestFocus()
                    }
                }
            }
        }
    }
}