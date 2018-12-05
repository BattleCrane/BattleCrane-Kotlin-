package com.orego.battlecrane.bc.std.scenario.skirmish;

import com.orego.battlecrane.bc.api.manager.BGameContext;
import com.orego.battlecrane.bc.api.manager.mapManager.BMapManager;
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer;
import com.orego.battlecrane.bc.api.model.unit.BUnit;
import com.orego.battlecrane.bc.api.scenario.BGameScenario;
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField;
import com.orego.battlecrane.bc.std.race.human.scenario.skirmish.BSkirmishHumanTools;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.orego.battlecrane.bc.api.manager.mapManager.BMapManager.MAP_SIZE;

public final class BStandardSkirmishScenario implements BGameScenario {

    @Override
    public final void initMap(
            @NotNull final BMapManager.BMapHolder mapHolder,
            @NotNull final BGameContext context
    ) {
        for (int x = 0; x < MAP_SIZE; x++) {
            for (int y = 0; y < MAP_SIZE; y++) {
                final BUnit emptyField = new BEmptyField(context);
                mapHolder.bindUnitTo(emptyField, x, y);
            }
        }
    }

    @NotNull
    @Override
    public final BPlayer getStartPlayer(final List<BPlayer> players) {
        return players.get(new Random().nextInt(1));
    }

    @NotNull
    @Override
    public final List<BPlayer> initPlayerList(final @NotNull BGameContext context) {
        final List<BPlayer> playerList = new ArrayList<>();
        final BPlayer redPlayer = new BPlayer();
        final BPlayer bluePlayer = new BPlayer();
        //Set tools:
        redPlayer.setTools(new BSkirmishHumanTools(context, redPlayer));
        bluePlayer.setTools(new BSkirmishHumanTools(context, bluePlayer));
        //Set enemies:
        redPlayer.addEnemy(bluePlayer);
        bluePlayer.addEnemy(redPlayer);
        //Add in player list:
        playerList.add(bluePlayer);
        playerList.add(redPlayer);
        return playerList;
    }
}