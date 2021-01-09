import java.util.ArrayList;

public class vector {
    private int h, w;
    public double[][] arr;

    public vector(int a, int b) {
        h = a;
        w = b;
        arr = new double[h][w];
    }

    public vector add(vector lhs) throws Exception {
        if (lhs.h != this.h || lhs.w != this.w)
            throw new Exception("can't add two vecotrs with diffrent size");
        vector ret = new vector(h, w);
        for (int i = 0; i < h; ++i)
            for (int j = 0; j < w; ++j)
                ret.arr[i][j] = lhs.arr[i][j] + this.arr[i][j];
        return ret;
    }

    public void addOnME(vector lhs) throws Exception {
        if (lhs.h != this.h || lhs.w != this.w)
            throw new Exception("can't add two vecotrs with diffrent size");
        for (int i = 0; i < h; ++i)
            for (int j = 0; j < w; ++j)
                this.arr[i][j] += lhs.arr[i][j];
    }

    public static vector[][] getVectors(int[][] arr, int h, int w) throws Exception {
        if (arr == null || arr.length % h != 0 || arr[0].length % w != 0)
            throw new Exception("can't construct the array of vectors");
        int H = arr.length / h;
        int W = arr[0].length / w;
        vector[][] ret = new vector[H][W];
        for (int r = 0; r < H; ++r) {
            for (int c = 0; c < W; ++c) {
                vector tmp = new vector(h, w);
                for (int i = 0; i < h; ++i)
                    for (int j = 0; j < w; ++j)
                        tmp.arr[i][j] = arr[i + r * H][j + c * W];
                ret[r][c] = tmp;
            }
        }
        return ret;
    }

    public void divideME(int d) {
        for (int i = 0; i < h; ++i)
            for (int j = 0; j < w; ++j)
                arr[i][j] /= d;
    }

    public static vector getAverage(vector[][] input) throws Exception {
        vector ret = new vector(input[0][0].h, input[0][0].h);
        int cnt = 0;
        for (int i = 0; i < input.length; ++i) {
            for (int j = 0; j < input[i].length; ++j) {
                ret.addOnME(input[i][j]);
                cnt++;
            }
        }
        ret.divideME(cnt);
        return ret;
    }

    public ArrayList<vector> split() {
        vector hi, low;
        hi = new vector(h, w);
        low = new vector(h, w);
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                hi.arr[i][j] = (int) (arr[i][j] + 1);
                low.arr[i][j] = hi.arr[i][j] - 1;
            }
        }
        var ret = new ArrayList<vector>();
        ret.add(hi);
        ret.add(low);
        return ret;
    }

}
