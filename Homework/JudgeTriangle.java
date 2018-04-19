import java.io.*;

class Triangle {
    private double a, b, c;

    Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public boolean isTriangle() {
        if (a + b + c > 2 * Math.max(a, Math.max(c, b))) {
            return true;
        } else {
            throw new IllegalArgumentException("" + a + " , " + b + " , " + c + " can't construct a triangle");
            // return false;
        }
    }
}

public class JudgeTriangle {
    public static void main(String[] args) {
        double a = 2.0;
        double b = 1.0;
        double c = 1.0;
        try {
            Triangle tri = new Triangle(a, b, c);
            if (tri.isTriangle()) {
                System.out.println("" + a + " , " + b + " , " + c + " can construct a triangle");
            }
        } catch (IllegalArgumentException iae) {
            System.out.print(iae.getMessage());
        }
    }
}