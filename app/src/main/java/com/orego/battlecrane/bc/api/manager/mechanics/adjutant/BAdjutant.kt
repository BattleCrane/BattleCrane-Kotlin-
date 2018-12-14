package com.orego.battlecrane.bc.api.manager.mechanics.adjutant;

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.action.BAction

abstract class BAdjutant(
    protected val context: BGameContext,
    protected val owner: BPlayer,
    protected val bonusFactories: MutableSet<BAction.Factory>
) {
    abstract fun onGameStarted()

    abstract fun onTurnStarted()

    abstract fun onTurnEnded()

    abstract inner class ResourceManager {

        var influenceCount = 0

        val buildingActions = mutableSetOf<BAction>()

        val armyActions = mutableSetOf<BAction>()
    }

    /**
     * Builder.
     */

    interface Builder {

        fun build(context: BGameContext, owner: BPlayer): BAdjutant
    }
}


///**
// * Assistant.
// */
//
//abstract class BAssistant : BAction.Listener {
//
//    abstract val producerSet : MutableSet<BAction.Factory>
//
//    val actionSet  = mutableSetOf<BAction>()
//
//    var buildActionCount = 0
//        set(value) {
//            this.actionSet.clear()
//            this.producerSet.forEach {
//                it.produceToStackByAbility(this.actionSet, value)
//            }
//            this.actionSet.forEach {
//                it.actionObservers[BIdGenerator.generateActionId()] = this
//            }
//            field = value
//        }
//
//    protected abstract fun calcTrainMarineLvl1ActionCount() : Int
//
//    fun calc() {
//        this.buildActionCount = this.calcTrainMarineLvl1ActionCount()
//    }
//
//    override fun onActionPerformed() {
//        this.buildActionCount = this.buildActionCount - 1
//    }
//}