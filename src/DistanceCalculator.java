import parcs.*;

import java.util.Arrays;

public class DistanceCalculator implements AM {

    @Override
    public void run(AMInfo info) {
        System.out.println("Hello there from Daemon");
        System.out.println("Starting to read coordinates");
        double[] xCoordinates = (double[]) info.parent.readObject();
        double[] yCoordinates = (double[]) info.parent.readObject();

        System.out.println("Finished reading coordinates");
        double[] distances = new double[xCoordinates.length];
        for (int i = 0; i < xCoordinates.length; i++) {
            distances[i] = calculateDistance(xCoordinates[i], yCoordinates[i]);
        }

        Arrays.sort(distances);
        System.out.println("Daemon sorting passed well");

        info.parent.write(distances);
    }

    private double calculateDistance(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }
}








