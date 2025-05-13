import java.util.*;

public class Dijkstra {

    public static int shortestPath(Map<Integer, List<int[]>> graph, int start, int end) {
        Map<Integer, Integer> distances = new HashMap<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        pq.add(new int[]{start, 0});
        distances.put(start, 0);

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int node = current[0];
            int distance = current[1];

            if (node == end) {
                return distance;
            }

            if (!graph.containsKey(node)) continue;

            for (int[] neighbor : graph.get(node)) {
                int neighborNode = neighbor[0];
                int weight = neighbor[1];
                int newDist = distance + weight;

                if (newDist < distances.getOrDefault(neighborNode, Integer.MAX_VALUE)) {
                    distances.put(neighborNode, newDist);
                    pq.add(new int[]{neighborNode, newDist});
                }
            }
        }

        return -1; // No path found
    }


    public static void main(String[] args) {
        Map<Integer, List<int[]>> graph = new HashMap<>();
    
            graph.put(1, Arrays.asList(new int[]{2, 1}, new int[]{5, 1}));
            graph.put(2, Arrays.asList(new int[]{3, 1}));
            graph.put(3, Arrays.asList(new int[]{4, 1}));
            graph.put(4, Arrays.asList(new int[]{5, 1}));
    
            System.out.println(Dijkstra.shortestPath(graph, 1, 2)); // Output: 1
            System.out.println(Dijkstra.shortestPath(graph, 2, 5)); // Output: 3
            System.out.println(Dijkstra.shortestPath(graph, 5, 1)); // Output: -1
        }
    }
    
