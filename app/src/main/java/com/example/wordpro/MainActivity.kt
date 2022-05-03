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
// Keeps track of current guess the user is on
val currentLine = 1

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getAllTextBoxes()
        setTextChangedListener(GUESSBOXES)
    }

    // Sets focus to next edit text once a letter is entered in one
    fun setTextChangedListener(boxes : MutableList<EditText>) {
        boxes.forEachIndexed { index, editText ->
            editText.addTextChangedListener {
                if (editText.text.toString().trim().length == 1) {
                    if ((index + 1) < boxes.size) {
                        boxes[index + 1].requestFocus()
                    }
                }
            }
        }
    }

    // Adds all text boxes to a list
    fun getAllTextBoxes() {
        GUESSBOXES.addAll(listOf(binding.edittextLine11, binding.edittextLine12, binding.edittextLine13,
                                binding.edittextLine14, binding.edittextLine15, binding.edittextLine21,
                                binding.edittextLine22, binding.edittextLine23, binding.edittextLine24,
                                binding.edittextLine25, binding.edittextLine31, binding.edittextLine32,
                                binding.edittextLine33, binding.edittextLine34, binding.edittextLine35,
                                binding.edittextLine41, binding.edittextLine42, binding.edittextLine43,
                                binding.edittextLine44, binding.edittextLine45, binding.edittextLine51,
                                binding.edittextLine52, binding.edittextLine53, binding.edittextLine54,
                                binding.edittextLine55, binding.edittextLine61, binding.edittextLine62,
                                binding.edittextLine63, binding.edittextLine64, binding.edittextLine65
                                )
        )
    }
}