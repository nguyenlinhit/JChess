package com.nvulinh.chess.engine.pieces;

import com.google.common.collect.ImmutableList;
import com.nvulinh.chess.engine.Alliance;
import com.nvulinh.chess.engine.board.Board;
import com.nvulinh.chess.engine.board.BoardUtils;
import com.nvulinh.chess.engine.board.Move;
import com.nvulinh.chess.engine.board.Tile;
import com.nvulinh.chess.engine.board.Move.MajorMove;
import com.nvulinh.chess.engine.board.Move.AttackMove;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Rook extends Piece{

    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATE = {-8, -1, 1, 8};

    protected Rook(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATE){
            int candidateDestinationCoordinate = this.piecePosition;
            while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                if (isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)
                        || isEightColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)){
                    break;
                }
                candidateDestinationCoordinate += candidateCoordinateOffset;
                if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if (!candidateDestinationTile.isTileOccupied()){
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                    }else{
                        final Piece pieceDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceDestination.getPieceAlliance();
                        if (this.pieceAlliance != pieceAlliance){
                            legalMoves.add(new AttackMove(board,this,candidateDestinationCoordinate,pieceDestination));
                        }
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstColumnExclusion(final  int currentPosition, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1 || candidateOffset == 8);
    }

    private static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -8 || candidateOffset == 1);
    }
}
