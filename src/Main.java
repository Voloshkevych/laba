import parcs.*;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Main implements AM {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.print("class Main start method main\n");
        task curtask = new task();
        curtask.addJarFile("DistanceCalculator.jar"); 
        System.out.print("class Main method main added jars\n");

        (new Main()).run(new AMInfo(curtask, (channel) null));
        System.out.print("class Main method main finished work\n");

        curtask.end();
    }

    @Override
    public void run(AMInfo info) {
        List<Point> points = null;
        try {
            points = readPointsFromFile("input.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.print("class Main: method run: read data from file pointsC:\\Users\\volos\\IdeaProjects\\sorting\\src cd");

        int partitions = 2; //default value
        try {
            BufferedReader reader = new BufferedReader(new FileReader("daemons.txt"));
            partitions = Integer.parseInt(reader.readLine());
            reader.close();
        } catch (IOException e) {
            System.out.println("Error while reading daemons.txt");
            e.printStackTrace();
        }
        System.out.print("class Main: method run: read data from file daemons");
        List<List<Point>> partitionedPoints = partitionList(points, partitions);

        List<point> parcsPoints = new ArrayList<>();
        List<channel> channels = new ArrayList<>();

        for (List<Point> subList : partitionedPoints) {
            parcsPoints.add(info.createPoint());
            channels.add(parcsPoints.get(parcsPoints.size() - 1).createChannel());
            parcsPoints.get(parcsPoints.size() - 1).execute("DistanceCalculator");

            channels.get(channels.size() - 1).write(subList.size());
            for (Point p : subList) {
                channels.get(channels.size() - 1).write(p);
            }
        }

        List<Point> allPoints = new ArrayList<>();
        for (channel ch : channels) {
            int size = ch.readInt();
            for (int i = 0; i < size; i++) {
                allPoints.add((Point) ch.readObject());
            }
        }

        Collections.sort(allPoints, Point::compareTo);
        try {
            writeNamesToFile(allPoints, "output.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<List<Point>> partitionList(List<Point> list, int partitions) {
        int partitionSize = list.size() / partitions;
        List<List<Point>> partitionedList = new ArrayList<>();
        for (int i = 0; i < partitions; i++) {
            partitionedList.add(new ArrayList<>(list.subList(i * partitionSize, (i + 1) * partitionSize)));
        }
        return partitionedList;
    }

    private static List<Point> readPointsFromFile(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        List<Point> points = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            String name = parts[0];
            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);
            points.add(new Point(name, x, y));
        }
        scanner.close();
        return points;
    }

    private static void writeNamesToFile(List<Point> points, String fileName) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File(fileName));
        for (Point p : points) {
            writer.println(p.getName());
        }
        writer.close();
    }
}
