package com.u8mobile.tapnice

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("com.u8mobile.tapnice.prefs", 0)

    private val PREFS_TOP_SCORE = "PREFS_TOP_SCORE"

    var topScore: Int
        get() = sharedPreferences.getInt(PREFS_TOP_SCORE, 0)
        set(value) = sharedPreferences.edit().putInt(PREFS_TOP_SCORE, value).apply()
}
