class Hillstations {
    void famousfor() {
        System.out.println("Hill station is famous for its beauty.");
    }

    void famousfood() {
        System.out.println("Hill station has delicious local food.");
    }
}

class Manali extends Hillstations {
    @Override
    void famousfor() {
        System.out.println("Manali is famous for snow mountains and adventure sports.");
    }

    @Override
    void famousfood() {
        System.out.println("Manali is famous for Siddu.");
    }
}

class Mahabaleshwar extends Hillstations {
    @Override
    void famousfor() {
        System.out.println("Mahabaleshwar is famous for strawberries and scenic points.");
    }

    @Override
    void famousfood() {
        System.out.println("Mahabaleshwar is famous for strawberry cream.");
    }
}

class Kashmir extends Hillstations {
    @Override
    void famousfor() {
        System.out.println("Kashmir is famous for natural beauty and valleys.");
    }

    @Override
    void famousfood() {
        System.out.println("Kashmir is famous for Rogan Josh.");
    }
}

public class HillStationDemo {
    public static void main(String[] args) {
        Hillstations var1;

        var1 = new Manali();
        var1.famousfor();
        var1.famousfood();

        System.out.println();

        var1 = new Mahabaleshwar();
        var1.famousfor();
        var1.famousfood();

        System.out.println();

        var1 = new Kashmir();
        var1.famousfor();
        var1.famousfood();
    }
}