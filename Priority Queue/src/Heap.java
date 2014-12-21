public class Heap<T extends Comparable<? super T>> implements HeapInterface<T>,
       Gradable<T> {

    private int size; //size of heap
    private T[] array; //new array
    private static final int ARRSIZE = 10; //initial capacity of the array

    public Heap() {
        array = (T[]) new Comparable[ARRSIZE];
        size = 0;
    }

    public void add(T item) {
        if (size >= array.length - 1) {
            doubleArray();
        }

        size++;
        int index = size;
        array[index] = item;

        bubbleUp();
    }

//doubles size of array when its full
    protected void doubleArray() {
        T[ ] newArray;

        newArray = (T[]) new Comparable[array.length * 2 ];
        for (int i = 1; i < array.length; i++) {
            newArray[ i ] = array[ i ];
            array = newArray;
        }
    }

    protected void bubbleUp() {
        int index = size;

        while (hasParent(index)
            && (parent(index).compareTo(array[index]) > 0)) {
            swap(index, parentIndex(index));
            index = parentIndex(index);
        }
    }


    protected boolean hasParent(int i) {
        return i > 1;
    }

    protected int leftIndex(int i) {
        return i * 2;
    }

    protected int rightIndex(int i) {
        return i * 2 + 1;
    }

    protected boolean hasLeftChild(int i) {
        return leftIndex(i) <= size;
    }

    protected boolean hasRightChild(int i) {
        return rightIndex(i) <= size;
    }

    protected T parent(int i) {
        return array[parentIndex(i)];
    }

    protected int parentIndex(int i) {
        return i / 2;
    }

    protected void swap(int index1, int index2) {
        T tmp = array[index1];
        array[index1] = array[index2];
        array[index2] = tmp;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public T peek() {
        if (this.isEmpty()) {
            return null;
        }

        return array[1];
    }

    @Override
    public T remove() {
        T result = peek();

        array[1] = array[size];
        array[size] = null;
        size--;

        bubbleDown();

        return result;
    }

    protected void bubbleDown() {
        int index = 1;

        while (hasLeftChild(index)) {
            int smallerChild = leftIndex(index);
            if (hasRightChild(index)
                && array[leftIndex(index)].compareTo(array[rightIndex(index)])
                > 0) {
                smallerChild = rightIndex(index);
            }

            if (array[index].compareTo(array[smallerChild]) > 0) {
                swap(index, smallerChild);
                index = smallerChild;
            } else {
                return;
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T[] toArray() {
        return array;
    }

    protected void empty() {
        array = (T[]) new Comparable[ARRSIZE];
        size = 0;
    }


}
