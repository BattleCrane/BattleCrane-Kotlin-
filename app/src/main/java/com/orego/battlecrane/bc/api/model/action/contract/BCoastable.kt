package com.orego.battlecrane.bc.api.model.action.contract

interface BCoastable {

    var coast: Int

    var resourceCount: Int

    fun buy() {
        val oldValue = this.resourceCount
        val newValue = this.resourceCount - coast
        this.resourceCount = newValue
        this.coastableObserver.values.forEach { it.onBought(oldValue, newValue, this.coast) }
    }

    val coastableObserver: MutableMap<Long, Listener>

    interface Listener {

        fun onBought(oldResourceCount: Int, newResourceCount: Int, comparison: Int)
    }
}