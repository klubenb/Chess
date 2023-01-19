package chess;

public class Rook extends ChessPiece{

    public Rook(String color) {
        super(color);
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if ((toLine >= 0 && toLine <= 7 && toColumn >= 0 && toColumn <= 7) &&
                ((line == toLine && column != toColumn) || (line != toLine && column == toColumn)) &&
                isTheStraightWayClear(chessBoard, line, column, toLine, toColumn)) return true;
        return false;
    }

    @Override
    public String getSymbol() {
        return "R";
    }

    protected boolean isTheStraightWayClear(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if ((line == toLine && column != toColumn)){
            for (int i = 1; i < Math.abs(column - toColumn); i++) {
                if (chessBoard.board[line][Math.min(column, toColumn) + i] != null) return  false;
            }
        }else if ((line != toLine && column == toColumn)) {
            for (int i = 1; i < Math.abs(line - toLine); i++) {
                if (chessBoard.board[Math.min(line, toLine) + i][column] != null) return false;
            }
        }
        if (chessBoard.board[toLine][toColumn] != null &&
                color.equals(chessBoard.board[toLine][toColumn].getColor())) return false;
        return true;
    }
}
