import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

class Point {
    double x, y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

public class ClosestPair {

    // Calculate the Euclidean distance between two points
    private static double distance(Point p1, Point p2) {
        double dx = p2.x - p1.x;
        double dy = p2.y - p1.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    // Brute-force algorithm to find the closest pair of points
    private static double bruteForceClosestPair(List<Point> points, int start, int end) {
    	long startBrute = System.nanoTime();
        double minDistance = Double.MAX_VALUE;
        for (int i = start; i <= end; i++) {
            for (int j = i + 1; j <= end; j++) {
                double distance = distance(points.get(i), points.get(j));
                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
        }
        long endBrute = System.nanoTime();
        System.out.println("Time Difference for Brute Force is " + (endBrute - startBrute) + " ns\n");
        return minDistance;
    }

    // Find the closest pair of points in the strip region
    private static double stripClosest(List<Point> strip, double minDistance) {
        double min = minDistance;
        Collections.sort(strip, Comparator.comparingDouble(point -> point.y));
        int size = strip.size();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size && (strip.get(j).y - strip.get(i).y) < min; j++) {
                double distance = distance(strip.get(i), strip.get(j));
                if (distance < min) {
                    min = distance;
                }
            }
        }
        return min;
    }

    // Divide-and-conquer algorithm to find the closest pair of points
    private static double closestPairUtil(List<Point> points, int start, int end) {
    	long startDC = System.nanoTime();
        if (end - start + 1 <= 3) {
            return bruteForceClosestPair(points, start, end);
        }

        int mid = (start + end) / 2;
        Point midPoint = points.get(mid);

        double leftMinDist = closestPairUtil(points, start, mid);
        double rightMinDist = closestPairUtil(points, mid + 1, end);

        double minDist = Math.min(leftMinDist, rightMinDist);

        List<Point> strip = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (Math.abs(points.get(i).x - midPoint.x) < minDist) {
                strip.add(points.get(i));
            }
        }

        double stripMinDist = stripClosest(strip, minDist);
        
        long endDC = System.nanoTime();
        System.out.println("The Difference for Divide and Conquer " + (endDC - startDC + " ns" + "\n"));
        return Math.min(minDist, stripMinDist);
    }

    // Find the closest pair of points using both divide-and-conquer and brute-force approaches
    public static void closestPair(List<Point> points) {
        // Sort the points by their x-coordinate
        Collections.sort(points, Comparator.comparingDouble(point -> point.x));

        // Find the closest pair using divide-and-conquer approach
        double minDistance = closestPairUtil(points, 0, points.size() - 1);
        
         // Find the closest pair using brute-force approach
        double bruteForceMinDistance = bruteForceClosestPair(points, 0, points.size() - 1);
            
        // Format the output with decimal precision
        DecimalFormat decimalFormat = new DecimalFormat("#.####");

        System.out.println("Closest Pair (Divide and Conquer): ");
        System.out.println("Point 1: (" + decimalFormat.format(points.get(0).x) + ", " +
                decimalFormat.format(points.get(0).y) + ")");
        System.out.println("Point 2: (" + decimalFormat.format(points.get(1).x) + ", " +
                decimalFormat.format(points.get(1).y) + ")");
        System.out.println("Distance: " + decimalFormat.format(minDistance));
        System.out.println("\n");
        
        System.out.println("Closest Pair (Brute Force): ");
        System.out.println("Point 1: (" + decimalFormat.format(points.get(0).x) + ", " +
                decimalFormat.format(points.get(0).y) + ")");
        System.out.println("Point 2: (" + decimalFormat.format(points.get(1).x) + ", " +
                decimalFormat.format(points.get(1).y) + ")");
        System.out.println("Distance: " + decimalFormat.format(bruteForceMinDistance));
        
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of points (N): ");
        int N = scanner.nextInt();
        scanner.close();

        // Generate random points
        List<Point> points = generateRandomPoints(N);
        // Save points to a file
        savePointsToFile(points, "input.txt");
        // Find the closest pair of points
        closestPair(points);
    }

    // Generate random points
    private static List<Point> generateRandomPoints(int N) {
        List<Point> points = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < N; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();
            points.add(new Point(x, y));
        }

        return points;
    }

    // Save points to a file
    private static void savePointsToFile(List<Point> points, String filename) {
    	File file = new File(filename);
        try (PrintWriter writer = new PrintWriter(file)) {
            DecimalFormat decimalFormat = new DecimalFormat("#.####");
            for (Point point : points) {
                writer.println(decimalFormat.format(point.x) + "," + decimalFormat.format(point.y));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


