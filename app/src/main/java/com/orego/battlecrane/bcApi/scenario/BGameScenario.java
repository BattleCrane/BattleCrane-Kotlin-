package com.orego.battlecrane.bcApi.scenario;

import com.orego.battlecrane.bcApi.manager.BGameContext;
import com.orego.battlecrane.bcApi.manager.mapManager.BMapManager;
import com.orego.battlecrane.bcApi.manager.playerManager.team.player.BPlayer;

import java.util.List;

public interface BGameScenario {

    void initMap(final BMapManager.BMapHolder mapHolder, BGameContext context);

    BPlayer getStartPlayer();

    List<BPlayer> getPlayerList();
}