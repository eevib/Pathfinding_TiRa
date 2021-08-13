# Implementation

Both algorithms are implemented in their own classes. There is a class node which is the node Dijkstra uses. JPS have its own node JPSNode. This is because both algorithms have different needs for the node. 

Both algorithms get a made graph, with start and end points marked. 

### Dijkstra
At the moment Dijkstra uses two dimensional double graphs to keep track of distances to start node and keeping track of nodes visited. Moreover a priority queue is used as a heap to go through all needed nodes. Moreover a arrayList is used to save the route and drawing it. 

### JPS
The JPS algortihm is not yet ready. 

### Map 
Map takes form of a ASCII graph, where walkable nodes are marked with a '.', obstacles are marked with an '@' and the path is marked with '+'. The start node is marked 'S' and end node marked with 'F'. 

