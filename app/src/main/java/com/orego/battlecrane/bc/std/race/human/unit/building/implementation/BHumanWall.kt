package com.orego.battlecrane.bc.std.race.human.unit.building.implementation

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.property.hitPointable.BHitPointable
import com.orego.battlecrane.bc.std.race.human.unit.building.BHumanBuilding

/**
 * Defends human units.
 */

class BHumanWall private constructor(context: BGameContext, playerId: Long, x: Int, y: Int) :
    BHumanBuilding(context, playerId, x, y), BHitPointable {

    companion object {

        const val HEIGHT = 1

        const val WIDTH = 1

        const val MAX_HIT_POINTS = 4
    }

    /**
     * Id.
     */

    override val hitPointableId: Long

    init {
        val generator = context.contextGenerator
        this.hitPointableId = generator.getIdGenerator(BHitPointable::class.java).generateId()
    }

    /**
     * Property.
     */

    override val height =
        HEIGHT

    override val width =
        WIDTH

    override var currentHitPoints =
        MAX_HIT_POINTS

    override var maxHitPoints =
        MAX_HIT_POINTS

    /**
     * Builder.
     */

    open class Builder {

        fun build(context: BGameContext, playerId: Long, x: Int, y: Int) =
            BHumanWall(context, playerId, x, y)
    }
}