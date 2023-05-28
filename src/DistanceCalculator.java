import parcs.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DistanceCalculator implements AM {
    @Override
    public void run(AMInfo info) {
        int size = info.parent.readInt();
        System.out.print("class Algorithm method run read data from parent server");
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            points.add((Point) info.parent.readObject());
        }

        for (Point point : points) {
            point.calculateDistance();
        }

        Collections.sort(points, Point::compareTo);
        info.parent.write(points.size());
        for (Point point : points) {
            info.parent.write(point);
        }
        System.out.println("DistanceCalculator finished");
    }
}







