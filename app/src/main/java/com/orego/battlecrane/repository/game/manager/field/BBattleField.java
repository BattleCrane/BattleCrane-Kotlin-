package com.orego.battlecrane.repository.game.manager.field;

import com.orego.battlecrane.repository.game.model.unit.BUnit;
import com.orego.battlecrane.repository.game.model.unit.field.BField;

public final class BBattleField {
    
    private static final int CELL_SIDE = 16;

    private static final int CELL_RANGE = CELL_SIDE * CELL_SIDE;
    
    private final BUnit[][] field = new BUnit[CELL_SIDE][CELL_SIDE];

    public BBattleField() {
        for (int i = 0; i < CELL_SIDE; i++) {
            for (int j = 0; j < CELL_SIDE; j++) {
                this.field[i][j] = new BField();
            }
        }
    }
    
    public final int getCellCount() {
        return CELL_RANGE;
    }
}