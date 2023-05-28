import java.io.Serializable;

public class Point implements Serializable, Comparable<Point> {
    private String name;
    private double x, y;
    private double distance;

    public Point(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.distance = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void calculateDistance() {
        this.distance = Math.sqrt(this.x * this.x + this.y * this.y);
    }

    @Override
    public int compareTo(Point other) {
        return Double.compare(this.distance, other.distance);
    }
}
