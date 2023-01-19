package chess;

public class King extends ChessPiece{

    public King(String color) {
        super(color);
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (Math.abs(line - toLine) <= 1 && Math.abs(column - toColumn) <= 1 &&
            !isUnderAttack(chessBoard, toLine, toColumn) &&
            (chessBoard.board[toLine][toColumn] == null || !color.equals(chessBoard.board[toLine][toColumn].getColor()))) {
                if ((toLine >= 0 && toLine <= 7 && toColumn >= 0 && toColumn <= 7) &&
                        ((line == toLine && column != toColumn) || (line != toLine && column == toColumn))) return true;
                else if ((line != toLine && column != toColumn) && (toLine >= 0 && toLine <= 7 && toColumn >= 0 && toColumn <= 7) &&
                        (Math.abs(line - toLine) == Math.abs(column - toColumn))) return true;
        }
        return false;
    }

    @Override
    public String getSymbol() {
        return "K";
    }

    public boolean isUnderAttack(ChessBoard chessBoard, int line, int column){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (chessBoard.board[i][j] != null &&
                        !(this.color.equals(chessBoard.board[i][j].getColor())) &&
                        chessBoard.board[i][j].canMoveToPosition(chessBoard, i, j, line, column)) return true;
            }
        }
        return false;
    }

}
