package com.orego.battlecrane.bc.std.race.human.unit.vehicle.implementation.tank

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.property.attackable.BAttackable
import com.orego.battlecrane.bc.api.model.property.hitPointable.BHitPointable
import com.orego.battlecrane.bc.std.race.human.unit.vehicle.BHumanVehicle

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

    open class Builder {

        open fun build(context: BGameContext, playerId: Long, x: Int, y: Int) =
            BHumanTank(context, playerId, x, y)
    }
}