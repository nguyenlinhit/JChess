package com.nvulinh.chess.engine.board;

import com.google.common.collect.ImmutableMap;
import com.nvulinh.chess.engine.pieces.Piece;

import java.util.HashMap;
import java.util.Map;

public abstract class Tile {
    protected final int tileCoordinate;

    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleTiles();

    private static Map<Integer, EmptyTile> createAllPossibleTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for (int i = 0; i < BoardUtils.NUM_TILES; i++){
            emptyTileMap.put(i,new EmptyTile(i));
        }
        return ImmutableMap.copyOf(emptyTileMap);
    }

    private Tile(final int tileCoordinate){
        this.tileCoordinate = tileCoordinate;
    }

    public static Tile createTile(final int tileCoordinate, final Piece piece){
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
    }

    public abstract boolean isTileOccupied();
    public abstract Piece getPiece();

    public final static class EmptyTile extends Tile {
        private EmptyTile(final int tileCoordinate){
            super(tileCoordinate);
        }

        @Override
        public boolean isTileOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }

    public final static class OccupiedTile extends Tile{
        private final Piece pieceOnTile;

        private OccupiedTile(int tileCoordinate,final Piece pieceOnTile){
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }


        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }
    }
}
