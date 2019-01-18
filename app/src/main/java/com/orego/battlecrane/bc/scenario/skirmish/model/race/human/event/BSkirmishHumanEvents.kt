package com.orego.battlecrane.bc.scenario.skirmish.model.race.human.event

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.*
import com.orego.battlecrane.bc.std.race.human.util.BHumanEvents
import com.orego.battlecrane.bc.std.race.human.util.BHumanTools
import com.orego.battlecrane.bc.scenario.skirmish.model.race.human.adjutant.trigger.building.*

object BSkirmishHumanEvents {

    object Construct {

        class BarracksEvent(producableId: Long, x: Int, y: Int) :
            BHumanEvents.Construct.Event(producableId, x, y, BHumanBarracks.WIDTH, BHumanBarracks.HEIGHT) {

            override fun getEvent(playerId: Long, x: Int, y: Int) =
                BSkirmishHumanBarracksOnCreateTrigger.Event.create(playerId, x, y)
        }

        class FactoryEvent(producableId: Long, x: Int, y: Int) :
            BHumanEvents.Construct.Event(producableId, x, y, BHumanFactory.WIDTH, BHumanFactory.HEIGHT) {

            override fun isEnable(context: BGameContext, playerId: Long): Boolean {
                if (super.isEnable(context, playerId)) {
                    val barracksFactoryDiff = BHumanTools.countDiffBarracksFactory(context, playerId)
                    if (barracksFactoryDiff > 0) {
                        return true
                    }
                }
                return false
            }

            override fun getEvent(playerId: Long, x: Int, y: Int) =
                BSkirmishHumanFactoryOnCreateTrigger.Event.create(playerId, x, y)
        }

        class GeneratorEvent(producableId: Long, x: Int, y: Int) :
            BHumanEvents.Construct.Event(producableId, x, y, BHumanGenerator.WIDTH, BHumanGenerator.HEIGHT) {


            override fun isEnable(context: BGameContext, playerId: Long): Boolean {
                if (super.isEnable(context, playerId)) {
                    val generatorCount = BHumanTools.countGenerators(context, playerId)
                    if (generatorCount < BHumanTools.GENERATOR_LIMIT) {
                        return true
                    }
                }
                return false
            }

            override fun getEvent(playerId: Long, x: Int, y: Int) =
                BSkirmishHumanGeneratorOnCreateTrigger.Event.create(playerId, x, y)
        }

        class TurretEvent(producableId: Long, x: Int, y: Int) :
            BHumanEvents.Construct.Event(producableId, x, y, BHumanTurret.WIDTH, BHumanTurret.HEIGHT) {

            override fun getEvent(playerId: Long, x: Int, y: Int) =
                BSkirmishHumanTurretOnCreateTrigger.Event.create(playerId, x, y)
        }

        class WallEvent(producableId: Long, x: Int, y: Int) :
            BHumanEvents.Construct.Event(producableId, x, y, BHumanWall.WIDTH, BHumanWall.HEIGHT * WALL_COUNT) {

            companion object {

                const val WALL_COUNT = 2
            }

            override fun getEvent(playerId: Long, x: Int, y: Int) =
                BSkirmishHumanWallOnCreateTrigger.Event.create(playerId, x, y)
        }
    }
}