package chess;

import java.util.Arrays;

public class ChessBoard {
    public ChessPiece[][] board = new ChessPiece[8][8]; // creating a field for game
    private String nowPlayer;
    private int[] whiteKingCoords;
    private int[] blackKingCoords;



    public ChessBoard(String nowPlayer) {
        this.nowPlayer = nowPlayer;
    }

    public ChessBoard(String nowPlayer, int[] whiteKingCoords, int[] blackKingCoords) {
        this.nowPlayer = nowPlayer;
        this.whiteKingCoords = whiteKingCoords;
        this.blackKingCoords = blackKingCoords;
    }

    public String nowPlayerColor() {
        return this.nowPlayer;
    }

    public void setWhiteKingCoords(int[] whiteKingCoords) {
        if(this.whiteKingCoords == null)
        this.whiteKingCoords = whiteKingCoords;
    }

    public void setBlackKingCoords(int[] blackKingCoords) {
        if(this.blackKingCoords == null)
        this.blackKingCoords = blackKingCoords;
    }

    public boolean moveToPosition(int startLine, int startColumn, int endLine, int endColumn) {
        if (checkPos(startLine) && checkPos(startColumn)) {

            if (!nowPlayer.equals(board[startLine][startColumn].getColor()) || board[startLine][startColumn] == null) return false;

            if (board[startLine][startColumn].canMoveToPosition(this, startLine, startColumn, endLine, endColumn) &&
                    checkChessCheck(startLine, startColumn, endLine, endColumn)) {

                if (board[startLine][startColumn].getSymbol().equals("K") ||  // check position for castling
                        board[startLine][startColumn].getSymbol().equals("R")) {
                    board[startLine][startColumn].check = false;
                }

                if (board[startLine][startColumn].getSymbol().equals("P") &&
                        ((board[startLine][startColumn].getColor().equals("White") && endLine == 7) ||
                            board[startLine][startColumn].getColor().equals("Black") && endLine == 0)){
                    board[endLine][endColumn] = new Queen(this.nowPlayerColor()); // if piece can move, we moved a piece
                }else{
                    board[endLine][endColumn] = board[startLine][startColumn]; // if piece can move, we moved a piece
                }

                if (board[startLine][startColumn].getSymbol().equals("K")){
                    if (board[startLine][startColumn].getColor().equals("White")){
                        whiteKingCoords = new int[]{endLine, endColumn};
                    }else if (board[startLine][startColumn].getColor().equals("Black")){
                        blackKingCoords = new int[]{endLine, endColumn};
                    }
                }
                board[startLine][startColumn] = null; // set null to previous cell
                this.nowPlayer = this.nowPlayerColor().equals("White") ? "Black" : "White";

                return true;
            } else return false;
        } else return false;
    }

    //проверка что при выполеннии хода не будет шаха королю
    private boolean checkChessCheck(int startLine, int startColumn, int endLine, int endColumn){

        ChessBoard checkChessBoard = new ChessBoard(this.nowPlayer, this.whiteKingCoords, this.blackKingCoords);
        checkChessBoard.board = new ChessPiece[this.board.length][this.board.length];

        for (int i = 0; i < this.board.length; i++) {
            System.arraycopy(this.board[i], 0, checkChessBoard.board[i], 0, this.board[i].length);
        }
        checkChessBoard.board[endLine][endColumn] = checkChessBoard.board[startLine][startColumn];
        checkChessBoard.board[startLine][startColumn] = null;
        if (checkChessBoard.nowPlayer.equals("White")){
            if (!new King("White").isUnderAttack(checkChessBoard, whiteKingCoords[0], whiteKingCoords[1])) return true;
        }else {
            if (!new King("Black").isUnderAttack(checkChessBoard, blackKingCoords[0], blackKingCoords[1])) return true;
        }
        System.out.println("Вам шах");
        return false;
    }

