package com.u8mobile.tapnice

import android.os.Bundle
import android.support.v4.widget.TextViewCompat
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.u8mobile.tapnice.App.Companion.prefs
import com.u8mobile.tapnice.TapLevel.Companion.getPredominantTapLevel
import com.u8mobile.tapnice.TapLevel.Companion.sortedTapList
import com.u8mobile.tapnice.TapLevel.Companion.totalScore
import kotlinx.android.synthetic.main.activity_result.*


class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        bindTopScore()
        bindYourScore()
        bindTapLists()
    }

    private fun bindTopScore() {
        tvTopScore.text = getString(R.string.top_score, prefs!!.topScore)
    }

    private fun bindTapLists() {
        llTapHistory.removeAllViews()
        llTapResultList.removeAllViews()

        sortedTapList!!.forEach {
            if (it.isEmpty()) {
                return@forEach
            }

            val tapLevel = it.first()
            val size = it.size

            llTapHistory.addView(createBarItemView(tapLevel, size))
            llTapResultList.addView(createTapLevelTextView(tapLevel, size))
        }
    }

    private fun bindYourScore() {
        rootView.setBackgroundResource(getPredominantTapLevel().colorResId)
        tvScore.text = getString(R.string.your_score, totalScore)
        tvTapLevel.text = getString(R.string.tap_level, getPredominantTapLevel().name)
    }

    private fun createBarItemView(tapLevel: TapLevel, weight: Int): View {
        val view = View(this)
        val layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT)
        layoutParams.weight = weight.toFloat()
        view.layoutParams = layoutParams
        view.setBackgroundResource(tapLevel.colorResId)
        return view
    }

    private fun createTapLevelTextView(tapLevel: TapLevel, count: Int): TextView {
        val textView = TextView(this)
        textView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        TextViewCompat.setTextAppearance(textView, R.style.score)
        textView.gravity = Gravity.END
        textView.textSize = 22f

        val textFormat = "%s (%dx) = %d"
        val text = textFormat.format(tapLevel.name, count, tapLevel.score * count)
        textView.text = text

        return textView
    }
}
