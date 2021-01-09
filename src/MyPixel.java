import java.awt.Color;

public class MyPixel {
    public double red;
    public double blue;
    public double green;
    public double alpha;

    public MyPixel() {
        red = green = blue = alpha = 0;
    }

    public MyPixel(int pix) {
        Color col = new Color(pix, true);
        red = col.getRed();
        blue = col.getBlue();
        green = col.getGreen();
        alpha = col.getAlpha();
    }

    public  MyPixel add(MyPixel lhs) {
        MyPixel ret = new MyPixel();
        ret.red = lhs.red + this.red;
        ret.blue = lhs.blue + this.blue;
        ret.green = lhs.green + this.green;
        ret.alpha = lhs.alpha + this.alpha;
        return ret;
    }

    public void addOnME(MyPixel lhs) {
        red += lhs.red;
        blue+=lhs.blue;
        green+=lhs.green;
        alpha+=lhs.alpha;
    }
    public void divideME(double x){
        red /= x;
        blue/=x;
        green/=x;
        alpha/=x;
    }

}
