package com.nvulinh.chess.engine.pieces;

import com.google.common.collect.ImmutableList;
import com.nvulinh.chess.engine.Alliance;
import com.nvulinh.chess.engine.board.Board;
import com.nvulinh.chess.engine.board.BoardUtils;
import com.nvulinh.chess.engine.board.Move;
import com.nvulinh.chess.engine.board.Move.AttackMove;
import com.nvulinh.chess.engine.board.Move.MajorMove;
import com.nvulinh.chess.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Knight extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

    /**
     *
     * @param piecePosition
     * @param pieceAlliance
     */
    public Knight(final Alliance pieceAlliance, final int piecePosition){
        super(pieceAlliance, piecePosition);
    }

    /**
     * Calculate Legal Move
     * @param board
     * @return
     */
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        List<Move> legalMoves = new ArrayList<>();
        for (final int currentCandidate : CANDIDATE_MOVE_COORDINATES){
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidate;
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){

                if (isFirstColumnExclusion(this.piecePosition, currentCandidate)
                    || isSecondColumnExclusion(this.piecePosition, currentCandidate)
                    || isSeventhColumnExclusion(this.piecePosition, currentCandidate)
                    || isEighthColumnExclusion(this.piecePosition, currentCandidate)){ continue; }

                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if (!candidateDestinationTile.isTileOccupied()){
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                }else{
                    final Piece pieceDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceDestination.getPieceAlliance();
                    if (this.pieceAlliance != pieceAlliance){
                        legalMoves.add(new AttackMove(board,this,candidateDestinationCoordinate,pieceDestination));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    /**
     *
     * @param currentPosition
     * @param candidateOffset
     * @return
     */
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10 ||
                candidateOffset == 6 || candidateOffset == 15);
    }

    /**
     *
     * @param currentPosition
     * @param candidateOffset
     * @return
     */
    private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10 || candidateOffset == 6);
    }

    /**
     *
     * @param currentPosition
     * @param candidateOffset
     * @return
     */
    private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == 10 || candidateOffset == -6);
    }

    /**
     *
     * @param currentPosition
     * @param candidateOffset
     * @return
     */
    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 17 || candidateOffset == 10 ||
                candidateOffset == -6 || candidateOffset == -15);
    }
}
