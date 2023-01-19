package chess;

public class Pawn extends ChessPiece {

    public Pawn(String color) {
        super(color);
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if((line != toLine && column == toColumn) &&
            (toLine >= 0 && toLine <= 7 && toColumn >= 0 && toColumn <= 7)){
                if (color.equals("White")){
                    if ((line == 1 && toLine - line == 2) &&
                            chessBoard.board[line + 1][column] == null &&
                            chessBoard.board[toLine][toColumn] == null) return true;
                    else if (toLine - line == 1 &&
                            chessBoard.board[toLine][toColumn] == null) return true;
                }else {
                    if ((line == 6 && line - toLine == 2) || line - toLine == 1 &&
                        chessBoard.board[line - 1][column] == null &&
                        chessBoard.board[toLine][toColumn] == null) return true;
                    else if (toLine - line == 1 &&
                            chessBoard.board[toLine][toColumn] == null) return true;
                }
        }else if(Math.abs(line - toLine) == 1 && Math.abs(column - toColumn) == 1 ){
            // атака пешки
            if (color.equals("White")){
                if (toLine - line == 1 &&
                    (chessBoard.board[toLine][toColumn] != null && !color.equals(chessBoard.board[toLine][toColumn].getColor()))) return true;
            }else {
                if (line - toLine == 1 &&
                        (chessBoard.board[toLine][toColumn] != null && !color.equals(chessBoard.board[toLine][toColumn].getColor()))) return true;
            }
        }
        return false;
    }

    @Override
    public String getSymbol() {
        return "P";
    }

}
