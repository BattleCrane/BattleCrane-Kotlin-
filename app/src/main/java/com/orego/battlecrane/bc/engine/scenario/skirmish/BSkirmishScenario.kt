package com.orego.battlecrane.bc.engine.scenario.skirmish

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BPlayerHeap
import com.orego.battlecrane.bc.engine.api.model.player.BPlayer
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.api.scenario.BGameScenario
import com.orego.battlecrane.bc.engine.api.scenario.util.plugin.location.BLocationPlugin
import com.orego.battlecrane.bc.engine.api.util.trigger.turn.BObservePlayerZonesOnStartTurnTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.location.grass.BSkirmishGrassPlugin
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.location.grass.field.BSkirmishEmptyGrassFieldBuilder
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.player.BSkirmishPlayerBuilder
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.BSkirmishHumanPlugin
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.headquarters.builder.BSkirmishHumanHeadquartersBuilder
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.wall.builder.BSkirmishHumanWallBuilder
import java.util.*

class BSkirmishScenario : BGameScenario() {

    companion object {

        const val PLAYER_COUNT = 2

        const val FIRST_PLAYER_ID: Long = 1

        const val SECOND_PLAYER_ID: Long = 2
    }

    val playerBuilderMap = mutableMapOf(
        FIRST_PLAYER_ID to BSkirmishPlayerBuilder(),
        SECOND_PLAYER_ID to BSkirmishPlayerBuilder()
    )

    private var currentLocationPlugin: BLocationPlugin? = null

    override fun installBaseTriggers(context: BGameContext) {
        //Invoke location plugin:
        super.installBaseTriggers(context)
        //Install zone observer:
        BObservePlayerZonesOnStartTurnTrigger.connect(context)
    }

    override fun getPlayers(context: BGameContext): Set<BPlayer> {
        val playerList = mutableSetOf<BPlayer>()
        val firstPlayerBuilder = this.playerBuilderMap[FIRST_PLAYER_ID]!!
        val secondPlayerBuilder = this.playerBuilderMap[SECOND_PLAYER_ID]!!
        //Create players:
        val firstPlayer = firstPlayerBuilder.build(context)
        val secondPlayer = secondPlayerBuilder.build(context)
        //Set enemies:
        firstPlayer.addEnemy(secondPlayer.playerId)
        secondPlayer.addEnemy(firstPlayer.playerId)
        //Add players in list:
        playerList.add(firstPlayer)
        playerList.add(secondPlayer)
        return playerList
    }

    override fun getStartTurnPlayerPosition() =
        if (Random().nextBoolean()) {
            0
        } else {
            1
        }

    override fun getUnits(context: BGameContext): Set<BUnit> =
        mutableSetOf<BUnit>()
            .addBuildings(context)
            .addEmptyFields(context)

    private fun MutableSet<BUnit>.addBuildings(context: BGameContext): MutableSet<BUnit> {

        fun newHeadquarters(playerId : Long, x: Int, y : Int): BUnit {
            val builder = BSkirmishHumanHeadquartersBuilder(playerId, x, y)
            return builder.build(context)
        }

        fun newWall(playerId : Long, x: Int, y : Int) : BUnit {
            val builder = BSkirmishHumanWallBuilder(playerId, x, y)
            return builder.build(context)
        }

        val heap = context.storage.getHeap(BPlayerHeap::class.java)
        val players = heap.getObjectList()
        if (players.size == PLAYER_COUNT) {
            //Get players:
            val bluePlayerId = players[0].playerId
            val redPlayerId = players[1].playerId
            //Put headquarters on the map:
            this.add(newHeadquarters(bluePlayerId, 14, 14))
            this.add(newHeadquarters(redPlayerId, 0, 0))
            //Put walls on the map:
            this.add(newWall(redPlayerId, 0, 4))
            this.add(newWall(redPlayerId, 1, 4))
            this.add(newWall(redPlayerId, 2, 4))
            this.add(newWall(redPlayerId, 3, 4))
            this.add(newWall(redPlayerId, 4, 4))
            this.add(newWall(redPlayerId, 4, 0))
            this.add(newWall(redPlayerId, 4, 1))
            this.add(newWall(redPlayerId, 4, 2))
            this.add(newWall(redPlayerId, 4, 3))
            this.add(newWall(bluePlayerId, 11, 11))
            this.add(newWall(bluePlayerId, 12, 11))
            this.add(newWall(bluePlayerId, 13, 11))
            this.add(newWall(bluePlayerId, 14, 11))
            this.add(newWall(bluePlayerId, 15, 11))
            this.add(newWall(bluePlayerId, 11, 15))
            this.add(newWall(bluePlayerId, 11, 14))
            this.add(newWall(bluePlayerId, 11, 13))
            this.add(newWall(bluePlayerId, 11, 12))
            return this
        } else {
            throw IllegalArgumentException("Standard skirmish gameScenario supports $PLAYER_COUNT players!")
        }
    }

    private fun MutableSet<BUnit>.addEmptyFields(context: BGameContext): Set<BUnit> {
        val matrix = BMapController.createMatrix()
        //Check uiUnit list:
        this.forEach { unit ->
            val id = unit.unitId
            unit.foreach { x, y ->
                matrix[x][y] = id
            }
        }
        //Fill rest fileds:
        BMapController.foreach { x, y ->
            if (matrix[x][y] == BMapController.NOT_ID) {
                val builder = BSkirmishEmptyGrassFieldBuilder(BPlayer.NEUTRAL_ID, x, y)
                val grassField = builder.build(context)
                this.add(grassField)
            }
        }
        return this
    }

    override fun getLocationPlugin() = this.currentLocationPlugin!!

    fun setLocationPlugin(locationPlugin: BLocationPlugin) {
        this.currentLocationPlugin = locationPlugin
    }

    //TODO:
    //Simulate location pick:

    init {
        this.setLocationPlugin(BSkirmishGrassPlugin())
    }

    //Simulate race pick:

    init {
        val firstPlayerBuilder = this.playerBuilderMap[FIRST_PLAYER_ID]!!
        val secondPlayerBuilder = this.playerBuilderMap[SECOND_PLAYER_ID]!!
        firstPlayerBuilder.addConfiguration(BSkirmishHumanPlugin())
        secondPlayerBuilder.addConfiguration(BSkirmishHumanPlugin())
    }
}