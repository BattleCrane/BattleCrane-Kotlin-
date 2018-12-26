package com.orego.battlecrane.bc.api.context.mapManager;

import android.content.Context;
import com.orego.battlecrane.bc.api.context.BGameContext;
import com.orego.battlecrane.bc.api.context.mapManager.point.BPoint;
import com.orego.battlecrane.bc.api.model.unit.BUnit;
import com.orego.battlecrane.bc.api.scenario.BGameScenario;

import java.util.HashMap;
import java.util.Map;

public final class BMapManager {

    public static final int MAP_SIZE = 16;

    private final BPoint[][] matrix = new BPoint[MAP_SIZE][MAP_SIZE];

    private final Map<Long, BUnit> unitHeap = new HashMap<>();

    private final BGameContext context;

    public BMapManager(final BGameScenario scenario, final BGameContext context) {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                final BPoint cell = new BPoint(i, j);
                this.matrix[i][j] = cell;
            }
        }
        scenario.initMap(this, context);
        this.context = context;
    }

    public final boolean createUnit(final BUnit unit, final BPoint pivot) {
        final int startX = pivot.getX();
        final int startY = pivot.getY();
        final int endX = startX + unit.getHorizontalSize();
        final int endY = startX + unit.getVerticalSize();
        if (this.inBounds(startX, startY)
                && this.inBounds(startX, endY)
                && this.inBounds(endX, startY)
                && this.inBounds(endX, endY)) {
            if (unit.isPlaced(this.context, pivot)) {
                //Attach pivot to entity:
                unit.setPivot(this.matrix[startX][startY]);
                //Attach entity to matrix:
                for (int i = startX; i < endX; i++) {
                    for (int j = startY; j < endY; j++) {
                        final BPoint point = BMapManager.this.matrix[i][j];
                        point.setAttachedUnit(unit.getUnitId());
                    }
                }
                //Put in heap:
                final long unitId = unit.getUnitId();
                BMapManager.this.unitHeap.put(unitId, unit);
                return true;
            }
        }
        return false;
    }

    public final boolean inBounds(final BPoint point) {
        return this.inBounds(point.getX(), point.getY());
    }

    public final boolean inBounds(final int x, final int y) {
        return x >= 0 && x < MAP_SIZE && y >= 0 && y < MAP_SIZE;
    }

    public final BUnit getUnitByPosition(final BPoint position) {
        return this.getUnitByPosition(position.getX(), position.getY());
    }

    public final BUnit getUnitByPosition(final int x, final int y) {
        final long unitId = this.matrix[x][y].getAttachedUnit();
        return this.unitHeap.get(unitId);
    }

    /**
     * Boilerplate. TODO:Go to Lombok
     */

    public final Map<Long, BUnit> getUnitHeap() {
        return this.unitHeap;
    }
}