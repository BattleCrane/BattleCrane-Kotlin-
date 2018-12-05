package com.orego.battlecrane.bc.api.scenario;

import com.orego.battlecrane.bc.api.manager.BGameContext;
import com.orego.battlecrane.bc.api.manager.mapManager.BMapManager;
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer;

import java.util.List;

public interface BGameScenario {

    void initMap(final BMapManager.BMapHolder mapHolder, BGameContext context);

    BPlayer getStartPlayer();

    List<BPlayer> getPlayerList();
}