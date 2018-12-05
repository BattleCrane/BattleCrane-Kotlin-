package com.orego.battlecrane.bc.api.model.contract

interface BLevelable {

    var currentLevel: Int

    var maxLevel: Int

    fun increaseLevel(range: Int): Boolean {
        val hasIncreased = range > 0 && this.currentLevel < this.maxLevel
        if (hasIncreased) {
            val oldLevel = this.currentLevel
            val newLevel = this.currentLevel + range
            this.currentLevel = newLevel
            this.levelUpObserver.values.forEach { it.onLevelChanged(oldLevel, newLevel, range) }
        }
        return hasIncreased
    }

    //TODO: MAKE BOOLEAN
    fun decreaseLevel(range: Int) {
        if (range > 0) {
            val oldLevel = this.currentLevel
            var newLevel = this.currentLevel - range
            if (newLevel < 0) {
                newLevel = 1
            }
            this.currentLevel = newLevel
            this.levelDownObserver.values.forEach { it.onLevelChanged(oldLevel, newLevel, range) }
        }
    }

    val levelUpObserver: MutableMap<Long, LevelListener>

    val levelDownObserver: MutableMap<Long, LevelListener>

    interface LevelListener {

        fun onLevelChanged(oldLevel: Int, newLevel: Int, comparison: Int)
    }
}