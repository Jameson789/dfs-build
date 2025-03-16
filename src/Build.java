import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Build {

  /**
   * Prints words that are reachable from the given vertex and are strictly shorter than k characters.
   * If the vertex is null or no reachable words meet the criteria, prints nothing.
   *
   * @param vertex the starting vertex
   * @param k the maximum word length (exclusive)
   */
  public static void printShortWords(Vertex<String> vertex, int k) { 
    printShortWords(vertex, k, new HashSet<>());
  } 

  private static void printShortWords(Vertex<String> vertex, int k, Set<Vertex<String>> seen){
    if(vertex == null) return;
    if(!seen.add(vertex)) return;
    if(vertex.data.length() < k){
      System.out.println(vertex.data);
    } 
    for (var neighbor : vertex.neighbors){
      printShortWords(neighbor, k, seen);
    }
  } 

  /**
   * Returns the longest word reachable from the given vertex, including its own value.
   *
   * @param vertex the starting vertex
   * @return the longest reachable word, or an empty string if the vertex is null
   */
  public static String longestWord(Vertex<String> vertex) {
    return longestWord(vertex, new HashSet<>());
  }

  private static String longestWord(Vertex<String> vertex, Set<Vertex<String>> seen){
    if(vertex == null) return "";
    if(!seen.add(vertex)) return ""; 
    String longest = vertex.data;
    for (var neighbor : vertex.neighbors) {
      String test = longestWord(neighbor, seen);
      if(test.length() > longest.length()){
        longest = test;
      }
    }
    return longest;
  }

  /**
   * Prints the values of all vertices that are reachable from the given vertex and 
   * have themself as a neighbor.
   *
   * @param vertex the starting vertex
   * @param <T> the type of values stored in the vertices
   */
  public static <T> void printSelfLoopers(Vertex<T> vertex) { 
    printSelfLoopers(vertex, new HashSet<>());
  } 

  private static <T> void printSelfLoopers(Vertex<T> vertex, Set<Vertex<T>> seen){
    if(vertex == null) return;
    if(!seen.add(vertex)) return;
    for (var neighbor : vertex.neighbors){
      if(neighbor.equals(vertex)){
        System.out.println(vertex.data);
      } 
      printSelfLoopers(neighbor, seen);
    }
  }

  /**
   * Determines whether it is possible to reach the destination airport through a series of flights
   * starting from the given airport. If the start and destination airports are the same, returns true.
   *
   * @param start the starting airport
   * @param destination the destination airport
   * @return true if the destination is reachable from the start, false otherwise
   */
  public static boolean canReach(Airport start, Airport destination) {
    return canReach(start, destination, new HashSet<>());
  }

  private static boolean canReach(Airport start, Airport destination, Set<Airport> seen){
    if(start == null || destination == null) return false; 
    if(start.equals(destination)) return true; 
    if(!seen.add(start)) return false;
    
    for(var neighbor : start.getOutboundFlights()){
      if(canReach(neighbor, destination, seen)){
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the set of all values in the graph that cannot be reached from the given starting value.
   * The graph is represented as a map where each vertex is associated with a list of its neighboring values.
   *
   * @param graph the graph represented as a map of vertices to neighbors
   * @param starting the starting value
   * @param <T> the type of values stored in the graph
   * @return a set of values that cannot be reached from the starting value
   */
  public static <T> Set<T> unreachable(Map<T, List<T>> graph, T starting) {
    Set<T> reachable = new HashSet<>();
    unreachable(graph, starting, reachable);
    Set<T> unreachableNodes = new HashSet<>(graph.keySet());
    unreachableNodes.removeAll(reachable);
    return unreachableNodes;
  }

  private static <T> void unreachable(Map<T, List<T>> graph, T starting, Set<T> seen){
    if (!graph.containsKey(starting) || seen.contains(starting)) return;
    seen.add(starting);
    for(var neighbor: graph.get(starting)) {
      unreachable(graph, neighbor, seen);
    }
  }
}
