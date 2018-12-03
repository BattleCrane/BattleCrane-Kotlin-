package com.orego.battlecrane.bcApi.scenario;

import com.orego.battlecrane.bcApi.manager.mapManager.BMapManager;

public interface BGameScenario {

    void initMap(final BMapManager.BMapHolder mapHolder);
}