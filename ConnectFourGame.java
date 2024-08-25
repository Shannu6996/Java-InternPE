import java.util.Scanner;

public class ConnectFourGame {
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private static final char EMPTY = ' ';
    private static char[][] board = new char[ROWS][COLUMNS];
    private static char currentPlayer = 'X';

    public static void main(String[] args) {
        initializeBoard();
        boolean gameWon = false;
        int movesCount = 0;

        Scanner scanner = new Scanner(System.in);

        while (!gameWon && movesCount < ROWS * COLUMNS) {
            System.out.println("Current board:");
            printBoard();

            System.out.print("Player " + currentPlayer + ", enter column (1-7): ");
            int column = scanner.nextInt() - 1;

            if (isValidMove(column)) {
                int row = dropDisc(column);
                gameWon = checkWin(row, column);
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                movesCount++;
            } else {
                System.out.println("Invalid move. Please try again.");
            }
        }

        printBoard();

        if (gameWon) {
            System.out.println("Player " + currentPlayer + " wins!");
        } else {
            System.out.println("It's a draw!");
        }

        scanner.close();
    }

    private static void initializeBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    private static void printBoard() {
        for (int i = ROWS - 1; i >= 0; i--) {
            System.out.print("| ");
            for (int j = 0; j < COLUMNS; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
        }
        System.out.println("---------------");
        System.out.println("| 1 | 2 | 3 | 4 | 5 | 6 | 7 |");
        System.out.println();
    }

    private static boolean isValidMove(int column) {
        return column >= 0 && column < COLUMNS && board[ROWS - 1][column] == EMPTY;
    }

    private static int dropDisc(int column) {
        int row = -1;
        for (int i = 0; i < ROWS; i++) {
            if (board[i][column] == EMPTY) {
                board[i][column] = currentPlayer;
                row = i;
                break;
            }
        }
        return row;
    }

    private static boolean checkWin(int row, int column) {
        char player = currentPlayer;

        // Check horizontally
        int count = 0;
        for (int j = 0; j < COLUMNS; j++) {
            if (board[row][j] == player) {
                count++;
                if (count == 4) return true;
            } else {
                count = 0;
            }
        }

        // Check vertically
        count = 0;
        for (int i = 0; i < ROWS; i++) {
            if (board[i][column] == player) {
                count++;
                if (count == 4) return true;
            } else {
                count = 0;
            }
        }

        // Check diagonally (bottom-left to top-right)
        int startOffset1 = Math.min(row, column);
        int startRow1 = row - startOffset1;
        int startCol1 = column - startOffset1;
        count = 0;
        for (int i = startRow1, j = startCol1; i < ROWS && j < COLUMNS; i++, j++) {
            if (board[i][j] == player) {
                count++;
                if (count == 4) return true;
            } else {
                count = 0;
            }
        }

        // Check diagonally (bottom-right to top-left)
        int startOffset2 = Math.min(row, COLUMNS - 1 - column);
        int startRow2 = row - startOffset2;
        int startCol2 = column + startOffset2;
        count = 0;
        for (int i = startRow2, j = startCol2; i < ROWS && j >= 0; i++, j--) {
            if (board[i][j] == player) {
                count++;
                if (count == 4) return true;
            } else {
                count = 0;
            }
        }

        return false;
    }
}
