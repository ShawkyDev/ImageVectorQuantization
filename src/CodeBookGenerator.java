import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CodeBookGenerator {
    final static int OO = Integer.MAX_VALUE;
    static vector[][] _vectors;
    public static ArrayList<vector> generateCodeBook(int[][] pixels, int h , int w, int cnt) {
        try {
            var vectors = vector.getVectors(pixels, h, w);
            _vectors = vectors;
            var averageVector = vector.getAverage(vectors);
            var curAvgs = new ArrayList<vector>();
            curAvgs.add(averageVector);
            curAvgs = averageVector.split();
            reCalcAvg(curAvgs, vectors);
            while (curAvgs.size() < cnt) {
                System.out.println("cur average = " + curAvgs.size());
                var tmpAvgs = new ArrayList<vector>();
                for (var vec : curAvgs)
                    tmpAvgs.addAll(vec.split());
                reCalcAvg(tmpAvgs, vectors);
                curAvgs = tmpAvgs;
            }
            reCalcAvg(curAvgs,vectors);
            return curAvgs;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return null;
        }

    }

    public static void reCalcAvg(ArrayList<vector> avgs, vector[][] vectors) throws Exception {
        var newAvg = new vector[avgs.size()];
        for (int i = 0; i < avgs.size(); i++)
            newAvg[i] = new vector(avgs.get(0).h, avgs.get(0).w);

        var newAvgCnt = new int[avgs.size()];
        for (int i = 0; i < vectors.length; ++i) {
            for (int j = 0; j < vectors[i].length; ++j) {
                //find the nearest vector
                int bst = OO, bstIdx = -1, idx = 0;
                for (var avg : avgs) {
                    var err = avg.calcError(vectors[i][j]);
                    if (err < bst) {
                        bst = err;
                        bstIdx = idx;
                    }
                    idx++;
                }
                newAvg[bstIdx].addOnME(vectors[i][j]);
                newAvgCnt[bstIdx]++;
            }
        }
        for (int i = 0; i < newAvg.length; ++i) {
            if (newAvgCnt[i] != 0)
                newAvg[i].divideME(newAvgCnt[i]);

        }
        int matches = 0;
        for (var vec : newAvg)
            for(var vec2 : avgs)
                if(vec.equals(vec2)) matches++;

        avgs.clear();
        Collections.addAll(avgs, newAvg);
        if(matches>0)
            reCalcAvg(avgs,vectors);
    }
    public static String[][] BuildCompressed( ArrayList<vector>book) throws Exception{
        var compressed = new String [_vectors.length][];
        for (int i = 0; i < _vectors.length; ++i) {
            compressed[i] = new String [_vectors[i].length];
            for (int j = 0; j < _vectors[i].length; ++j) {
                //find the nearest vector
                int bst = OO, bstIdx = -1, idx = 0;
                for (var avg : book) {
                    var err = avg.calcError(_vectors[i][j]);
                    if (err < bst) {
                        bst = err;
                        bstIdx = idx;
                    }
                    idx++;
                }

                compressed[i][j]=Integer.toBinaryString(bstIdx);
            }
        }
        return compressed;
    }
    public static int[][] generateTest() {
        int[][] arr = {
                {1, 2, 7, 9, 4, 11},
                {3, 4, 6, 6, 12, 12},
                {4, 9, 15, 14, 9, 9},
                {10, 10, 20, 18, 8, 8},
                {4, 3, 17, 16, 1, 4},
                {4, 5, 18, 18, 5, 6}
        };
        return arr;

    }

    public static void main(String[] args) {

        /*var book = generate("");
        System.out.println("printing");
        for (var vec : book)
            vec.print();
*/
    }
}