    public void printBoard() {  //print board in console
        System.out.println("Turn " + nowPlayer);
        System.out.println();
        System.out.println("Player 2(Black)");
        System.out.println();
        System.out.println("\t0\t1\t2\t3\t4\t5\t6\t7");
        for (int i = 7; i > -1; i--) {
            System.out.print(i + "\t");
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print(".." + "\t");
                } else {
                    System.out.print(board[i][j].getSymbol() + board[i][j].getColor().substring(0, 1).toLowerCase() + "\t");
                }
            }
            System.out.println();
            System.out.println();
        }
        System.out.println("Player 1(White)");
    }

    public boolean checkPos(int pos) {
        return pos >= 0 && pos <= 7;
    }

    public boolean castling0() {
        if (nowPlayer.equals("White")) {
            if (board[0][0] == null || board[0][4] == null) return false;
            if (board[0][0].getSymbol().equals("R") && board[0][4].getSymbol().equals("K") && // check that King and Rook
                    board[0][1] == null && board[0][2] == null && board[0][3] == null) {              // never moved
                if (board[0][0].getColor().equals("White") && board[0][4].getColor().equals("White") &&
                        board[0][0].check && board[0][4].check &&
                        !new King("White").isUnderAttack(this, 0, 2)) { // check that position not in under attack
                    board[0][4] = null;
                    board[0][2] = new King("White");   // move King
                    board[0][2].check = false;
                    board[0][0] = null;
                    board[0][3] = new Rook("White");   // move Rook
                    board[0][3].check = false;
                    nowPlayer = "Black";  // next turn
                    return true;
                } else return false;
            } else return false;
        } else {
            if (board[7][0] == null || board[7][4] == null) return false;
            if (board[7][0].getSymbol().equals("R") && board[7][4].getSymbol().equals("K") && // check that King and Rook
                    board[7][1] == null && board[7][2] == null && board[7][3] == null) {              // never moved
                if (board[7][0].getColor().equals("Black") && board[7][4].getColor().equals("Black") &&
                        board[7][0].check && board[7][4].check &&
                        !new King("Black").isUnderAttack(this, 7, 2)) { // check that position not in under attack
                    board[7][4] = null;
                    board[7][2] = new King("Black");   // move King
                    board[7][2].check = false;
                    board[7][0] = null;
                    board[7][3] = new Rook("Black");   // move Rook
                    board[7][3].check = false;
                    nowPlayer = "White";  // next turn
                    return true;
                } else return false;
            } else return false;
        }
    }

    public boolean castling7() {
        if (nowPlayer.equals("White")) {
            if (board[0][7] == null || board[0][4] == null) return false;
            if (board[0][7].getSymbol().equals("R") && board[0][4].getSymbol().equals("K") && // check that King and Rook
                    board[0][5] == null && board[0][6] == null) {              // never moved
                if (board[0][7].getColor().equals("White") && board[0][4].getColor().equals("White") &&
                        board[0][7].check && board[0][4].check &&
                        !new King("White").isUnderAttack(this, 0, 6)) { // check that position not in under attack
                    board[0][4] = null;
                    board[0][6] = new King("White");   // move King
                    board[0][6].check = false;
                    board[0][7] = null;
                    board[0][5] = new Rook("White");   // move Rook
                    board[0][5].check = false;
                    nowPlayer = "Black";  // next turn
                    return true;
                } else return false;
            } else return false;
        } else {
            if (board[7][7] == null || board[7][4] == null) return false;
            if (board[7][7].getSymbol().equals("R") && board[7][4].getSymbol().equals("K") && // check that King and Rook
                    board[7][5] == null && board[7][6] == null) {              // never moved
                if (board[7][7].getColor().equals("Black") && board[7][4].getColor().equals("Black") &&
                        board[7][7].check && board[7][4].check &&
                        !new King("Black").isUnderAttack(this, 7, 6)) { // check that position not in under attack
                    board[7][4] = null;
                    board[7][6] = new King("Black");   // move King
                    board[7][6].check = false;
                    board[7][7] = null;
                    board[7][5] = new Rook("Black");   // move Rook
                    board[7][5].check = false;
                    nowPlayer = "White";  // next turn
                    return true;
                } else return false;
            } else return false;
        }
    }
}
