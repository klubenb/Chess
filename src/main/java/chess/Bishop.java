package chess;

public class Bishop extends ChessPiece{

    public Bishop(String color) {
        super(color);
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if ((line != toLine && column != toColumn) &&
                (toLine >= 0 && toLine <= 7 && toColumn >= 0 && toColumn <= 7) &&
                (Math.abs(line - toLine) == Math.abs(column - toColumn)) &&
                isTheDiagonalWayClear(chessBoard, line, column, toLine, toColumn)) return true;
        return false;
    }

    @Override
    public String getSymbol() {
        return "B";
    }

    protected boolean isTheDiagonalWayClear(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (line - toLine == column - toColumn){
            for (int i = 1; i < Math.abs(line - toLine); i++) {
                if (chessBoard.board[Math.min(line, toLine) + i][Math.min(column, toColumn) + i] != null) return  false;
            }
        }else{
            for (int i = 1; i < Math.abs(line - toLine); i++) {
                if (chessBoard.board[Math.min(line, toLine) + i][Math.max(column, toColumn) - i] != null) return  false;
            }
        }
        if (chessBoard.board[toLine][toColumn] != null &&
            color.equals(chessBoard.board[toLine][toColumn].getColor())) return false;
        return true;
    }
}
