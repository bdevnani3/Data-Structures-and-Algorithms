import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class MST {

    /**
     * Using disjoint set(s), run Kruskal's algorithm on the given graph and
     * return the MST. Return null if no MST exists for the graph.
     *
     * @param g The graph to be processed. Will never be null.
     * @return The MST of the graph; null if no valid MST exists.
     */
    public static Collection<Edge> kruskals(Graph g) {

        PriorityQueue<Edge> edges = new PriorityQueue<Edge>();
        DisjointSets<Vertex> disSets = new
                DisjointSets<Vertex>(g.getVertices());
        ArrayList<Edge> minSpanning = new ArrayList<Edge>();


        for (Edge edge : g.getEdgeList()) {
            edges.add(edge);
        }

        while (!edges.isEmpty() && (minSpanning.size()
                < g.getVertices().size() - 1)) {
            Edge ed = edges.poll();
            Vertex v = ed.getV();
            Vertex u = ed.getU();
            if (!disSets.sameSet(u, v)) {
                disSets.merge(v, u);
                minSpanning.add(ed);
            }
            if (g.getVertices().size() - 1 == minSpanning.size()) {
                return minSpanning;
            }
        }
        return null;
    }

    /**
     * Run Prim's algorithm on the given graph and return the minimum spanning
     * tree. If no MST exists, return null.
     *
     * @param g The graph to be processed. Will never be null.
     * @param start The ID of the start node. Will always exist in the graph.
     * @return the MST of the graph; null if no valid MST exists.
     */
    public static Collection<Edge> prims(Graph g, int start) {
        PriorityQueue<Edge> availableEdges = new PriorityQueue<Edge>();
        ArrayList<Edge> listMinEdges = new ArrayList<Edge>();
        Set<Vertex> visitedEdges = new HashSet<Vertex>();
        Vertex source = new Vertex(start);
        visitedEdges.add(source);

        while (g.getVertices().size() - 1 != listMinEdges.size()) {
            Map<Vertex, Integer> adjacents = g.getAdjacencies(source);

            for (Vertex vertex: adjacents.keySet()) {
                Edge edge = new Edge(source, vertex,
                        adjacents.get(vertex));
                if (!visitedEdges.contains(edge.getV())) {
                    availableEdges.add(edge);
                }
            }
            Edge smallestEdge = availableEdges.poll();

            if (smallestEdge == null) {
                return null;
            }

            source = smallestEdge.getV();

            while (visitedEdges.contains(source) && !availableEdges.isEmpty()) {
                smallestEdge = availableEdges.poll();
                source = smallestEdge.getV();
            }

            if (!visitedEdges.contains(source)) {
                listMinEdges.add(smallestEdge);
            }
            visitedEdges.add(source);
        }
        return listMinEdges;
    }
}