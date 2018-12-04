package com.orego.battlecrane.bcApi.scenario.skirmish;

import com.orego.battlecrane.bcApi.manager.BGameContext;
import com.orego.battlecrane.bcApi.manager.mapManager.BMapManager;
import com.orego.battlecrane.bcApi.manager.playerManager.team.player.BPlayer;
import com.orego.battlecrane.bcApi.race.human.tools.BHumanTools;
import com.orego.battlecrane.bcApi.scenario.BGameScenario;
import com.orego.battlecrane.bcApi.model.unit.BUnit;
import com.orego.battlecrane.bcApi.model.unit.field.empty.BEmptyField;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.orego.battlecrane.bcApi.manager.mapManager.BMapManager.MAP_SIZE;

public final class BStandardSkirmishScenario implements BGameScenario {

    private final List<BPlayer> playerList = new ArrayList<>();

    public BStandardSkirmishScenario() {
        final BPlayer redPlayer = new BPlayer();
        final BPlayer bluePlayer = new BPlayer();
        //Set tools:
        redPlayer.setTools(new BHumanTools());
        bluePlayer.setTools(new BHumanTools());
        //Set enemies:
        redPlayer.addEnemy(bluePlayer);
        bluePlayer.addEnemy(redPlayer);
        //Add in player list:
        this.playerList.add(bluePlayer);
        this.playerList.add(redPlayer);
    }

    @Override
    public final void initMap(final BMapManager.BMapHolder mapHolder, final BGameContext context) {
        for (int x = 0; x < MAP_SIZE; x++) {
            for (int y = 0; y < MAP_SIZE; y++) {
                final BUnit emptyField = new BEmptyField(context);
                mapHolder.bindUnitTo(emptyField, x, y);
            }
        }
    }

    @Override
    public final BPlayer getStartPlayer() {
        return this.playerList.get(new Random().nextInt(1));
    }

    @Override
    public final List<BPlayer> getPlayerList() {
        return this.playerList;
    }
}