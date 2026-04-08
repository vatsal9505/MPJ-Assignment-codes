class Shapes {
    double length, breadth, radius, side;

    Shapes() {
        length = breadth = radius = side = 0;
    }

    Shapes(double length, double breadth) {
        this.length = length;
        this.breadth = breadth;
    }

    Shapes(double radius) {
        this.radius = radius;
    }

    Shapes(double side, int x) {
        this.side = side;
    }

    double area(double length, double breadth) {
        return length * breadth;
    }

    double area(double radius) {
        return 3.14 * radius * radius; 
    }

    double area(double side, int x) {
        return side * side; 
    }

    public static void main(String[] args) {
        Shapes rectangle = new Shapes(10, 5);
        Shapes circle = new Shapes(7);
        Shapes square = new Shapes(4, 1);

        System.out.println("Area of Rectangle = " + rectangle.area(10, 5));
        System.out.println("Area of Circle = " + circle.area(7));
        System.out.println("Area of Square = " + square.area(4, 1));
    }
}