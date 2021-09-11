
# Testing document

### Unit testing
JPS, Dijkstra and graph are tested with Junit tests. Hpwever the function of the algorithms can't be tested with Junit tests, therefore most of the effort is put towards performance tests. 

### Manual testing
The route made by both the algorithms can be printed. In the graph the start node is marked with an S and end node marked with an F.
The correctness of the route were regardles of length checked by the drawing of both routes. The Berlin_1_512.map were used as then the map could be somehow easily read from the laptop screen. 

### Performance testing
In both correctness and speed tests the same random chosen scenarious are used, from the same map. 

#### Correctness tests
Tests used ASCII maps and .scen scenarios. Dijkstra is compared to the .scen distance and checked that Dijkstra is as good or better (as long or shorter) as the scenario disance. JPS route distance is then compared to Dijsktra's route length. Since in a few cases JPS gives a slightly longer route, the  number of error routes are calculated and the distance error is as well calculated. On average the distance error is very small, under 1 %. Even there is an error, the algorithms always found a route, the only small error is in the distance. Moreover it is good to understand that Dijkstra always found a better or as good route as the scenario. 

I used Berlin_2_119549024, Boston_0_1024 and Denver_2_1024 map and scenarios, to make 1000 routes all together 5 times. As can be seen from the table below the JPS had more errors on the Boston and Denver map. However the distance error is still very small.  


Map | Routes | Route average distance | Errors | Error in distance % | Error % | Dijkstra speed | JPS speed
---|----|----|------|------|----|----|-----
Berlin_2_1024.map | 100 | 755.75 | 3 | 0.2663167786077694 % | 3.0 % | 13774 | 1876
Berlin_2_1024.map | 1000 | 779.21 | 37 | 0.017959190201509205 % | 3.70 % | 144456 | 19549
Berlin_2_1024.map | 1000 | 785.50 | 39 | 0.005300935231815758 % | 3.90 %| 148722 | 19646
Boston_0_1024.map | 1000 | 772.77 | 111 | 0.004839689750724404 % | 11.1 % | 178648 | 22924
Boston_0_1024.map | 1000 | 774.72 | 120 | 0.005675915302972429 % | 12.0 % | 185963 | 27152
Denver_2_1024.map | 1000 | 740.23 | 115 | 0.010485603505400976 % | 11.5 % | 193642 | 26013


#### Speed tests
Speed tests were performed with the same randomly selected scenarious as the performance tests. I didn't take into account if a route were correct or not, since a route was always found. As we can see JPS is many times faster as Dijkstra. The results can be found in the table above. The speed tests calculates only the time it takes for the algorithm to find the route, for example drawing the route is not taken into account, which is slower for JPS than Dijkstra. The average speed for Dijkstra with 5 x 1000 randomly selected routes with the Berlin_2_1042, Boston_0_1024 and Denver_2_1024 map and scenarios was 143486.2 milliseconds and the same number for JPS was 23056.8. With these stats JPS was over 6 times faste. 

