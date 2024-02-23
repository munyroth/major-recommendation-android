package com.munyroth.majorrecommendation.utility

import android.text.InputFilter
import android.text.Spanned
import android.util.Log

class InputFilterMinMax(private val min: Float, private val max: Float) : InputFilter {

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val sourceStr = source?.toString() ?: return null
        val destStr = dest?.toString() ?: ""

        // Check if adding this input would exceed the max length
        val input = StringBuilder(destStr).apply {
            replace(dstart, dend, sourceStr.substring(start, end))
        }.toString()

        // Check for two decimal places
        if (input.contains(".")) {
            val decimalIndex = input.indexOf(".")
            val decimalLength = input.length - decimalIndex - 1
            if (decimalLength > 2) return ""
        }

        // Apply min-max range filter
        try {
            val newValue = input.toFloat()
            if (!isInRange(min, max, newValue)) return ""
        } catch (e: NumberFormatException) {
            Log.e("InputFilterMinMax", "Error parsing input")
            return ""
        }

        return null // Input passed both filters
    }

    private fun isInRange(a: Float, b: Float, c: Float): Boolean {
        return c in a..b
    }
}