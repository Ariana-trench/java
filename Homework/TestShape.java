abstract class Shape {
    public abstract double getArea();
}

class Circle extends Shape {
    double r;

    Circle(double r) {
        this.r = r;
    }

    public double getArea() {
        return Math.PI * r * r;
    }
}

class Rectangle extends Shape {
    double h, w;

    Rectangle(double h, double w) {
        this.h = h;
        this.w = w;
    }

    public double getArea() {
        return h * w;
    }
}

class Triangle extends Shape {
    double a, b, c;

    Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public boolean isTriangle() {
        if (a + b + c > 2 * Math.max(a, Math.max(c, b))) {
            return true;
        } else {
            return false;
        }
    }

    public double getArea() {
        if (this.isTriangle()) {
            double p = (a + b + c) / 2;
            return Math.sqrt(p * (p - a) * (p - b) * (p - c));
        }else{
            throw new IllegalArgumentException("" + a + " , " + b + " , " + c + " can't construct a triangle");
        }
    }
}

public class TestShape {
    public static void main(String[] args) {
        try {
            int len = args.length;
            if (len == 1) {
                System.out.println(new Circle(Double.parseDouble(args[0])).getArea());
            } else if (len == 2) {
                System.out.println(new Rectangle(Double.parseDouble(args[0]), Double.parseDouble(args[1])).getArea());
            } else if (len == 3) {
                System.out.println(new Triangle(Double.parseDouble(args[0]), Double.parseDouble(args[1]),
                        Double.parseDouble(args[2])).getArea());
            } else {
                throw new IllegalArgumentException();
            }
        } catch (NumberFormatException nfe) {
            System.out.println("illegal args");
            System.out.println(nfe.getMessage());
        } catch (IllegalArgumentException iae) {
            System.out.println("illegal number of args");
            System.out.println(iae.getMessage());
        } catch (Exception e) {
            System.out.println("error");
            System.out.println(e.getMessage());
        }
    }
}