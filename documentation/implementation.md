# Implementation

Both algorithms are implemented in their own classes. There is a class called node which is the node Dijkstra uses. JPS have its own node JPSNode. This is because both algorithms have different needs for the node. 

Both algorithms get a made graph, with start and end points marked.

### Dijkstra
Dijkstra uses two dimensional double graphs to keep track of distances to start node and keeping track of nodes visited. Moreover a priority queue is used as a heap to go through all needed nodes. An arrayList is used to save the route and drawing it. 

### JPS
JPS have a two dimensional JPSNode list called nodes which saves the nodes with shortest distance from start. 
A JPSNode priority queue is a open list of successor nodes and a two dimensional Boolean list is used to keep track of which nodes are on the openList. The route is saved as a JPSNode ArrayList. The map is given as a Graph graph. 

### Graph
The ASCII map is made to a graph, which is a two dimensional char list.

### Map 
Map takes form of a ASCII graph, where walkable nodes are marked with a '.', obstacles are marked with an '@' and the Dijkstra path is marked with '+' and JPS route marked with '?'. The start node is marked 'S' and end node marked with 'F'. Note that the routes are marked only if drawRoute() method is run. 
 
### Improvements
It would probably been a lot more effective to skip the node and JPS node and just go with different two dimensional lists. Many times it would be sufficient to just use coordinates, e.g. in jump method JPSNode is not mandatory, it could be made simpler by just using coordinates and two dimensional lists. 

### IO
Files from [Moving AI](https://www.movingai.com/benchmarks/street/index.html) are used. The MapReader and ScenReader can only read files which doesn't have any rows before the actual data starts.

#### MapReader and ScenReader
Map reader takes care of making a file into a graph and the scenReader turns a file of scenarious into an ArrayList with the rows in String lists. 
