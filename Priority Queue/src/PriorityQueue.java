public class PriorityQueue<T extends Comparable<? super T>> implements
       PriorityQueueInterface<T>, Gradable<T> {

    private Heap<T> priorityQueue;

    public PriorityQueue() {
        priorityQueue = new Heap<T>();
    }

    @Override
    public void insert(T item) {
        priorityQueue.add(item);
    }

    @Override
    public T findMin() {
        return priorityQueue.peek();
    }

    @Override
    public T deleteMin() {
        return priorityQueue.remove();
    }

    @Override
    public boolean isEmpty() {
        return priorityQueue.isEmpty();
    }

    @Override
    public void makeEmpty() {
        priorityQueue.empty();
    }

    @Override
    public T[] toArray() {
        return priorityQueue.toArray();
    }
}
