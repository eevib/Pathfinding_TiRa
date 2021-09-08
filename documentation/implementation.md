# Implementation

Both algorithms are implemented in their own classes. There is a class node which is the node Dijkstra uses. JPS have its own node JPSNode. This is because both algorithms have different needs for the node. 

Both algorithms get a made graph, with start and end points marked.

A map is given from a file, which creates a graph of it and then the graph is given to Dijkstra and JPS. 

### Dijkstra
At the moment Dijkstra uses two dimensional double graphs to keep track of distances to start node and keeping track of nodes visited. Moreover a priority queue is used as a heap to go through all needed nodes. Moreover a arrayList is used to save the route and drawing it. 

### JPS
JPS have a two dimensional JPSNode list called nodes which saves the nodes with shortest distance from start. 
A JPSNode priority queue is a open list of successor nodes and a two dimensional Boolean list is used to keep track of which nodes are on the openList. The route is saved as a JPSNode ArrayList. The map is given as a Graph graph. 

### Graph
The ASCII map is made to a graph, which is a two dimensional char list.

### Map 
Map takes form of a ASCII graph, where walkable nodes are marked with a '.', obstacles are marked with an '@' and the path is marked with '+'. The start node is marked 'S' and end node marked with 'F'. 

### Improvements
It would probably been a lot more effective to skip the node and JPS node and just go with different two dimensional lists. Many times it would be sufficient to jsut use coordinates, e.g. in jump method JPSNode is not mandatory, it could be made simpler by just using coordinates and two dimensional lists. 

### IO

#### MapReader 
Map reader takes care of making a file into a graph. 
