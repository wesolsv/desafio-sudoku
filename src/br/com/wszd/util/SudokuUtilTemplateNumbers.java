package br.com.wszd.util;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class SudokuUtilTemplateNumbers {

    private static final int BOARD_SIZE = 9;
    private static final double FIXED_PROBABILITY = 0.45;

    private SudokuUtilTemplateNumbers() {
    }

    public static Stream<String> generatePositions(Random random) {
        int[][] boardPositions = new int[BOARD_SIZE][BOARD_SIZE];

        return IntStream.range(0, BOARD_SIZE * BOARD_SIZE)
                .mapToObj(i -> {
                    int row = i / BOARD_SIZE;
                    int col = i % BOARD_SIZE;

                    Set<Integer> validNumbers = new HashSet<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));

                    for (int j = 0; j < BOARD_SIZE; j++) {
                        validNumbers.remove(boardPositions[row][j]);
                        validNumbers.remove(boardPositions[j][col]);
                    }

                    int startRow = (row / 3) * 3;
                    int startCol = (col / 3) * 3;
                    for (int r = startRow; r < startRow + 3; r++) {
                        for (int c = startCol; c < startCol + 3; c++) {
                            validNumbers.remove(boardPositions[r][c]);
                        }
                    }

                    int num = validNumbers.isEmpty() ? 0 : new ArrayList<>(validNumbers).get(random.nextInt(validNumbers.size()));
                    boolean isFixed = random.nextDouble() < FIXED_PROBABILITY;
                    boardPositions[row][col] = num;

                    return col + "," + row + ";" + num + "," + isFixed;
                });
    }
}

