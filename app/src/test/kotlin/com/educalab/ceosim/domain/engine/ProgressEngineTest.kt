package com.educalab.ceosim.domain.engine

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ProgressEngineTest {

    @Test
    fun `level 1 for zero xp`() {
        assertEquals(1, ProgressEngine.levelForXp(0))
    }

    @Test
    fun `level 1 just below first threshold`() {
        assertEquals(1, ProgressEngine.levelForXp(19))
    }

    @Test
    fun `level 2 exactly at threshold`() {
        assertEquals(2, ProgressEngine.levelForXp(20))
    }

    @Test
    fun `level increases monotonically with more xp`() {
        assertEquals(3, ProgressEngine.levelForXp(50))
        assertEquals(4, ProgressEngine.levelForXp(100))
        assertEquals(5, ProgressEngine.levelForXp(180))
        assertEquals(6, ProgressEngine.levelForXp(300))
    }

    @Test
    fun `level never exceeds the max defined level even with huge xp`() {
        assertEquals(ProgressEngine.maxLevel(), ProgressEngine.levelForXp(999_999))
    }

    @Test
    fun `computeLevelInfo reports correct progress fraction mid level`() {
        val info = ProgressEngine.computeLevelInfo(35) // Nivel 2 (20..49)
        assertEquals(2, info.level)
        assertEquals(15, info.xpIntoLevel)
        assertTrue(info.progressFraction in 0f..1f)
    }

    @Test
    fun `computeLevelInfo caps progress fraction at max level`() {
        val info = ProgressEngine.computeLevelInfo(500)
        assertEquals(1f, info.progressFraction)
        assertEquals(null, info.xpForNextLevel)
    }

    @Test
    fun `computeLevelInfo never breaks with negative xp input`() {
        val info = ProgressEngine.computeLevelInfo(-50)
        assertEquals(1, info.level)
        assertEquals(0, info.currentXp)
    }
}
