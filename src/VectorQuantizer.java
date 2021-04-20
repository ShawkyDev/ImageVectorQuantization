import java.io.File;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class VectorQuantizer {

    static String imgPath = "D:\\pp.jpg";
    static String outPath = "D:\\out1.jpg";

    public static int[][] readImage() throws IOException {
        int[][] pixels;
        File file = new File(imgPath);
        BufferedImage img = ImageIO.read(file);
        int height = img.getHeight();
        int width = img.getWidth();
        pixels = new int[height][width];
        for (int i = 0; i < height; ++i)
            for (int j = 0; j < width; ++j)
                pixels[i][j] = img.getRGB(j, i);
        return pixels;

    }

    public static void writeImage(int[][] imagePixels, String outPath) {
        int H = imagePixels.length;
        int W = imagePixels[0].length;
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                int pix = imagePixels[i][j];
                img.setRGB(j, i, pix);
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

    public static void buildCompressedImage(String[][] codes, ArrayList<PixelMatrix> book) {
        var pixels = new int[codes.length * book.get(0).h][codes[0].length * book.get(0).w];
        int cnt = 0;
        for (int r = 0; r < codes.length; ++r) {
            for (int c = 0; c < codes[r].length; ++c) {

                PixelMatrix tmp = book.get(Integer.parseInt(codes[r][c], 2));
                for (int i = 0; i < tmp.h; ++i) {
                    for (int j = 0; j < tmp.w; ++j) {
                        pixels[i + r * tmp.h][j + c * tmp.w] = tmp.arr[i][j].toInt();
                        cnt++;
                    }
                }
            }
        }
        System.out.println("cnt = " + cnt);
        writeImage(pixels, outPath);
    }


    public static void main(String[] args) {
        try {
            var input = new Scanner(System.in);
            int x=0;
            while(x!=-1) {
                x=input.nextInt();
                var y =input.nextInt();
                outPath = "D:\\" + x + "out" +y+".jpg";
                imgPath = "D:\\shawky.jpg";
                var pixels = readImage();
                var book = CodeBookGenerator.generateCodeBook(pixels, x, x, y);
                var codes = CodeBookGenerator.BuildCompressed(book);
                buildCompressedImage(codes, book);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }


    }
}




