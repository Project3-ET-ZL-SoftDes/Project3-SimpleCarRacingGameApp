import java.util.Map;

// testing all content.
public class RaceVenueTestHarness {

    public static void main(String[] args) {

        RaceVenue mainStrip = new MainStripVenue();
        RaceVenue pistonCup = new PistonCupVenue();
        RaceVenue tropicalIsland = new TropicalIslandVenue();

        mainStrip.determineCarPosition();
        pistonCup.determineCarPosition();
        tropicalIsland.determineCarPosition();

        for(Map.Entry<Character, Double> paths : mainStrip.getAvailablePaths().entrySet()) {
            System.out.println("KEY=" + paths.getKey() + ", VALUE=" + paths.getValue());
        }

        System.out.println();

        for(Map.Entry<Character, Double> paths : pistonCup.getAvailablePaths().entrySet()) {
            System.out.println("KEY=" + paths.getKey() + ", VALUE=" + paths.getValue());
        }

        System.out.println();

        for(Map.Entry<Character, Double> paths : tropicalIsland.getAvailablePaths().entrySet()) {
            System.out.println("KEY=" + paths.getKey() + ", VALUE=" + paths.getValue());
        }

        System.out.print(mainStrip);
        System.out.print(pistonCup);
        System.out.print(tropicalIsland);

    }

}
