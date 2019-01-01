package com.orego.battlecrane.bc.std.scenario.skirmish

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BPlayerHeap
import com.orego.battlecrane.bc.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.api.model.entity.main.BUnit
import com.orego.battlecrane.bc.api.model.player.BPlayer
import com.orego.battlecrane.bc.api.scenario.BGameScenario
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField
import com.orego.battlecrane.bc.std.race.human.adjutant.BHumanAdjutant
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanHeadquarters
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanWall
import java.util.*

class BStandardSkirmishScenario : BGameScenario {

    override val firstTurnPlayerPosition = Random().nextInt(1)

    override fun getPlayers(context: BGameContext): List<BPlayer> {
        val playerList = mutableListOf<BPlayer>()
        val redPlayer = BPlayer(context)
        val bluePlayer = BPlayer(context)

        //Set enemies:
        redPlayer.addEnemy(bluePlayer.playerId)
        bluePlayer.addEnemy(redPlayer.playerId)

        //Add in player list:
        playerList.add(bluePlayer)
        playerList.add(redPlayer)
        return playerList
    }

    override fun getAdjutants(context: BGameContext): List<BAdjutant> {
        val adjutantList = mutableListOf<BAdjutant>()
        val heap = context.storage.getHeap(BPlayerHeap::class.java)
        val players = heap.getObjectList()
        if (players.size == 2) {
            adjutantList.add(BHumanAdjutant(context, players[0].playerId))
            adjutantList.add(BHumanAdjutant(context, players[1].playerId))
            return adjutantList
        } else {
            throw IllegalArgumentException("Standard skirmish scenario supports two players!")
        }
    }

    override fun getUnits(context: BGameContext) =
        mutableListOf<BUnit>()
            .addBuildings(context)
            .addEmptyFields(context)

    private fun MutableList<BUnit>.addBuildings(context: BGameContext): MutableList<BUnit> {
        val heap = context.storage.getHeap(BPlayerHeap::class.java)
        val players = heap.getObjectList()
        if (players.size == 2) {

            //Get players:
            val bluePlayerId = players[0].playerId
            val redPlayerId = players[1].playerId

            //Put headquarters on the map:
            this.add(BHumanHeadquarters(context, bluePlayerId, 14, 14))
            this.add(BHumanHeadquarters(context, redPlayerId, 0, 0))

            //Put walls on the map
            for (j in 0..4) {
                this.add(BHumanWall(context, redPlayerId, j, 4))
                this.add(BHumanWall(context, redPlayerId, 4, j))
                this.add(BHumanWall(context, bluePlayerId, 15 - j, 11))
                this.add(BHumanWall(context, bluePlayerId, 11, 15 - j))
            }
            return this
        } else {
            throw IllegalArgumentException("Standard skirmish scenario supports two players!")
        }
    }

    private fun MutableList<BUnit>.addEmptyFields(context: BGameContext): MutableList<BUnit> {
        val matrix = Array(BMapController.MAP_SIZE) {
            Array(BMapController.MAP_SIZE) {
                BMapController.NOT_INITIALIZED_UNIT_ID
            }
        }
        //Check unit list:
        for (i in 0 until this.size) {
            val unit = this[i]
            val unitId = unit.unitId
            val startX = unit.x
            val startY = unit.y
            val endX = startX + unit.width
            val endY = startY + unit.height
            for (x in startX until endX) {
                for (y in startY until endY) {
                    matrix[x][y] = unitId
                }
            }
        }
        //Fill rest fileds:
        for (x in 0 until BMapController.MAP_SIZE) {
            for (y in 0 until BMapController.MAP_SIZE) {
                if (matrix[x][y] == BMapController.NOT_INITIALIZED_UNIT_ID) {
                    this.add(BEmptyField(context, BPlayer.NEUTRAL_PLAYER_ID, x, y))
                }
            }
        }
        return this
    }
}