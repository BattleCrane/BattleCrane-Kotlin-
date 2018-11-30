package com.orego.battlecrane.bcApi.util

interface BLevelable {

    var currentLevel : Int

    var maxLevel : Int

    fun increaseLevel(range : Int)

    fun decreaseLevel(range: Int)

    val levelObserver : MutableList<>

    interface LevelListener {

        fun onLevelChanged(oldLevel: Int, newLevel: Int, comparison: Int)
    }
}