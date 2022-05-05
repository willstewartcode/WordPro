package com.example.wordpro

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.example.wordpro.databinding.ActivityMainBinding
import java.util.*

// Array that holds all of the edit texts that users put guesses into
val GUESSBOXES = mutableListOf<EditText>()
// Keeps track of current guess the user is on
var currentLine = 1
// Word list - pulled from file
val WORDLIST = mutableListOf<String>()
// random word chosen from list
var randomWord = ""

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Loads words from file
        val input = getAssets().open("word_list.txt")
        val scanner = Scanner(input)
        while(scanner.hasNextLine()) {
            val word = scanner.nextLine()
            WORDLIST.add(word)
        }
        Log.i("STATUS_WORDLIST", WORDLIST.toString())

        // Gets random word from list
        randomWord = WORDLIST.random()
        Log.i("STATUS_RANDOM_WORD", "Random word: $randomWord")

        getAllTextBoxes() // loads all edit texts into list
        setTextChangedListener(GUESSBOXES)

        // Disables edit texts in all but the first line
        GUESSBOXES.forEachIndexed { index, editText ->
            if (index >= 5) {
                editText.isFocusable = false
            }
        }

        binding.checkButton.setOnClickListener(CheckButtonListener())
    }

    override fun onStart() {
        super.onStart()

        val preferences = getSharedPreferences(
            getString(R.string.preferences_storage_name),
            Context.MODE_PRIVATE
        )

        val useLargeText = preferences.getBoolean(getString(R.string.text_size_key), false)
        val useAlternativeColors = preferences.getBoolean(getString(R.string.alternative_colors_key), false)

        if (useLargeText) {
            setTextSize(24f)
        } else {
            setTextSize(16f)
        }
    }

    // Changes text size based on preference
    private fun setTextSize(textSize : Float) {
        // All widgets with text
        val widgets = listOf<TextView>(
            binding.edittextLine11, binding.edittextLine12, binding.edittextLine13,
            binding.edittextLine14, binding.edittextLine15, binding.edittextLine21,
            binding.edittextLine22, binding.edittextLine23, binding.edittextLine24,
            binding.edittextLine25, binding.edittextLine31, binding.edittextLine32,
            binding.edittextLine33, binding.edittextLine34, binding.edittextLine35,
            binding.edittextLine41, binding.edittextLine42, binding.edittextLine43,
            binding.edittextLine44, binding.edittextLine45, binding.edittextLine51,
            binding.edittextLine52, binding.edittextLine53, binding.edittextLine54,
            binding.edittextLine55, binding.edittextLine61, binding.edittextLine62,
            binding.edittextLine63, binding.edittextLine64, binding.edittextLine65,
            binding.checkButton
        )
        for (widget in widgets) {
            widget.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        }
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

    inner class CheckButtonListener : View.OnClickListener {
        override fun onClick(v: View?) {
            // Gets only the edit texts from the currently active line
            val currentLineBoxes = mutableListOf<EditText>()
            GUESSBOXES.forEachIndexed { index, editText ->
                when (currentLine) {
                    1 -> {
                        if (index in 0..4) {
                            currentLineBoxes.add(editText)
                        }
                    }
                    2 -> {
                        if (index in 5..9) {
                            currentLineBoxes.add(editText)
                        }
                    }
                    3 -> {
                        if (index in 10..14) {
                            currentLineBoxes.add(editText)
                        }
                    }
                    4 -> {
                        if (index in 15..19) {
                            currentLineBoxes.add(editText)
                        }
                    }
                    5 -> {
                        if (index in 20..24) {
                            currentLineBoxes.add(editText)
                        }
                    }
                    6 -> {
                        if (index in 25..29) {
                            currentLineBoxes.add(editText)
                        }
                    }
                }
            }
            Log.i("STATUS_CHECK", currentLineBoxes.toString())

            // Gets the input from the user
            val sb = StringBuilder()
            var guessedWord: String
            for (editText in currentLineBoxes) {
                val input = editText.text.toString().trim().lowercase(Locale.getDefault())
                if (input.isNotEmpty()) {
                    sb.append(input)
                }
            }
            if (sb.length < 5) {
                Toast.makeText(
                    applicationContext,
                    R.string.empty_field,
                    Toast.LENGTH_LONG
                ).show()
            } else if (sb.length == 5) {
                guessedWord = sb.toString()
                Log.i("STATUS_GUESS", "Guessed word: $guessedWord")
                val isInList = checkWordList(guessedWord)
                if (!isInList) {
                    Toast.makeText(
                        applicationContext,
                        R.string.invalid_word,
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    compareWords(guessedWord, currentLineBoxes)
                }
                currentLine++
                moveToNextLine()
            }
        }

        // Checks if guessed word is a word in the list
        fun checkWordList(guessedWord : String) : Boolean {
            var isInList = false
            for (word in WORDLIST) {
                if (word == guessedWord) {
                    isInList = true
                }
            }
            return isInList
        }

        // Compares guessed word with the correct answer
        fun compareWords(guessedWord: String, currentLineBoxes : MutableList<EditText>) {
//            for (editText in currentLineBoxes) {
//                editText.setBackgroundColor(Color.WHITE)
//            }
            guessedWord.forEachIndexed { index, char ->
                if (char == randomWord[0] || char == randomWord[1] || char == randomWord[2]
                    || char == randomWord[3] || char == randomWord[4]) {
                    currentLineBoxes[index].setBackgroundResource(R.drawable.edit_text_bg_yellow)
                }
                if (char == randomWord[index]) {
                    currentLineBoxes[index].setBackgroundResource(R.drawable.edit_text_bg_green)
                }
            }
        }

        fun moveToNextLine() {
            GUESSBOXES.forEachIndexed { index, editText ->
                when (currentLine) {
                    1 -> {
                        GUESSBOXES[index].isFocusable = index in 0..4
                    }
                    2 -> {
                        GUESSBOXES[index].isFocusable = index in 5..9
                    }
                    3 -> {
                        GUESSBOXES[index].isFocusable = index in 10..14
                    }
                    4 -> {
                        GUESSBOXES[index].isFocusable = index in 15..19
                    }
                    5 -> {
                        GUESSBOXES[index].isFocusable = index in 20..24
                    }
                    6 -> {
                        GUESSBOXES[index].isFocusable = index in 25..29
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.about_menu_item) {
            val builder = AlertDialog.Builder(binding.root.context)
            builder
                .setTitle(R.string.about)
                .setMessage(R.string.about_message)
                .setPositiveButton(android.R.string.ok, null)
                .show()
            return true
        } else if (item.itemId == R.id.preferences_menu_item) {
            val intent = Intent(this, PreferencesActivity::class.java)
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}