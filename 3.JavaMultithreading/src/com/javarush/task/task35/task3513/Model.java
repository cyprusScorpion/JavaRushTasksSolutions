package com.javarush.task.task35.task3513;

import java.util.*;

public class Model {
    private static final int FIELD_WIDTH = 4;
    private Tile[][] gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
    int score;
    int maxTile;
    Stack<Tile[][]> previousStates = new Stack<>();
    Stack<Integer> previousScores = new Stack<>();
    private boolean isSaveNeeded = true;

    public Model() {
        resetGameTiles();
    }

    private void saveState(Tile[][] tiles) {
        Tile[][] tilesToSave = new Tile[tiles.length][tiles[0].length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                tilesToSave[i][j] = new Tile(tiles[i][j].value);
            }
        }
        previousStates.push(tilesToSave);
        int scoreToSave = score;
        previousScores.push(scoreToSave);
        isSaveNeeded = false;
    }

    public void rollback() {
        if (!previousStates.empty()) {
            gameTiles = previousStates.pop();
        }
        if (!previousScores.empty()) {
            score = previousScores.pop();
        }
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    public boolean canMove() {
        if (!getEmptyTiles().isEmpty()) {
            return true;
        }
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j < gameTiles.length - 1; j++) {
                if (gameTiles[i][j].value == gameTiles[i][j + 1].value) {
                    return true;
                }
            }
        }
        for (int j = 0; j < gameTiles.length; j++) {
            for (int i = 0; i < gameTiles.length - 1; i++) {
                if (gameTiles[i][j].value == gameTiles[i + 1][j].value) {
                    return true;
                }
            }
        }
        return false;
    }

    protected void resetGameTiles() {
        this.score = 0;
        this.maxTile = 0;
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j < gameTiles[i].length; j++) {
                gameTiles[i][j] = new Tile();
            }
        }
        addTile();
        addTile();
    }

    private void addTile() {
        List<Tile> list = getEmptyTiles();
        if (list != null && list.size() != 0) {
            list.get((int) (list.size() * Math.random())).value =
                    (Math.random() < 0.9 ? 2 : 4);
        }
    }

    private List<Tile> getEmptyTiles() {
        List<Tile> emptyTiles = new ArrayList<>();
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j < gameTiles[i].length; j++) {
                if (gameTiles[i][j].isEmpty()) {
                    emptyTiles.add(gameTiles[i][j]);
                }
            }
        }
        return emptyTiles;
    }

    private boolean compressTiles(Tile[] tiles) {
        boolean changes = false;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].value == 0 && i < tiles.length - 1 && tiles[i + 1].value != 0) {
                Tile temp = tiles[i];
                tiles[i] = tiles[i + 1];
                tiles[i + 1] = temp;
                i = -1;
                changes = true;
            }
        }
        return changes;
    }

    private boolean mergeTiles(Tile[] tiles) {
        boolean changes = false;
        compressTiles(tiles);
        for (int i = 0; i < tiles.length - 1; i++) {
            if (tiles[i].value != 0 & tiles[i].value == tiles[i + 1].value) {
                tiles[i].value += tiles[i + 1].value;
                tiles[i + 1].value = 0;
                compressTiles(tiles);
                this.score += tiles[i].value;
                changes = true;
                if (tiles[i].value > this.maxTile) {
                    this.maxTile = tiles[i].value;
                }
            }
        }
        return changes;
    }

    protected void left() {
        if (isSaveNeeded) {
            saveState(gameTiles);
        }
        boolean changes = false;
        for (int i = 0; i < gameTiles.length; i++) {
            if (compressTiles(gameTiles[i]) | mergeTiles(gameTiles[i])) {
                changes = true;
            }
        }
        isSaveNeeded = true;
        if (changes) {
            addTile();
        }
    }

    public void rotate() {
        for (int i = 0; i < gameTiles.length/2; i++) {
            for (int j = i; j < gameTiles.length - 1 - i; j++) {
                Tile tmp = gameTiles[i][j];
                gameTiles[i][j] = gameTiles[gameTiles.length - j - 1][i];
                gameTiles[gameTiles.length - j - 1][i] = gameTiles[gameTiles.length - i - 1][gameTiles.length - j - 1];
                gameTiles[gameTiles.length - i - 1][gameTiles.length - j - 1] = gameTiles[j][gameTiles.length - i - 1];
                gameTiles[j][gameTiles.length - i - 1] = tmp;
            }
        }
    }

    public void right() {
        saveState(gameTiles);
        rotate();
        rotate();
        left();
        rotate();
        rotate();
    }

    public void up() {
        saveState(gameTiles);
        rotate();
        rotate();
        rotate();
        left();
        rotate();
    }

    public void down() {
        saveState(gameTiles);
        rotate();
        left();
        rotate();
        rotate();
        rotate();
    }

    public void randomMove() {
        int n = ((int) (Math.random() * 100)) % 4;
        switch (n) {
            case 0:
                down();
            case 1:
                up();
            case 2:
                left();
            case 3:
                right();
        }
    }

    public boolean hasBoardChanged() {
        int sumOne = 0;
        int sumTwo = 0;
        if (!previousStates.empty()) {
            Tile[][] prevGameTiles = previousStates.peek();
            for (int i = 0; i < FIELD_WIDTH; i++) {
                for (int j = 0; j < FIELD_WIDTH; j++) {
                    sumOne += gameTiles[i][j].value;
                    sumTwo += prevGameTiles[i][j].value;
                }
            }
        }
        return sumOne != sumTwo;
    }

    public MoveEfficiency getMoveEfficiency(Move move) {
        MoveEfficiency moveEfficiency;
        move.move();
        if (hasBoardChanged()) {
            moveEfficiency = new MoveEfficiency(getEmptyTiles().size(),  score,  move);
        } else {
            moveEfficiency = new MoveEfficiency(- 1, 0, move);
        }
        rollback();
        return moveEfficiency;
    }

    public void autoMove() {
        PriorityQueue<MoveEfficiency> priorityQueue = new PriorityQueue<>(
                4, Collections.reverseOrder());
        priorityQueue.offer(getMoveEfficiency(this::right));
        priorityQueue.offer(getMoveEfficiency(this::left));
        priorityQueue.offer(getMoveEfficiency(this::up));
        priorityQueue.offer(getMoveEfficiency(this::down));
        priorityQueue.peek().getMove().move();
    }
}