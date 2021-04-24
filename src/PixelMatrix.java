import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class PixelMatrix {
    public int h, w;
    public MyPixel[][] arr;

    public PixelMatrix(int a, int b) {
        h = a;
        w = b;
        arr = new MyPixel[h][w];
        for (int i = 0; i < h; ++i)
            for (int j = 0; j < w; ++j)
                arr[i][j] = new MyPixel(0);
    }

    public PixelMatrix add(PixelMatrix lhs) throws Exception {
        if (lhs.h != this.h || lhs.w != this.w)
            throw new Exception("can't add two Matrices with different size");
        PixelMatrix ret = new PixelMatrix(h, w);
        for (int i = 0; i < h; ++i)
            for (int j = 0; j < w; ++j)
                ret.arr[i][j] = lhs.arr[i][j].add(this.arr[i][j]);
        return ret;
    }

    public void addOnME(PixelMatrix lhs) throws Exception {
        if (lhs.h != this.h || lhs.w != this.w)
            throw new Exception("can't add two Matrices with different size");
        for (int i = 0; i < h; ++i)
            for (int j = 0; j < w; ++j) {

                this.arr[i][j].addOnME(lhs.arr[i][j]);
            }
    }

    public static PixelMatrix[][] getVectors(int[][] _arr, int _h, int _w) throws Exception {
        if (_arr == null || _arr.length / _h == 0 || _arr[0].length / _w == 0)
            throw new Exception("can't construct the array of vectors\n" +
                    "the chosen Height and Width of Vector is too big for the target image");

        int H = _arr.length / _h;
        int W = _arr[0].length / _w;
        PixelMatrix[][] ret = new PixelMatrix[H][W];
        for (int r = 0; r < H; ++r) {
            for (int c = 0; c < W; ++c) {
                PixelMatrix tmp = new PixelMatrix(_h, _w);
                for (int i = 0; i < _h; ++i) {
                    for (int j = 0; j < _w; ++j) {
                        tmp.arr[i][j] = new MyPixel(_arr[i + r * _h][j + c * _w]);
                    }
                }
                ret[r][c] = tmp;
            }
        }
        return ret;
    }

    public void divideME(int d) throws Exception {
        for (int i = 0; i < h; ++i)
            for (int j = 0; j < w; ++j)
                arr[i][j].divideME(d);
    }

    public static PixelMatrix getAverage(PixelMatrix[][] input) throws Exception {
        PixelMatrix ret = new PixelMatrix(input[0][0].h, input[0][0].w);
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

    public static PixelMatrix getAverage(PixelMatrix[] input) throws Exception {
        PixelMatrix ret = new PixelMatrix(input[0].h, input[0].h);
        int cnt = 0;
        for (int i = 0; i < input.length; ++i) {
            ret.addOnME(input[i]);
            cnt++;
        }
        ret.divideME(cnt);
        return ret;
    }

    public int calcError(PixelMatrix lhs) throws Exception {
        if (lhs.h != this.h || lhs.w != this.w)
            throw new Exception("can't add two vecotrs with diffrent size");
        int ret = 0;
        for (int i = 0; i < h; ++i)
            for (int j = 0; j < w; ++j)
                ret += (arr[i][j].red - lhs.arr[i][j].red) * (arr[i][j].red - lhs.arr[i][j].red) +

                        (arr[i][j].blue - lhs.arr[i][j].blue) * (arr[i][j].blue - lhs.arr[i][j].blue) +

                        (arr[i][j].green - lhs.arr[i][j].green) * (arr[i][j].green - lhs.arr[i][j].green) +

                        (arr[i][j].alpha - lhs.arr[i][j].alpha) * (arr[i][j].alpha - lhs.arr[i][j].alpha);

        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PixelMatrix PixelMatrix = (PixelMatrix) o;
        return h == PixelMatrix.h &&
                w == PixelMatrix.w &&
                Arrays.equals(arr, PixelMatrix.arr);
    }


    public ArrayList<PixelMatrix> split() {
        PixelMatrix hi, low;
        hi = new PixelMatrix(h, w);
        low = new PixelMatrix(h, w);
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                hi.arr[i][j].red = (int) (arr[i][j].red + 1);
                low.arr[i][j].red = hi.arr[i][j].red - 1;

                hi.arr[i][j].blue = (int) (arr[i][j].blue + 1);
                low.arr[i][j].blue = hi.arr[i][j].blue - 1;

                hi.arr[i][j].green = (int) (arr[i][j].green + 1);
                low.arr[i][j].green = hi.arr[i][j].green - 1;

                hi.arr[i][j].alpha = (int) (arr[i][j].alpha + 1);
                low.arr[i][j].alpha = hi.arr[i][j].alpha - 1;
            }
        }
        var ret = new ArrayList<PixelMatrix>();
        ret.add(hi);
        ret.add(low);
        return ret;
    }

    public void print() {
        System.out.println("-----------------");
        for (int i = 0; i < h; ++i)
            for (int j = 0; j < w; ++j)
                System.out.print(arr[i][j].toString() + " " + ((j == w - 1) ? "\n" : ","));
    }

}
