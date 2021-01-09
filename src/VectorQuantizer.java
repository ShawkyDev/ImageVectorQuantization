import java.io.File;
import java.io.FileWriter;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
public class VectorQuantizer {

    static String imgPath = "D:\\img.jpg";
    public static int[][]  getPixels() throws IOException {
        int[][] pixels;
        File file = new File(imgPath);
        BufferedImage img = ImageIO.read(file);
        int height = img.getHeight();
        int width = img.getWidth();
        pixels = new int[height][width];
        for(int i = 0 ; i < height; ++i)
            for(int j = 0 ; j < width ; ++j)
                pixels[i][j] = img.getRGB(i,j);
     return pixels;
    }

    public static void main(String[] args) {
    }
}
