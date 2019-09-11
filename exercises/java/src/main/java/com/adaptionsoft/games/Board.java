package com.adaptionsoft.games;

import com.adaptionsoft.games.categories.*;

import java.util.HashMap;
import java.util.Map;

public class Board {

    public Map<Integer, Category> createBoard() {
        Map<Integer, Category> positions = new HashMap<>();
        Pop pop = new Pop();
        Science science = new Science();
        Sports sports = new Sports();
        Rock rock = new Rock();
        positions.put(1, pop);
        positions.put(2, science);
        positions.put(3, sports);
        positions.put(4, rock);
        positions.put(5, pop);
        positions.put(6, science);
        positions.put(7, sports);
        positions.put(8, rock);
        positions.put(9, pop);
        positions.put(10, science);
        positions.put(11, sports);
        positions.put(12, rock);
        return positions;
    }
}
