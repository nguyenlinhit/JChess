package com.nvulinh.chess.engine.board;

import com.nvulinh.chess.engine.pieces.Piece;


public class Move {
    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;

    Move(final Board board,
         final Piece movedPiece,
         final int destinationCoordinate){
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }

    public static final class MajorMove extends Move{

        public MajorMove(final Board board,
                  final Piece movedPiece,
                  final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }

    public static final class AttackMove extends Move{
        final Piece attackPiece;
        public AttackMove(final Board board,
                             final Piece movedPiece,
                             final int destinationCoordinate,
                             final Piece attackPiece) {
            super(board, movedPiece, destinationCoordinate);

            this.attackPiece = attackPiece;
        }
    }
}
