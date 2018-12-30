package com.orego.battlecrane.bc.std.scenario.skirmish;

import com.orego.battlecrane.bc.api.context.BGameContext;
import com.orego.battlecrane.bc.api.context.controller.map.BMapController;
import com.orego.battlecrane.bc.api.model.player.BPlayer;
import com.orego.battlecrane.bc.api.model.contract.BUnit;
import com.orego.battlecrane.bc.api.scenario.BGameScenario;
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField;
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanHeadquarters;
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanWall;
import com.orego.battlecrane.bc.std.race.human.scenario.skirmish.adjutant.BHumanAdjutant;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.orego.battlecrane.bc.api.context.controller.map.BMapController.MAP_SIZE;

public final class BStandardSkirmishScenario implements BGameScenario {

    @NotNull
    @Override
    public final List<BPlayer> initPlayerList(final @NotNull BGameContext context) {
        final List<BPlayer> playerList = new ArrayList<>();
        final BPlayer redPlayer = new BPlayer(context, new BHumanAdjutant.Builder());
        final BPlayer bluePlayer = new BPlayer(context, new BHumanAdjutant.Builder());
        //Set enemies:
        redPlayer.addEnemy(bluePlayer);
        bluePlayer.addEnemy(redPlayer);
        //Add in player list:
        playerList.add(bluePlayer);
        playerList.add(redPlayer);
        return playerList;
    }

    @NotNull
    @Override
    public final BPlayer getStartPlayerPosition(final List<BPlayer> players) {
        return players.get(new Random().nextInt(1));
    }

    @Override
    public final void initMap(
            @NotNull final BMapController.BMapHolder mapHolder,
            @NotNull final BGameContext context
    ) {
        //Fill startTurn player stackPosition:
        final List<BPlayer> players = context.getPlayerManager().getPlayers();
        if (players.size() == 2) {
            //Get players:
            final BPlayer bluePlayer = players.get(0);
            final BPlayer redPlayer = players.get(1);
            //Put headquarters on the map:
            mapHolder.bindUnitTo(new BHumanHeadquarters(context, bluePlayer), 14, 14);
            mapHolder.bindUnitTo(new BHumanHeadquarters(context, redPlayer), 0, 0);
            //Put walls on the map
            for (int j = 0; j < 5; j++) {
                mapHolder.bindUnitTo(new BHumanWall(context, redPlayer), j, 4);
                mapHolder.bindUnitTo(new BHumanWall(context, redPlayer), 4, j);
                mapHolder.bindUnitTo(new BHumanWall(context, bluePlayer), 15 - j, 11);
                mapHolder.bindUnitTo(new BHumanWall(context, bluePlayer), 11, 15 - j);
            }
        } else {
            throw new IllegalArgumentException("Standard skirmish scenario supports two players!");
        }
        //Fill rest points:
        for (int x = 0; x < MAP_SIZE; x++) {
            for (int y = 0; y < MAP_SIZE; y++) {
                if (!mapHolder.hasBoundUnit(x, y)) {
                    final BUnit emptyField = new BEmptyField(context);
                    mapHolder.bindUnitTo(emptyField, x, y);
                }
            }
        }
    }
}