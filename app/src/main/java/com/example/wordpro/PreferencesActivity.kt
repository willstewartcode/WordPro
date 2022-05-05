package com.example.wordpro

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import com.example.wordpro.databinding.ActivityPreferencesBinding

class PreferencesActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {
    private lateinit var binding: ActivityPreferencesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.largetextCheckbox.setOnCheckedChangeListener(this)
    }

    override fun onStart() {
        val preferences = getSharedPreferences(
            getString(R.string.preferences_storage_name),
            Context.MODE_PRIVATE
        )

        // Updates checkbox widgets to reflect preferences
        val useLargeText = preferences.getBoolean(getString(R.string.text_size_key), false)
        binding.largetextCheckbox.isChecked = useLargeText

        val useAlternativeColors = preferences.getBoolean(getString(R.string.alternative_colors_key), false)
        binding.useAlternativeColorsCheckbox.isChecked = useAlternativeColors

        super.onStart()
    }

    override fun onCheckedChanged(checkbox: CompoundButton?, isChecked: Boolean) {
        val preferences = getSharedPreferences(
            getString(R.string.preferences_storage_name),
            Context.MODE_PRIVATE
        )

        with(preferences.edit()) {
            if (checkbox != null) {
                if (checkbox.id == R.id.largetext_checkbox) {
                    putBoolean(getString(R.string.text_size_key), isChecked)
                    apply()
                } else if (checkbox.id == R.id.use_alternative_colors_checkbox) {
                    putBoolean(getString(R.string.alternative_colors_key), isChecked)
                    apply()
                }
            }
        }
    }
}