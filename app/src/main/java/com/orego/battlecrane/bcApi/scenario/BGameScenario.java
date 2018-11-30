package com.orego.battlecrane.bcApi.scenario;

import com.orego.battlecrane.bcApi.manager.battleMapManager.BBattleMapManager;

public interface BGameScenario {

    void initMap(final BBattleMapManager.BMapHolder mapHolder);
}