package com.orego.battlecrane.bcApi.scenario;

import com.orego.battlecrane.bcApi.manager.battleMapManager.BMapManager;

public interface BGameScenario {

    void initMap(final BMapManager.BMapHolder mapHolder);
}