package com.orego.battlecrane.bc.android.api.asset

object BUiAsset {

    const val BLUE = "blue"

    const val RED = "red"

    const val ACTIVE = "active"

    const val SELECTED = "selected"

    val COLOR_MAP = mapOf(
        1.toLong() to BLUE,
        2.toLong() to RED
    )

    object CommonPath {

        const val ATTACK = "common/action/action_attack.png"
    }
}