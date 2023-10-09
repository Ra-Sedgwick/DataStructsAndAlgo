import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestUtils {

    public static boolean areMatricesEqual(int[][] a, int[][] b) {
        for (int i = 0; i < a.length; i++) {
            if (!Arrays.equals(a[i], b[i])) {
                return false;
            }
        }
        return true;
    }

    public static List<int[][]> getMatrix() {
        int[][] inputMatrix = {
                {0, 1, 2, 3, 4},
                {5, 6, 7, 8, 9},
                {10, 11, 12, 13, 14},
                {15, 16, 17, 18, 19},
                {20, 21, 22, 23, 24}
        };

        int[][] outputMatrix = {
                {20, 15, 10, 5, 0},
                {21, 16, 11, 6, 1},
                {22, 17, 12, 7, 2},
                {23, 18, 13, 8, 3},
                {24, 19, 14, 9, 4}
        };

        return new ArrayList<>() {{
            add(inputMatrix);
            add(outputMatrix);
        }};
    }

    public static List<int[][]> getZeroArray() {
        int[][] inputMatrix = {
                {0, 1, 2, 3},
                {5, 6, 7, 8, 9},
                {1},
                {0},
                {1, 2, 3, 4, 0}
        };

        int[][] outputMatrix = {
                {0, 0, 0, 0},
                {5, 6, 7, 8, 9},
                {1},
                {0},
                {0, 0, 0, 0, 0}
        };

        return new ArrayList<>() {{
            add(inputMatrix);
            add(outputMatrix);
        }};
    }

    public static List<int[][]> getZeroArrayRowAndCol() {
        int[][] inputMatrix = {
                {0, 1, 2, 3},
                {5, 6, 7, 8, 9},
                {1, 0, 2, 3, 4, 5},
                {1, 2, 3,},
                {1, 2, 3, 4, 0}
        };

        int[][] outputMatrix = {
                {0, 0, 0, 0},
                {0, 0, 7, 8, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 3,},
                {0, 0, 0, 0, 0}
        };

        return new ArrayList<>() {{
            add(inputMatrix);
            add(outputMatrix);
        }};
    }

    public static List<List<Integer>> getHourGlassMatrix() {
        List<List<Integer>> inputMatrix = new ArrayList<>();
        Integer[] a1;
        a1 = new Integer[]{-9, -9, -9, 1, 1, 1};
        inputMatrix.add(Arrays.stream(a1).toList());

        a1 = new Integer[]{ 0, -9,  0,  4, 3, 2};
        inputMatrix.add(Arrays.stream(a1).toList());

        a1 = new Integer[]{-9, -9, -9,  1, 2, 3};
        inputMatrix.add(Arrays.stream(a1).toList());

        a1 = new Integer[]{0,  0,  8, 6, 6, 0};
        inputMatrix.add(Arrays.stream(a1).toList());

        a1 = new Integer[]{0,  0,  0, -2, 0, 0};
        inputMatrix.add(Arrays.stream(a1).toList());

        a1 = new Integer[]{0,  0,  1,  2, 4, 0};
        inputMatrix.add(Arrays.stream(a1).toList());

        return inputMatrix;
    }

    public static List<List<Integer>> getHourGlassMatrix3() {
        List<List<Integer>> inputMatrix = new ArrayList<>();
        Integer[] a1;
        a1 = new Integer[]{-1, -1, 0, -9, -2, -2};
        inputMatrix.add(Arrays.stream(a1).toList());

        a1 = new Integer[]{-2, -1, -6, -8, -2, -5};
        inputMatrix.add(Arrays.stream(a1).toList());

        a1 = new Integer[]{-1, -1, -1, -2, -3, -4};
        inputMatrix.add(Arrays.stream(a1).toList());

        a1 = new Integer[]{-1, -9, -2, -4, -4, -5};
        inputMatrix.add(Arrays.stream(a1).toList());

        a1 = new Integer[]{-7, -3, -3, -2, -9, -9};
        inputMatrix.add(Arrays.stream(a1).toList());

        a1 = new Integer[]{-1, -3, -1, -2, -4, -5};
        inputMatrix.add(Arrays.stream(a1).toList());

        return inputMatrix;
    }







}
