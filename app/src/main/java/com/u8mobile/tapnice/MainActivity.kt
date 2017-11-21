package com.u8mobile.tapnice

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.u8mobile.tapnice.TapLevel.Companion.computeLatestTapLevel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindTopScore(App.prefs!!.topScore)
        bindTap()
    }

    private fun bindTopScore(score: Int) {

        if (score > App.prefs!!.topScore) {
            App.prefs!!.topScore = score
        }

        tvTopScore.text = String.format(getString(R.string.top_score), App.prefs!!.topScore)
    }

    private fun bindTap() {
        rootView.setOnClickListener({
            setupCountDownTimer()
            computeScore()
        })
    }

    private fun setupCountDownTimer() {
        if (countDownTimer != null) {
            return
        }

        countDownTimer = object : CountDownTimer(10000, 100) {
            override fun onTick(millisUntilFinished: Long) {
                progressBar.incrementProgressBy(-1)
            }

            override fun onFinish() {
                progressBar.progress = 0
                showResultScreen()
            }
        }

        countDownTimer?.start()
    }

    private fun computeScore() {
        val tapLevel = computeLatestTapLevel()

        val textSwitcherScoreCurrent = textSwitcherScore.currentView as TextView
        val currentTotalScore = textSwitcherScoreCurrent.text?.toString()?.toInt() ?: 0
        val newTotalScore = currentTotalScore + tapLevel.score

        updateTextScore(newTotalScore)
        updateTapLevel(tapLevel)
        bindTopScore(newTotalScore)
    }

    private fun updateTextScore(score: Int) {
        tvScore.text = score.toString()
        textSwitcherScore.setText(score.toString())
    }

    private fun updateTapLevel(tapLevel: TapLevel) {
        tvTapLevel.text = String.format(getString(R.string.tap_level), tapLevel.name)
        rootView.setBackgroundResource(tapLevel.colorResId)
    }

    private fun showResultScreen() {
        pause()

        val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                android.util.Pair(tvScore, tvScore.transitionName),
                android.util.Pair(tvTapLevel, tvTapLevel.transitionName),
                android.util.Pair(tvTopScore, tvTopScore.transitionName)
        )

        val intent = Intent(this, ResultActivity::class.java)
        startActivity(intent, options.toBundle())

    }

    private fun pause() {
        countDownTimer?.cancel()
        countDownTimer = null
        progressBar.progress = progressBar.max
    }

    private fun stop() {
        pause()
        updateTextScore(0)
        updateTapLevel(TapLevel.NONE)
        bindTopScore(0)
        TapLevel.reset()
    }

    override fun onResume() {
        super.onResume()
        stop()
    }
}
