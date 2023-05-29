import parcs.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main implements AM {

    public static void main(String[] args) {
        System.out.println("Congrats, it is working");
        task curtask = new task();
        curtask.addJarFile("DistanceCalculator.jar");
        (new Main()).run(new AMInfo(curtask, (channel) null));
        System.out.println("Success I guess");
        curtask.end();
    }

    public void run(AMInfo info) {
        System.out.println("Main: method run: beginning");
        int partitions = readDaemonsNumber();
        System.out.println("Beginning to read coordinates from input files");
        double[] xCoordinates = readFromFile("xcoordinate.txt");
        double[] yCoordinates = readFromFile("ycoordinate.txt");
        System.out.println("Successful read");

        System.out.println("Channels say hello there");
        int length = xCoordinates.length / partitions;
        List<channel> channels = new ArrayList<>();
        for (int i = 0; i < partitions; i++) {
            int start = i * length;
            int end = i == partitions - 1 ? xCoordinates.length : start + length;

            double[] xPart = Arrays.copyOfRange(xCoordinates, start, end);
            double[] yPart = Arrays.copyOfRange(yCoordinates, start, end);

            point p = info.createPoint();
            channel c = p.createChannel();
            channels.add(c);
            p.execute("DistanceCalculator");
            c.write(xPart);
            c.write(yPart);

        }
        System.out.println("Channels worked just fine");

        double[] result = new double[xCoordinates.length];
        int index = 0;

        if (channels != null) {
            for (channel ch : channels) {
                System.out.println("Started to copy sorted distances from daemons");
                double[] distances = (double[]) ch.readObject();
                System.out.println(distances.length);
                System.arraycopy(distances, 0, result, index, distances.length);
                index += distances.length;
            }
            System.out.println("Finished copying sorted distances");
        } else {
            System.out.println("No channels available.");
        }


        Arrays.sort(result);
        System.out.println("All distances are sorted");
        for (double distance : result) {
            System.out.println(distance);
        }
        System.out.println("Congrats it worked!");
    }

    private double[] readFromFile(String fileName) {
        List<Double> data = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(Double.parseDouble(line));
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error while reading " + fileName);
            e.printStackTrace();
        }
        return data.stream().mapToDouble(Double::doubleValue).toArray();
    }

    private int readDaemonsNumber() {
        int partitions = 2; //default value
        try {
            BufferedReader reader = new BufferedReader(new FileReader("daemons.txt"));
            partitions = Integer.parseInt(reader.readLine());
            reader.close();
        } catch (IOException e) {
            System.out.println("Error while reading daemons.txt");
            e.printStackTrace();
        }
        return partitions;
    }
}

