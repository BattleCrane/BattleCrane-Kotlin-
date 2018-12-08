package com.orego.battlecrane.bc.api.manager.mapManager;

import com.orego.battlecrane.bc.api.manager.BGameContext;
import com.orego.battlecrane.bc.api.manager.mapManager.point.BPoint;
import com.orego.battlecrane.bc.api.scenario.BGameScenario;
import com.orego.battlecrane.bc.api.model.unit.BUnit;

import java.util.HashMap;
import java.util.Map;

public final class BMapManager {

    public static final int MAP_SIZE = 16;

    private static final int MAP_RANGE = MAP_SIZE * MAP_SIZE;

    private final BPoint[][] matrix = new BPoint[MAP_SIZE][MAP_SIZE];

    private final Map<Long, BUnit> unitHeap = new HashMap<>();

    private final BMapHolder mapHolder = new BMapHolder();

    public final boolean createUnit(final BUnit unit, final BPoint pivot) {
        final boolean isPlaced = unit.isPlaced(pivot);
        if (isPlaced) {
            //Attach unit on matrix:
            this.mapHolder.bindUnitTo(unit, pivot.getX(), pivot.getY());
            //Notify subscribers:
            unit.getOnCreateObserver().values().forEach(it -> it.onCreate(unit));
        }
        return isPlaced;
    }

    public final boolean inBounds(final int x, final int y) {
        return x >= 0 && x < MAP_SIZE && y >= 0 && y < MAP_SIZE;
    }

    public final BUnit getUnitByPosition(final BPoint position) {
        return this.getUnitByPosition(position.getX(), position.getY());
    }

    public final BUnit getUnitByPosition(final int x, final int y) {
        return this.matrix[x][y].getAttachedUnit();
    }

    public final class BMapHolder {

        //TODO: Handling errors: cell was attached, index out bound etc:
        public final void bindUnitTo(final BUnit unit, final int x, final int y) {
            final int horizontalSide = unit.getHorizontalSide();
            final int verticalSide = unit.getVerticalSide();
            final BPoint pivot = BMapManager.this.matrix[x][y];
            //Attach pivot to entity:
            unit.setPivot(pivot);
            //Attach entity to matrix:
            for (int i = x; i < horizontalSide; i++) {
                for (int j = y; j < verticalSide; j++) {
                    final BPoint point = BMapManager.this.matrix[i][j];
                    point.setAttachedUnit(unit);
                }
            }
            //Put in heap:
            final long unitId = unit.getUnitId();
            BMapManager.this.unitHeap.put(unitId, unit);
        }

        //TODO: Unbind.

        public final boolean hasBoundUnit(final int x, final int y) {
            return BMapManager.this.matrix[x][y].getAttachedUnit() != null;
        }
    }

    public BMapManager(final BGameScenario scenario, final BGameContext gameContext) {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                final BPoint cell = new BPoint(i, j);
                this.matrix[i][j] = cell;
            }
        }
        scenario.initMap(this.mapHolder, gameContext);
    }

    /**
     * Boilerplate. TODO:Go to Lombok
     */

    public final Map<Long, BUnit> getUnitHeap() {
        return this.unitHeap;
    }
}