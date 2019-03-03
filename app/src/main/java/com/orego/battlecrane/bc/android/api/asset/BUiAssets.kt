package com.orego.battlecrane.bc.android.api.asset

object BUiAssets {

    object Unit {

        object ViewModeKey {

            const val BLUE = "blue"

            const val RED = "red"

            const val NEUTRAL = "neutral"

            const val ACTIVE = "active"

            const val SELECTED = "selected"

            val COLOR_KEY_MAP = mapOf(
                0.toLong() to NEUTRAL,
                1.toLong() to BLUE,
                2.toLong() to RED
            )
        }
    }
}