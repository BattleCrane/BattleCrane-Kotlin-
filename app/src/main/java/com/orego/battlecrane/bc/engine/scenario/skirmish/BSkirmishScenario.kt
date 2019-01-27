package com.orego.battlecrane.bc.engine.scenario.skirmish

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BPlayerHeap
import com.orego.battlecrane.bc.engine.api.model.player.BPlayer
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.api.scenario.BGameScenario
import com.orego.battlecrane.bc.engine.api.scenario.plugin.implementation.race.BRacePlugin
import com.orego.battlecrane.bc.engine.api.util.trigger.turn.BObservePlayerZonesOnStartTurnTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.location.grass.BSkirmishGrassPlugin
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.location.grass.field.BSkirmishEmptyGrassFieldBuilder
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.player.BStandardSkirmishPlayerBuilder
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.BSkirmishHumanPlugin
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.headquarters.builder.BSkirmishHumanHeadquartersBuilder
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.wall.builder.BSkirmishHumanWallBuilder
import java.util.*

class BSkirmishScenario : BGameScenario() {

    companion object {

        const val PLAYER_COUNT = 2
    }

    override fun installBaseTriggers(context: BGameContext) {
        BObservePlayerZonesOnStartTurnTrigger.connect(context)
    }

    override fun getPlayers(context: BGameContext): List<BPlayer> {
        val playerList = mutableListOf<BPlayer>()
        val builder = BStandardSkirmishPlayerBuilder()
        val bluePlayer = builder.build(context)
        val redPlayer = builder.build(context)
        //Set enemies:
        redPlayer.addEnemy(bluePlayer.playerId)
        bluePlayer.addEnemy(redPlayer.playerId)
        //Add in player list:
        playerList.add(bluePlayer)
        playerList.add(redPlayer)
        return playerList
    }

    override fun getStartTurnPlayerPosition() =
        if (Random().nextBoolean()) {
            0
        } else {
            1
        }

    override fun getLocationPlugin(context: BGameContext) =
        BSkirmishGrassPlugin()

    override fun getRacePlugins(context: BGameContext): List<BRacePlugin> {
        val pluginList = mutableListOf<BRacePlugin>()
        val heap = context.storage.getHeap(BPlayerHeap::class.java)
        val players = heap.getObjectList()
        if (players.size == PLAYER_COUNT) {
            pluginList.add(
                BSkirmishHumanPlugin(
                    players[0].playerId
                )
            )
            pluginList.add(
                BSkirmishHumanPlugin(
                    players[1].playerId
                )
            )
            return pluginList
        } else {
            throw IllegalArgumentException("Standard skirmish scenario supports $PLAYER_COUNT players!")
        }
    }

    override fun getUnits(context: BGameContext) =
        mutableListOf<BUnit>()
            .addBuildings(context)
            .addEmptyFields(context)

    private fun MutableList<BUnit>.addBuildings(context: BGameContext): MutableList<BUnit> {
        val heap = context.storage.getHeap(BPlayerHeap::class.java)
        val players = heap.getObjectList()
        if (players.size == PLAYER_COUNT) {
            //Get players:
            val bluePlayerId = players[0].playerId
            val redPlayerId = players[1].playerId
            //Put headquarters on the map:
            val headquartersBuilder = BSkirmishHumanHeadquartersBuilder()
            this.add(headquartersBuilder.build(context, bluePlayerId, 14, 14))
            this.add(headquartersBuilder.build(context, redPlayerId, 0, 0))
            //Put walls on the map:
            val wallBuiler = BSkirmishHumanWallBuilder()
            this.add(wallBuiler.build(context, redPlayerId, 0, 4))
            this.add(wallBuiler.build(context, redPlayerId, 1, 4))
            this.add(wallBuiler.build(context, redPlayerId, 2, 4))
            this.add(wallBuiler.build(context, redPlayerId, 3, 4))
            this.add(wallBuiler.build(context, redPlayerId, 4, 4))
            this.add(wallBuiler.build(context, redPlayerId, 4, 0))
            this.add(wallBuiler.build(context, redPlayerId, 4, 1))
            this.add(wallBuiler.build(context, redPlayerId, 4, 2))
            this.add(wallBuiler.build(context, redPlayerId, 4, 3))
            this.add(wallBuiler.build(context, bluePlayerId, 11, 11))
            this.add(wallBuiler.build(context, bluePlayerId, 12, 11))
            this.add(wallBuiler.build(context, bluePlayerId, 13, 11))
            this.add(wallBuiler.build(context, bluePlayerId, 14, 11))
            this.add(wallBuiler.build(context, bluePlayerId, 15, 11))
            this.add(wallBuiler.build(context, bluePlayerId, 11, 15))
            this.add(wallBuiler.build(context, bluePlayerId, 11, 14))
            this.add(wallBuiler.build(context, bluePlayerId, 11, 13))
            this.add(wallBuiler.build(context, bluePlayerId, 11, 12))
            return this
        } else {
            throw IllegalArgumentException("Standard skirmish gameScenario supports $PLAYER_COUNT players!")
        }
    }

    private fun MutableList<BUnit>.addEmptyFields(context: BGameContext): MutableList<BUnit> {
        val matrix = BMapController.createMatrix()
        //Check unit list:
        this.forEach { unit ->
            val id = unit.unitId
            unit.foreach { x, y ->
                matrix[x][y] = id
            }
        }
        //Fill rest fileds:
        val emptyGrassBuilder = BSkirmishEmptyGrassFieldBuilder()
        BMapController.foreach { x, y ->
            if (matrix[x][y] == BMapController.NOT_ID) {
                val grassField = emptyGrassBuilder.build(context, BPlayer.NEUTRAL_ID, x, y)
                this.add(grassField)
            }
        }
        return this
    }
}