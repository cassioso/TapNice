package com.u8mobile.tapnice

import org.junit.Assert.assertEquals
import org.junit.Test

class TapLevelTest {

    @Test
    fun tapLevelEvaluatorNice() {
        assertEquals(TapLevel.NICE, TapLevel.evaluate(10))
    }

    @Test
    fun tapLevelEvaluatorRegular() {
        assertEquals(TapLevel.REGULAR, TapLevel.evaluate(30))
    }

    @Test
    fun tapLevelEvaluatorMeh() {
        assertEquals(TapLevel.MEH, TapLevel.evaluate(50))
    }

    @Test
    fun tapLevelEvaluatorShame() {
        assertEquals(TapLevel.SHAME, TapLevel.evaluate(100))
    }

    @Test
    fun computeTapLevelNice() {
        assertEquals(TapLevel.SHAME, TapLevel.computeLatestTapLevel())

        Thread.sleep(10)

        assertEquals(TapLevel.SHAME, TapLevel.computeLatestTapLevel())

        Thread.sleep(10)

        assertEquals(TapLevel.NICE, TapLevel.computeLatestTapLevel())

        Thread.sleep(10)

        assertEquals(TapLevel.NICE, TapLevel.computeLatestTapLevel())

        Thread.sleep(10)

        assertEquals(TapLevel.NICE, TapLevel.computeLatestTapLevel())
    }
}
