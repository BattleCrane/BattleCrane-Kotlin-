package com.orego.battlecrane.bcApi.scenario;

import com.orego.battlecrane.bcApi.manager.battlefield.cell.BCell;
import com.orego.battlecrane.bcApi.manager.unit.BUnit;

import java.util.Map;

public interface BGameScenario {

    void initMap(final BCell[][] cells, final Map<Integer, BUnit> unitHeap);
}