package com.orego.battlecrane.repository.game.manager.field;

import com.orego.battlecrane.repository.game.model.unit.BUnit;
import com.orego.battlecrane.repository.game.model.unit.field.BField;

public final class BBattleField {
    
    public static final int FIELD_SIDE = 16;

    private static final int FIELD_RANGE = FIELD_SIDE * FIELD_SIDE;
    
    private final BUnit[][] field = new BUnit[FIELD_SIDE][FIELD_SIDE];

    public BBattleField() {
        for (int i = 0; i < FIELD_SIDE; i++) {
            for (int j = 0; j < FIELD_SIDE; j++) {
                this.field[i][j] = new BField();
            }
        }
    }
    
    public final int getCellCount() {
        return FIELD_RANGE;
    }
}