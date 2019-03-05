package com.orego.battlecrane.bc.android.api.asset


object BUiAssets {

    enum class ViewMode(val key: String) {
        BLUE("blue"),
        RED("red"),
        NEUTRAL("neutral"),
        ACTIVE("active"),
        SELECTED("selected");

        companion object {

            fun getKeyByPlayerId(playerId: Long) =
                when (playerId) {
                    0.toLong() -> NEUTRAL.key
                    1.toLong() -> BLUE.key
                    2.toLong() -> RED.key
                    else -> throw UnsupportedOperationException("$playerId key is not supported")
                }
        }
    }
}