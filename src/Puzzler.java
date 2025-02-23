import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Puzzler {
    private int N, M;
    private List<List<String>> blocks;
    private char[][] board;
    private int caseCount;

    public Puzzler(int N, int M, List<List<String>> blocks) {
        this.N = N;
        this.M = M;
        this.blocks = blocks;
        this.board = new char[N][M];
        this.caseCount = 0;
        // Initialize the board with empty spaces
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public boolean solve() {
        return solve(0);
    }

    private boolean solve(int blockIndex) {
        caseCount++;
        if (blockIndex == blocks.size()) {
            return isBoardFull();
        }

        List<String> block = blocks.get(blockIndex);
        Set<List<String>> allOrientations = getAllUniqueOrientations(block);

        for (List<String> orientation : allOrientations) {
            for (int i = 0; i <= N - orientation.size(); i++) {
                for (int j = 0; j <= M - orientation.get(0).length(); j++) {
                    if (canPlaceBlock(orientation, i, j)) {
                        placeBlock(orientation, i, j);
                        if (solve(blockIndex + 1)) {
                            return true;
                        }
                        removeBlock(orientation, i, j);
                    }
                }
            }
        }
        return false;
    }

    private Set<List<String>> getAllUniqueOrientations(List<String> block) {
        Set<List<String>> orientations = new HashSet<>();
        orientations.add(block);
        orientations.add(rotate90(block));
        orientations.add(rotate180(block));
        orientations.add(rotate270(block));
        orientations.add(mirrorHorizontal(block));
        orientations.add(mirrorVertical(block));
        orientations.add(mirrorHorizontal(rotate90(block)));
        orientations.add(mirrorVertical(rotate90(block)));
        orientations.add(mirrorHorizontal(rotate180(block)));
        orientations.add(mirrorVertical(rotate180(block)));
        orientations.add(mirrorHorizontal(rotate270(block)));
        orientations.add(mirrorVertical(rotate270(block)));
        return orientations;
    }

    private List<String> rotate90(List<String> block) {
        int rows = block.size();
        int cols = block.get(0).length();
        List<String> rotated = new ArrayList<>();
        for (int j = 0; j < cols; j++) {
            StringBuilder newRow = new StringBuilder();
            for (int i = rows - 1; i >= 0; i--) {
                if (j < block.get(i).length()) {
                    newRow.append(block.get(i).charAt(j));
                } else {
                    newRow.append(' ');
                }
            }
            rotated.add(newRow.toString());
        }
        return rotated;
    }

    private List<String> rotate180(List<String> block) {
        return rotate90(rotate90(block));
    }

    private List<String> rotate270(List<String> block) {
        return rotate90(rotate180(block));
    }

    private List<String> mirrorHorizontal(List<String> block) {
        List<String> mirrored = new ArrayList<>();
        for (String row : block) {
            mirrored.add(new StringBuilder(row).reverse().toString());
        }
        return mirrored;
    }

    private List<String> mirrorVertical(List<String> block) {
        List<String> mirrored = new ArrayList<>(block);
        for (int i = 0; i < block.size() / 2; i++) {
            String temp = mirrored.get(i);
            mirrored.set(i, mirrored.get(block.size() - 1 - i));
            mirrored.set(block.size() - 1 - i, temp);
        }
        return mirrored;
    }

    private boolean canPlaceBlock(List<String> block, int row, int col) {
        for (int i = 0; i < block.size(); i++) {
            for (int j = 0; j < block.get(i).length(); j++) {
                if (block.get(i).charAt(j) != ' ' && (row + i >= N || col + j >= M || board[row + i][col + j] != ' ')) {
                    return false;
                }
            }
        }
        return true;
    }

    private void placeBlock(List<String> block, int row, int col) {
        for (int i = 0; i < block.size(); i++) {
            for (int j = 0; j < block.get(i).length(); j++) {
                if (block.get(i).charAt(j) != ' ') {
                    board[row + i][col + j] = block.get(i).charAt(j);
                }
            }
        }
    }

    private void removeBlock(List<String> block, int row, int col) {
        for (int i = 0; i < block.size(); i++) {
            for (int j = 0; j < block.get(i).length(); j++) {
                if (block.get(i).charAt(j) != ' ') {
                    board[row + i][col + j] = ' ';
                }
            }
        }
    }

    private boolean isBoardFull() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                char c = board[i][j];
                System.out.print(Color.getColor(c) + c + Color.reset());
            }
            System.out.println();
        }
    }

    public char[][] getBoard() {
        return board;
    }

    public int getCaseCount() {
        return caseCount;
    }
}