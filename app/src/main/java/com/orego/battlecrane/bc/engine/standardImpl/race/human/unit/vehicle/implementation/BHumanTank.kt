package com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.vehicle.implementation

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.model.unit.property.BAttackable
import com.orego.battlecrane.bc.engine.api.model.unit.property.BHitPointable
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.vehicle.BHumanVehicle

/**
 * Basic human vehicle.
 */

class BHumanTank private constructor(context: BGameContext, playerId: Long, x: Int, y: Int) :
    BHumanVehicle(context, playerId, x, y), BHitPointable,
    BAttackable {

    companion object {

        const val HEIGHT = 1

        const val WIDTH = 1

        const val MAX_HEALTH = 2

        const val DAMAGE = 2
    }

    /**
     * Properties.
     */

    override val height =
        HEIGHT

    override val width =
        WIDTH

    override var currentHitPoints =
        MAX_HEALTH

    override var maxHitPoints =
        MAX_HEALTH

    override var damage =
        DAMAGE

    override var isAttackEnable = false

    /**
     * Id.
     */

    override val hitPointableId: Long

    override val attackableId: Long

    init {
        val generator = context.contextGenerator
        this.hitPointableId = generator.getIdGenerator(BHitPointable::class.java).generateId()
        this.attackableId = generator.getIdGenerator(BAttackable::class.java).generateId()
    }

    /**
     * Builder.
     */

    open class Builder(playerId: Long, x: Int, y: Int) : BUnit.Builder(playerId, x, y) {

        override fun onCreate(context: BGameContext) =
            BHumanTank(context, this.playerId, this.x, this.y)
    }
}