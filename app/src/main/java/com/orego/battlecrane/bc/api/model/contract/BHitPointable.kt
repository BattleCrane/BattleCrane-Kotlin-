package com.orego.battlecrane.bc.api.model.contract

interface BHitPointable {

    var currentHitPoints: Int

    var maxHitPoints: Int
}


//    TODO: MAKE INCREASE NODE IN PIPELINE:
//    fun increaseHitPoints(range: Int) {
//        val result = range > 0 && this.currentHitPoints < this.maxHitPoints
//        if (result) {
//            val oldHealth = this.currentHitPoints
//            val newHealth = this.currentHitPoints + range
//            if (newHealth > this.maxHitPoints) {
//                this.currentHitPoints = this.maxHitPoints
//            } else {
//                this.currentHitPoints = newHealth
//            }
//            this.increaseHitPointsObserver.values.forEach { it.onHitPointsChanged(oldHealth, newHealth, range) }
//        }
//    }