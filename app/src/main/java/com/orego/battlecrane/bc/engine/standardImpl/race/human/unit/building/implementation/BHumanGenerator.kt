package com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.model.property.BHitPointable
import com.orego.battlecrane.bc.engine.api.model.property.BLevelable
import com.orego.battlecrane.bc.engine.api.model.property.BProducable
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.BHumanBuilding

/**
 * Construct buildings.
 */

class BHumanGenerator private constructor(context: BGameContext, playerId: Long, x: Int, y: Int) :
    BHumanBuilding(context, playerId, x, y), BHitPointable,
    BLevelable,
    BProducable {

    companion object {

        const val HEIGHT = 2

        const val WIDTH = 2

        const val LEVEL_1_MAX_HIT_POINTS = 1

        const val LEVEL_2_MAX_HIT_POINTS = 2

        const val LEVEL_3_MAX_HIT_POINTS = 4

        const val FIRST_LEVEL = 1

        const val SECOND_LEVEL = 2

        const val THIRD_LEVEL = 3

        const val MAX_LEVEL =
            THIRD_LEVEL
    }

    /**
     * Id.
     */

    override val hitPointableId: Long

    override val levelableId: Long

    override val producableId: Long

    init {
        val generator = context.contextGenerator
        this.hitPointableId = generator.getIdGenerator(BHitPointable::class.java).generateId()
        this.levelableId = generator.getIdGenerator(BLevelable::class.java).generateId()
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
        LEVEL_1_MAX_HIT_POINTS

    override var maxHitPoints =
        LEVEL_1_MAX_HIT_POINTS

    override var currentLevel =
        FIRST_LEVEL

    override var maxLevel =
        MAX_LEVEL

    override var isProduceEnable = false

    /**
     * Builder.
     */

    open class Builder {

        open fun build(context: BGameContext, playerId: Long, x: Int, y: Int) =
            BHumanGenerator(
                context,
                playerId,
                x,
                y
            )
    }
}