import java.io.File;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class VectorQuantizer {
    static double version_number = 1.0;

    public static int[][] readImage(String imgPath) throws IOException {
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

    public static void buildCompressedImage(String[][] codes, ArrayList<PixelMatrix> book, String outPath) {
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
        writeImage(pixels, outPath);
    }
    public static void main(String[] args) {
        try {
            if (args.length < 4) {
                System.err.println("error : too few arguments");
                System.out.println("the command should be:");
                System.out.println("<Image-Path> <Vector-Height> <Vector-Width> <number-of-samples>");
                return;
            }
            int H = Integer.parseInt(args[1]);
            int W = Integer.parseInt(args[2]);
            int BookSize = Integer.parseInt(args[3]);
            var pixels = readImage(args[0]);
            var book = CodeBookGenerator.generateCodeBook(pixels, H, W, BookSize);
            var codes = CodeBookGenerator.Quantize(book);
            buildCompressedImage(codes, book, args[0]+"_out.ong");

        } catch (
                Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}




