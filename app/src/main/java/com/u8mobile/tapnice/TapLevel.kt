package com.u8mobile.tapnice

import java.io.Serializable
import java.util.*

enum class TapLevel(val score: Int, val colorResId: Int, val maxScore: Long) : Serializable {
    NICE(129, R.color.score_nice, 15L),
    REGULAR(96, R.color.score_regular, 40L),
    MEH(63, R.color.score_meh, 90L),
    SHAME(-30, R.color.score_shame, Long.MAX_VALUE),
    NONE(0, R.color.screenBackground, 0);

    companion object {

        private var lastTimeMillis = 0L
        private var lastDeltaTimeMillis = 0L

        var sortedTapList: List<List<TapLevel>>? = null
            get() = createSortedTapList()

        val tapList = ArrayList<TapLevel>()
        var totalScore = 0

        fun evaluate(value: Long): TapLevel {
            return TapLevel.values().filter { it.maxScore >= value && it != NONE }[0]
        }

        fun computeLatestTapLevel(): TapLevel {
            val currentTimeMillis = System.currentTimeMillis()
            val deltaTimeMillis = currentTimeMillis - lastTimeMillis
            val deltaGap = Math.abs(lastDeltaTimeMillis - deltaTimeMillis)

            lastTimeMillis = currentTimeMillis
            lastDeltaTimeMillis = deltaTimeMillis

            val tapLevel = TapLevel.evaluate(deltaGap)
            tapList.add(tapLevel)
            totalScore = totalScore.plus(tapLevel.score)

            return tapLevel
        }

        fun reset() {
            lastTimeMillis = 0L
            lastDeltaTimeMillis = 0L
            totalScore = 0
            sortedTapList = null
            tapList.clear()
        }

        fun getPredominantTapLevel(): TapLevel {
            return sortedTapList!!.last().first()
        }

        private fun createSortedTapList(): List<List<TapLevel>> {
            val filteredTapList = ArrayList<List<TapLevel>>()
            TapLevel.values().mapTo(filteredTapList) { tapList.filter { tapLevel: TapLevel -> tapLevel == it } }
            return filteredTapList.sortedBy { list: List<TapLevel> -> list.size }
        }

    }
}