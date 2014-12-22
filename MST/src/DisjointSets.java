import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DisjointSets<T> implements DisjointSetsInterface<T> {
    //Should be a map of data to its parent; root data maps to itself.
    private Map<T, Node> set;

    /**
     * @param setItems the initial items (sameSet and merge will never be called
     * with items not in this set, this set will never contain null elements).
     */
    public DisjointSets(Set<T> setItems) {
        set = new HashMap<T, Node>();
        for (T u : setItems) {
            set.put(u, new Node(u, 0));
        }
    }

    private T find(T toFind) {
        final Node node = set.get(toFind);
        if (node == null) {
            return null;
        }
        if (toFind.equals(node.parent)) {
            return toFind;
        }
        node.parent = find(node.parent);
        return node.parent;
    }

    @Override
    public boolean sameSet(T u, T v) {
        if (u == null || v == null) {
            throw new IllegalArgumentException("Null Arguments!");
        }

        if (find(u).equals(find(v))) {
            return true;
        }
        return false;
    }

    @Override
    public void merge(T u, T v) {
        if (u == null || v == null) {
            throw new IllegalArgumentException("Null Arguments!");
        }

        final T rootU = find(u);
        final T rootV = find(v);

        if (!rootU.equals(rootV)) {
            final Node uNode = set.get(rootU);
            final Node vNode = set.get(rootV);
            if (uNode.rank < vNode.rank) {
                uNode.parent = rootV;
            } else if (uNode.rank > vNode.rank) {
                vNode.parent = rootU;
            } else {
                vNode.parent = rootU;
                uNode.rank++;
            }
        }
    }

    private class Node {
        public T parent;
        public int rank;

        public Node(T parent, int rank) {
            this.parent = parent;
            this.rank = rank;
        }
    }
}
