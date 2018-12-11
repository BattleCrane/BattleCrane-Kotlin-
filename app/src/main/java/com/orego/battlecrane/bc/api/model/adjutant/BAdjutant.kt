package com.orego.battlecrane.bc.api.model.adjutant;

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.api.util.BIdGenerator

abstract class BAdjutant {

    companion object {

        const val START_VALUE = 0
    }

    abstract val assistants : MutableSet<BAssistant>

    var influenceResourceCount: Int = START_VALUE

    open fun onStartGame() {

    }

    open fun onStartTurn() {
        this.influenceResourceCount++
        this.assistants.forEach { it.calc() }
    }

    /**
     * Builder.
     */

    interface Builder {

        fun build(context: BGameContext, owner: BPlayer) : BAdjutant
    }

    /**
     * Assistant.
     */

    abstract class BAssistant : BAction.Listener {

        abstract val producerSet : MutableSet<BAction.Producer>

        val actionSet  = mutableSetOf<BAction>()

        var abilityCount = 0
            set(value) {
                this.actionSet.clear()
                this.producerSet.forEach {
                    it.produceToStackByAbility(this.actionSet, value)
                }
                this.actionSet.forEach {
                    it.actionObservers[BIdGenerator.generateActionId()] = this
                }
                field = value
            }

        protected abstract fun calcAbilityCount() : Int

        fun calc() {
            this.abilityCount = this.calcAbilityCount()
        }

        override fun onActionPerformed() {
            this.abilityCount = this.abilityCount - 1
        }
    }
}