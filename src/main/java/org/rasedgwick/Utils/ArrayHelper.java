package org.rasedgwick.Utils;

import org.rasedgwick.algorithms.StringsAndArrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class ArrayHelper {
    static Logger logger = LoggerFactory.getLogger(ArrayHelper.class);

    public static void print2D(int matrix[][])
    {
        // Loop through all rows
        for (int n = 0 ; n < matrix.length ; n++)
        {
            logger.info(Arrays.toString(matrix[n]));
        }
        logger.info("\n");
    }

    public static void pint2D(List<List<Integer>> matrix) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (int row = 0; row < matrix.size(); row++){
            sb.append("[");
            for (int col = 0; col < matrix.size(); col++){
                sb.append(matrix.get(row).get(col));
                sb.append(", ");
            }
            sb.append("]\n");
        }
        sb.append("\n");
        logger.info(sb.toString());
    }

}
