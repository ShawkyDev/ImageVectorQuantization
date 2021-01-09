import java.io.File;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class VectorQuantizer {

    static String imgPath = "D:\\img.jpg";

    public static int[][] readImage() throws IOException {
        int[][] pixels;
        File file = new File(imgPath);
        BufferedImage img = ImageIO.read(file);
        int height = img.getHeight();
        int width = img.getWidth();
        pixels = new int[height][width];
        for (int i = 0; i < height; ++i)
            for (int j = 0; j < width; ++j)
                pixels[i][j] = img.getRGB(i, j);
        return pixels;

    }

    public static void writeImage(int[][] imagePixels, String outPath) {
        int H = imagePixels.length;
        int W = imagePixels[0].length;
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_3BYTE_BGR);

        for (int x = 0; x < W; x++) {
            for (int y = 0; y < H; y++) {
                int pix = imagePixels[x][y];
                img.setRGB(x, y, pix);

            }
        }

        File f = new File(outPath);

        try {
            ImageIO.write(img, "jpg", f);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void buildCompressedImage(String[][] codes, ArrayList<vector> book) {
        var pixels = new int[codes.length * book.get(0).h][codes[0].length * book.get(0).w];
        int cnt = 0;
        for (int r = 0; r < codes.length; ++r) {
            for (int c = 0; c < codes[r].length; ++c) {

                vector tmp = book.get(Integer.parseInt(codes[r][c], 2));
                for (int i = 0; i < tmp.h; ++i) {
                    for (int j = 0; j < tmp.w; ++j) {
                        pixels[i + r * tmp.h][j + c * tmp.w] = tmp.arr[i][j].toInt();
                        cnt++;
                    }
                }
            }
        }
        System.out.println("cnt = " + cnt);
        writeImage(pixels, "D:\\newimg.jpg");
    }


    public static void main(String[] args) {
        try {
            var pixels = readImage();
            var book = CodeBookGenerator.generateCodeBook(pixels, 4, 4, 8);
            var codes = CodeBookGenerator.BuildCompressed(book);
            buildCompressedImage(codes, book);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }


    }
}




