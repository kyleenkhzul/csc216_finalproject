This project was done for Professor Vandrevala SP25, Data Structures and Algorithms.

Dijkstra.java is my implementation of the algorithm for the lab. 

Resources:
This geeksforgeeks resource shows the theory and implementation of Dijkstra's algorithm. 
* <https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/> 


Navigation.java is the actual project file for the final project. 

Resources:

These four video resources go in depth about the theory of Dijkstra's Algorithm
* <https://www.youtube.com/watch?v=gQtgtKtvRdo> 
* <https://www.youtube.com/watch?v=O7d-BgIqx-I> 
* <https://www.youtube.com/watch?v=bZkzH5x0SKU> 
* <https://www.youtube.com/watch?v=_lHSawdgXpI> 

This video shows a Leetcode implementation of Dijkstra's Algorithm
* <https://www.youtube.com/watch?v=XEb7_z5dG3c>

TPQs:

1. If I tried to implement a solution using “brute force”, would it work effectively? Why or why not?
    A brute force approach of examining every single path between two landmarks and selecting the one with shortest
    distance or time would not be effective. As the number of landmarks increase, the number of possible paths grows
    factorially due to permutations, making the time complexity O(n!). This becomes literally impossible to run
    efficiently for large graphs. 

2. Did your solution work effectively to solve your problem? Why or why not?
    Yes, the solution works effectively. It correctly calculates the shortest time or path between two landmarks.
    From the book, we know the time complexity is O(( V + E ) logV) using a priority queue. 

3. What are some of the issues with your solution?
    Right now, as implemented, the graph is relatively sparse, which means there are limited paths between most landmarks.
    It can not show alternative paths. Additionally, it doesn't handle dynamic traffic conditions or real-time updates. It 
    assumes undirected roads and static weights which is unrealistic for a city landscape. 

4. What are some of the edge cases with your solution?
    If a user types in a landmark incorrectly, the program will continue to prompt until correct. Landmarks can be added but
    not connected. This leads to no paths to and from. If the landmark is the same start and end, it will trivially return a 
    path. If two landmarks are in completely different subgraphs, no path will be found. 

5. How might you improve your idea in the future?
    a. Add more roads to increase possible routes and enrich the graph.
    b. Add traffic data or real-time updates to weights using an API like Google Maps or Apple Maps.
    c. Provide visualization using a GUI or web interface.
    d. Enable exporting or saving routes as files or JSON. 