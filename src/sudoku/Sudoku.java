package sudoku;

import sac.graph.GraphState;
import sac.graph.GraphStateImpl;

import java.util.List;

public class Sudoku extends GraphStateImpl {
    public static final int n = 3;
    public static final int n2 = n * n;
    private byte[][] board;
    private int zeros = n2 * n2;

    public Sudoku() {
        board = new byte[n2][n2];
        for (int i = 0; i < n2; i++) {
            for (int j = 0; j < n2; j++) {
                board[i][j] = 0;
            }
        }
    }

    public Sudoku(Sudoku toCopy) {
        board = new byte[n2][n2];
        for (int i = 0; i < n2; i++) {
            for (int j = 0; j < n2; j++) {
                board[i][j] = toCopy.board[i][j];
            }
        }
        zeros = toCopy.zeros;
    }

    private void refreshZeros() {
        zeros = 0;
        for (int i = 0; i < n2; i++) {
            for (int j = 0; j < n2; j++) {
                if (board[i][j] == 0) {
                    zeros++;
                }
            }
        }
    }

    @Override
    public String toString() {
        String txt = "";
        for (int i = 0; i < n2; i++) {
            System.out.println();
            for (int j = 0; j < n2; j++) {
                if (j == n2 - 1) {
                    txt += board[i][j];
                } else {
                    txt += board[i][j] + ",";
                }
            }
            txt += "\n";
        }
        return txt;
    }

    public void fromStringN3(String sudokuAsString) {
        int index = 0;
        for (int i = 0; i < n2; i++) {
            for (int j = 0; j < n2; j++) {
                board[i][j] = Byte.valueOf(sudokuAsString.substring(index, index + 1));
                index++;
            }
        }
        refreshZeros();
    }

    private boolean isGroupLegal(byte[] group) {
        boolean[] visited = new boolean[n2 + 1];

        for (int i = 1; i < n2 + 1; i++) {
            visited[i] = false;
        }

        for (int i = 0; i < n2; i++) {
            if (group[i] == 0) {
                continue;
            }
            if (visited[group[i]]) {
                return false;
            }
            visited[group[i]] = true;
        }
        return true;
    }

    public boolean isLegal() {
        byte[] group = new byte[n2];

        // rows
        for (int i = 0; i < n2; i++) {
            for (int j = 0; j < n2; j++) {
                group[j] = board[i][j];
            }
            if (!isGroupLegal(group)) {
                return false;
            }
        }
        // columns
        for (int i = 0; i < n2; i++) {
            for (int j = 0; j < n2; j++) {
                group[j] = board[j][i];
            }
            if (!isGroupLegal(group)) {
                return false;
            }
        }
        // squeres
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                int z = 0;
                for (int k = 0; k < n; k++)
                    for (int l = 0; l < n; l++)
                        group[z++] = board[i * n + k][j * n + l];
                if (!isGroupLegal(group)) {
                    return false;
                }

            }

        return true;
    }

    @Override
    public List<GraphState> generateChildren() {
        return null;
    }

    @Override
    public boolean isSolution() {
        return (isLegal() && (zeros == 0));
    }

    public static void main(String[] args) {
        Sudoku sudoku = new Sudoku();
        String sudokuAsString = "200080300060070084030500209000105408000000000402706000301007040720040060004010003";
        sudoku.fromStringN3(sudokuAsString);
        System.out.println(sudoku.toString());
        System.out.println(sudoku.isLegal());
        sudoku.refreshZeros();
        System.out.println("zeros: " + sudoku.zeros);
    }
}
