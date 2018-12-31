package com.orego.battlecrane.bc.api.model.contract

interface BLevelable {

    val levelableId : Long

    var currentLevel: Int

    var maxLevel: Int
}

//TODO: MAKE NODE IN PIPELINE!!!
//fun decreaseLevel(range: Int) : Boolean {
//    val hasDecreased = range > 0 && this.currentLevel > 1
//    if (hasDecreased) {
//        val oldLevel = this.currentLevel
//        var newLevel = this.currentLevel - range
//        if (newLevel < 1) {
//            newLevel = 1
//        }
//        this.currentLevel = newLevel
//        this.levelDownObserver.values.forEach { it.onLevelChanged(oldLevel, newLevel, range) }
//    }
//    return hasDecreased
//}