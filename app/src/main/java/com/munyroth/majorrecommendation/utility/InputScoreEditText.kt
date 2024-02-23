package com.munyroth.majorrecommendation.utility

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import com.google.android.material.textfield.TextInputEditText

class InputScoreEditText : TextInputEditText {

    private var storedText: String = ""

    companion object {
        private const val MAX_SUBJECT_SCORE = 75f
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        // Set the input filter for the score
        filters = arrayOf(InputFilterMinMax(0f, MAX_SUBJECT_SCORE))

        // Add TextWatcher to handle the input value
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("InputScoreEditText", "beforeTextChanged: $s")
                // Store the current text if it's '0'
                if (s.toString() == "0") {
                    // Use a more descriptive variable name than 'd'
                    // to hold the current text value
                    storedText = s.toString()
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("InputScoreEditText", "beforeTextChanged: $s")
                // If the stored text was '0' and user entered more than one character
                if (storedText == "0") {
                    storedText = ""

                    if (!s.isNullOrEmpty() && s.length > 1) {
                        // Check if the first character is '0' and the second is not '.' (decimal point)
                        if (s[0] == '0' && s[1] != '.') {
                            // Remove the first character
                            setText(s.substring(1))
                            // Change the cursor position
                            text?.let { setSelection(it.length) }
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                Log.d("InputScoreEditText", "beforeTextChanged: $s")
            }
        })
    }
}
