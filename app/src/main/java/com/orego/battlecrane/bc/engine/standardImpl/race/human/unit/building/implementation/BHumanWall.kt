package com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.model.unit.property.BHitPointable
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.BHumanBuilding

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

    open class Builder(playerId: Long, x: Int, y: Int) : BUnit.Builder(playerId, x, y) {

        override fun onCreate(context: BGameContext) =
            BHumanWall(context, this.playerId, this.x, this.y)
    }
}