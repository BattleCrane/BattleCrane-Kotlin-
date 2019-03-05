package com.orego.battlecrane.bc.android.api.asset


object BUiAssets {

    enum class ViewMode(val key: String) {

        BLUE("blue"),

        RED("red"),

        NEUTRAL("neutral"),

        ACTIVE("active"),

        SELECTED("selected"),

        LOCKED("locked");

        companion object {

            fun getByPlayerId(playerId: Long): ViewMode =
                when (playerId) {
                    0.toLong() -> NEUTRAL
                    1.toLong() -> BLUE
                    2.toLong() -> RED
                    else -> throw UnsupportedOperationException("$playerId key is not supported")
                }
        }
    }
}