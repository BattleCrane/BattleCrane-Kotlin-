package com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.model.unit.property.BHitPointable
import com.orego.battlecrane.bc.engine.api.model.unit.property.BProducable
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.BHumanBuilding

/**
 * Command center of human race.
 */

class BHumanHeadquarters private constructor(context: BGameContext, playerId: Long, x: Int, y: Int) :
    BHumanBuilding(context, playerId, x, y), BHitPointable,
    BProducable {

    companion object {

        const val HEIGHT = 2

        const val WIDTH = 2

        const val MAX_HIT_POINTS = 8
    }

    /**
     * Id.
     */

    override val hitPointableId: Long

    override val producableId: Long

    init {
        val generator = context.contextGenerator
        this.hitPointableId = generator.getIdGenerator(BHitPointable::class.java).generateId()
        this.producableId = generator.getIdGenerator(BProducable::class.java).generateId()
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

    override var isProduceEnable = false

    /**
     * Builder.
     */

    open class Builder(playerId: Long, x: Int, y: Int) : BUnit.Builder(playerId, x, y) {

        override fun onCreate(context: BGameContext) =
            BHumanHeadquarters(context, this.playerId, this.x, this.y)
    }
}