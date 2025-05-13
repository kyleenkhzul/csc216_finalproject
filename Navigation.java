import java.util.*;

class Landmark {
    String name;

    Landmark(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Landmark landmark = (Landmark) o;
        return Objects.equals(name, landmark.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}

class Road {
    Landmark destination;
    double distance;
    double time;

    Road(Landmark destination, double distance, double time) {
        this.destination = destination;
        this.distance = distance;
        this.time = time;
    }
}

class Graph {
    Map<Landmark, List<Road>> map = new HashMap<>();

    void addLandmark(String name) {
        map.putIfAbsent(new Landmark(name), new ArrayList<>());
    }

    void addRoad(String from, String to, double distance, double time) {
        Landmark fromL = getLandmark(from);
        Landmark toL = getLandmark(to);
        map.get(fromL).add(new Road(toL, distance, time));
        map.get(toL).add(new Road(fromL, distance, time)); // Assume undirected
    }

    Landmark getLandmark(String name) {
        return map.keySet().stream().filter(l -> l.name.equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    List<Landmark> getLandmarks() {
        return new ArrayList<>(map.keySet());
    }
}

class Dijkstra {
    static class PathNode implements Comparable<PathNode> {
        Landmark landmark;
        double totalWeight;
        List<Landmark> path;

        PathNode(Landmark landmark, double totalWeight, List<Landmark> path) {
            this.landmark = landmark;
            this.totalWeight = totalWeight;
            this.path = path;
        }

        public int compareTo(PathNode other) {
            return Double.compare(this.totalWeight, other.totalWeight);
        }
    }

    static PathNode findShortestPath(Graph graph, String startName, String endName, boolean useTime) {
        Landmark start = graph.getLandmark(startName);
        Landmark end = graph.getLandmark(endName);

        if (start == null || end == null) return null;

        PriorityQueue<PathNode> queue = new PriorityQueue<>();
        Set<Landmark> visited = new HashSet<>();
        queue.add(new PathNode(start, 0, new ArrayList<>(List.of(start))));

        while (!queue.isEmpty()) {
            PathNode current = queue.poll();
            if (visited.contains(current.landmark)) continue;
            if (current.landmark.equals(end)) return current;

            visited.add(current.landmark);
            for (Road road : graph.map.get(current.landmark)) {
                if (!visited.contains(road.destination)) {
                    double weight = useTime ? road.time : road.distance;
                    List<Landmark> newPath = new ArrayList<>(current.path);
                    newPath.add(road.destination);
                    queue.add(new PathNode(road.destination, current.totalWeight + weight, newPath));
                }
            }
        }
        return null;
    }
}

public class Navigation {
    public static void main(String[] args) {
        Graph graph = new Graph();

        // Landmarks
        String[] landmarks = {
            "Millennium Park", "Navy Pier", "Willis Tower", "Art Institute",
            "Museum of Science and Industry", "Lincoln Park Zoo", "Wrigley Field", "Shedd Aquarium",
            "China Town"
        };
        for (String lm : landmarks) graph.addLandmark(lm);

        // Roads
        graph.addRoad("Millennium Park", "Art Institute", 0.2, 2);
        graph.addRoad("Millennium Park", "Navy Pier", 1.2, 6);
        graph.addRoad("Millennium Park", "Shedd Aquarium", 1.5, 7);
        graph.addRoad("Art Institute", "Willis Tower", 0.9, 5);
        graph.addRoad("Willis Tower", "Lincoln Park Zoo", 3.1, 10);
        graph.addRoad("Lincoln Park Zoo", "Wrigley Field", 2.5, 9);
        graph.addRoad("Museum of Science and Industry", "Shedd Aquarium", 3.0, 12);
        graph.addRoad("Shedd Aquarium", "Navy Pier", 1.7, 8);
        graph.addRoad("Willis Tower", "China Town", 1.3, 5);
        graph.addRoad("China Town", "Museum of Science and Industry", 2.4, 9);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Available Landmarks: " + Arrays.toString(landmarks));

        String startName;
        Landmark start;
        do {
            System.out.print("Enter starting landmark: ");
            startName = scanner.nextLine();
            start = graph.getLandmark(startName);
            if (start == null) System.out.println("Invalid landmark. Please check spelling.");
        } while (start == null);

        String endName;
        Landmark end;
        do {
            System.out.print("Enter destination landmark: ");
            endName = scanner.nextLine();
            end = graph.getLandmark(endName);
            if (end == null) System.out.println("Invalid landmark. Please check spelling.");
        } while (end == null);

        scanner.close();
        Dijkstra.PathNode shortestDistancePath = Dijkstra.findShortestPath(graph, startName, endName, false);
        Dijkstra.PathNode shortestTimePath = Dijkstra.findShortestPath(graph, startName, endName, true);

        if (shortestDistancePath != null) {
            System.out.println("\nShortest Distance Path:");
            System.out.println("Path: " + shortestDistancePath.path);
            System.out.printf("Total Distance: %.2f miles\n", shortestDistancePath.totalWeight);
        }

        if (shortestTimePath != null) {
            System.out.println("\nShortest Time Path:");
            System.out.println("Path: " + shortestTimePath.path);
            System.out.printf("Estimated Time: %.2f minutes\n", shortestTimePath.totalWeight);
        }
    }
}