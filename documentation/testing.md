
# Testing document

### Unit testing
JPS and Dijkstra are tested with a few Junit tests. 

### Manual testing
The route made by both the algorithms can be printed. In the graph the start node is marked with an S and end node marked with an F.
The correctness of the route were regardles of length checked by the drawing of both routes. The Berlin_1_512.map were used as then the map could be somehow easily read from the laptop screen.

### Performance testing
In both correctness and speed tests the same random chosen scenarious are used, from same map. 

#### Correctness tests
Tests used ASCII maps and .scen scenarios. Dijkstra is compared to the .scen distance and checked that it is smaller or as long as Dijsktras route distance. JPS is compared to Dijsktra. Since in a few cases JPS gives a slightly longer route, the  number of error routes are calculated and the distance error is as well calculated. On average the distance error is very small, under 1 %. Even there is an error, the algorithms always found a route, the only small error is in the distance. Moreover it is good to understand that Dijkstra always found a better or as good route as the scenario. 


Map | Routes | Route average distance | Errors | Error in distance % | Error % | Dijkstra speed | JPS speed
Berlin_2_512.map | 2000 | ? | 197 | 0.009354800104569138 % | 9.85 %
Berlin_2_1024.map | 100 | 755.75 | 3 | 0.2663167786077694 % | 3.0 % | 13774 | 1876
Berlin_2_1024.map | 1000 | 779.21 | 37 | 0.017959190201509205 % | 3.70 % | 19549 | 144456
Berlin_2_1024.map | 1000 | 785.50 | 39 | 0.005300935231815758 % | 3.90 %| 148722 | 19646


#### Speed tests
Speed tests were performed with the same randomly selected scenarious as the performance tests. I didn't take into account if a route were correct or not, since a route was always found. As we can see JPS is many times faster as Dijkstra. The results can be found in the table above. 

