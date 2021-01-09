import java.util.ArrayList;

public class CodeBookGenerator {
    public static ArrayList<vector> generate(String path) {
        try {
            VectorQuantizer.imgPath = path;
            var pixels = VectorQuantizer.getPixels();
            var vectors = vector.getVectors(pixels,10,10);
            var averageVector = vector.getAverage(vectors);


        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }


    }
}
