import java.util.ArrayList;
import java.util.Collections;

public class CodeBookGenerator {
    final static int OO = Integer.MAX_VALUE;
    static PixelMatrix[][] _pixelMatrices;

    public static ArrayList<PixelMatrix> generateCodeBook(int[][] pixels, int h, int w, int cnt) {
        try {
            var vectors = PixelMatrix.getVectors(pixels, h, w);
            _pixelMatrices = vectors;
            var averageVector = PixelMatrix.getAverage(vectors);
            var currentAverages = new ArrayList<PixelMatrix>();
            currentAverages.add(averageVector);
            currentAverages = averageVector.split();
            reCalcAvg(currentAverages, vectors);
            while (currentAverages.size() < cnt) {
                var tmpAverages = new ArrayList<PixelMatrix>();
                for (var vec : currentAverages)
                    tmpAverages.addAll(vec.split());
                reCalcAvg(tmpAverages, vectors);
                currentAverages = tmpAverages;
            }
            reCalcAvg(currentAverages, vectors);
            return currentAverages;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return null;
        }

    }

    public static void reCalcAvg(ArrayList<PixelMatrix> averages, PixelMatrix[][] PixelMatrices) throws Exception {
        var newAvg = new PixelMatrix[averages.size()];
        for (int i = 0; i < averages.size(); i++)
            newAvg[i] = new PixelMatrix(averages.get(0).h, averages.get(0).w);

        var newAvgCnt = new int[averages.size()];
        for (PixelMatrix[] pixelMatrix : PixelMatrices) {
            for (PixelMatrix matrix : pixelMatrix) {
                //find the nearest matrix
                int bst = OO, bstIdx = -1, idx = 0;
                for (var avg : averages) {
                    var err = avg.calcError(matrix);
                    if (err < bst) {
                        bst = err;
                        bstIdx = idx;
                    }
                    idx++;
                }
                newAvg[bstIdx].addOnME(matrix);
                newAvgCnt[bstIdx]++;
            }
        }
        for (int i = 0; i < newAvg.length; ++i) {
            if (newAvgCnt[i] != 0)
                newAvg[i].divideME(newAvgCnt[i]);

        }
        int matches = 0;
        for (var vec : newAvg)
            for (var vec2 : averages)
                if (vec.equals(vec2)) matches++;

        averages.clear();
        Collections.addAll(averages, newAvg);
        if (matches > 0)
            reCalcAvg(averages, PixelMatrices);
    }

    public static String[][] Quantize(ArrayList<PixelMatrix> book) throws Exception {
        var compressed = new String[_pixelMatrices.length][];
        for (int i = 0; i < _pixelMatrices.length; ++i) {
            compressed[i] = new String[_pixelMatrices[i].length];
            for (int j = 0; j < _pixelMatrices[i].length; ++j) {
                //find the nearest vector
                int bst = OO, bstIdx = -1, idx = 0;
                for (var avg : book) {
                    var err = avg.calcError(_pixelMatrices[i][j]);
                    if (err < bst) {
                        bst = err;
                        bstIdx = idx;
                    }
                    idx++;
                }

                compressed[i][j] = Integer.toBinaryString(bstIdx);
            }
        }
        return compressed;
    }

    public static int[][] generateTest() {
        return new int[][]{
                {1, 2, 7, 9, 4, 11},
                {3, 4, 6, 6, 12, 12},
                {4, 9, 15, 14, 9, 9},
                {10, 10, 20, 18, 8, 8},
                {4, 3, 17, 16, 1, 4},
                {4, 5, 18, 18, 5, 6}
        };

    }

}
