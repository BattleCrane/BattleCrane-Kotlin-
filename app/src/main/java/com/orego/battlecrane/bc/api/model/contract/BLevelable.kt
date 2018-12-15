package com.orego.battlecrane.bc.api.model.contract

interface BLevelable {

    var currentLevel: Int

    var maxLevel: Int

    val levelUpObserver: MutableMap<Long, Listener>

    val levelDownObserver: MutableMap<Long, Listener>

    fun increaseLevel(range: Int): Boolean {
        val hasIncreased = range > 0 && this.currentLevel < this.maxLevel
        if (hasIncreased) {
            val oldLevel = this.currentLevel
            var newLevel = this.currentLevel + range
            if (newLevel > this.maxLevel) {
               newLevel = this.maxLevel
            }
            this.currentLevel = newLevel
            this.levelUpObserver.values.forEach { it.onLevelChanged(oldLevel, newLevel, range) }
        }
        return hasIncreased
    }

    fun decreaseLevel(range: Int) : Boolean {
        val hasDecreased = range > 0 && this.currentLevel > 1
        if (hasDecreased) {
            val oldLevel = this.currentLevel
            var newLevel = this.currentLevel - range
            if (newLevel < 1) {
                newLevel = 1
            }
            this.currentLevel = newLevel
            this.levelDownObserver.values.forEach { it.onLevelChanged(oldLevel, newLevel, range) }
        }
        return hasDecreased
    }

    interface Listener {

        fun onLevelChanged(oldLevel: Int, newLevel: Int, comparison: Int)
    }
}