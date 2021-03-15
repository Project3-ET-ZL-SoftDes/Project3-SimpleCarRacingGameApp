import javafx.scene.paint.Color;

public class ApplicationTester {

    // testing all content.
    public static void main(String[] args) {

        Engine engine1 = new FiftyCCEngine();
        Engine engine2 = new OneHundredCCEngine();
        Engine engine3 = new OneHundredFiftyCCEngine();

        Engine engine4 = new FiftyCCEngine("Random Name for 50", 12, true);
        Engine engine5 = new OneHundredCCEngine("Random Name for 100", 15, true);
        Engine engine6 = new OneHundredFiftyCCEngine("Random Name for 150", 5, false);

        Transmission tranny1 = new AutomaticTransmission();
        Transmission tranny2 = new ManualTransmission();

        Transmission tranny3 = new AutomaticTransmission("Tranny 3 - Auto", 12.5, 13.7, 4);
        Transmission tranny4 = new ManualTransmission("Tranny 4 - Manual", 25.6, 12.21, 10);

        Transmission tranny5 = new AutomaticTransmission("Tranny 5 - Auto", 19.4, 15.567, 17);
        Transmission tranny6 = new ManualTransmission("Tranny 6 - Manual", 11.2222, 22.22, 110);

        Car car1 = new Car();
        System.out.print(car1);

        Car car2 = new Car(3, 6.5, 3.3, "String1", "String2", Color.GREEN, engine1, tranny1);
        System.out.print(car2);

        Car car3 = new Car(4, 6.6, 3.4, "String3", "String4", Color.GREEN, engine2, tranny2);
        System.out.print(car3);

        Car car4 = new Car(5, 6.7, 3.5, "String5", "String6", Color.GREEN, engine3, tranny3);
        System.out.print(car4);

        Car car5 = new Car(6, 6.8, 3.6, "String7", "String8", Color.GREEN, engine4, tranny4);
        System.out.print(car5);

        Car car6 = new Car(7, 6.9, 3.7, "String9", "String10", Color.GREEN, engine5, tranny5);
        System.out.print(car6);

        Car car7 = new Car(8, 6.10, 3.8, "String11", "String12", Color.GREEN, engine6, tranny6);
        System.out.print(car7);

    }
}
