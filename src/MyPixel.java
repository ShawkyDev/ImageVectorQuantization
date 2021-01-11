import java.awt.Color;
import java.util.Objects;

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

    public MyPixel add(MyPixel lhs) {
        MyPixel ret = new MyPixel();
        ret.red = lhs.red + this.red;
        ret.blue = lhs.blue + this.blue;
        ret.green = lhs.green + this.green;
        ret.alpha = lhs.alpha + this.alpha;
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyPixel myPixel = (MyPixel) o;
        return Double.compare(myPixel.red, red) == 0 &&
                Double.compare(myPixel.blue, blue) == 0 &&
                Double.compare(myPixel.green, green) == 0 &&
                Double.compare(myPixel.alpha, alpha) == 0;
    }


    public void addOnME(MyPixel lhs) {
        red += lhs.red;
        blue += lhs.blue;
        green += lhs.green;
        alpha += lhs.alpha;
    }

    public void divideME(double x) throws Exception {
        if (x == 0)
            throw new Exception("diving be zeor!");
        red /= x;
        blue/=x;
        green/=x;
        alpha/=x;
    }
    public int toInt(){
        return ((((int) alpha)) << 24) | ((((int) red)) << 16) | ((((int) green)) << 8) | (int) blue;

    }
    @Override
    public String toString() {
        return "[" +
                "red=" + red +
                ", blue=" + blue +
                ", green=" + green +
                ", alpha=" + alpha +
                ']';
    }
}
