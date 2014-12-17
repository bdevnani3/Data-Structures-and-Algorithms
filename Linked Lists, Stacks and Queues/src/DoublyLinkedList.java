/**
 * Doubly linked list implementation
 * @author Bhavika Devnani
 * @version 1.0
 */
public class DoublyLinkedList<T> implements LinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int length;

    @Override
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > length) {
            throw new IndexOutOfBoundsException("This index does not exist");
        }

        Node<T> newNode = new Node(data);

        if (index == 0) {
            if (head == null) {
                head = newNode;
                newNode.setNext(null);
                newNode.setPrevious(null);
                tail = newNode;
            } else {
                newNode.setNext(head);
                head.setPrevious(newNode);
                newNode.setPrevious(null);
                head = newNode;
            }
        } else if (index == length) {
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            newNode.setNext(null);
            tail = newNode;
        } else {
            int counter = 0;
            Node<T> getNode = head;
            while (counter != index - 1) {
                getNode = getNode.getNext();
                counter++;
            }
            newNode.setNext(getNode.getNext());
            getNode.setNext(newNode);
            newNode.getNext().setPrevious(newNode);
            newNode.setPrevious(getNode);
        }
        length++;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("This index does not exist");
        } else if (index == 0) {
            return head.getData();
        } else if (index == length - 1) {
            return tail.getData();
        } else {
            Node<T> getNode;
            if (index <= length / 2) {
                int counter = 0;
                getNode = head;
                while (counter != index) {
                    getNode = getNode.getNext();
                    counter++;
                }
            } else {
                int counter = length;
                getNode = tail;
                while (counter != index) {
                    getNode = getNode.getPrevious();
                    counter--;
                }
            }
            return getNode.getData();
        }
    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("This index does not exist");
        }
        Node<T> removeNode;

        if (index == 0) {
            removeNode = head;
            if (length == 1) {
                head.setNext(null);
                head.setPrevious(null);
                head = null;
                tail = null;
            } else {
                head = head.getNext();
                head.setPrevious(null);
                removeNode.setNext(null);
            }
        } else if (index == length - 1) {
            removeNode = tail;
            tail = tail.getPrevious();
            tail.setNext(null);
            removeNode.setPrevious(null);
        } else {
            Node<T> currentNode;
            int counter = 0;
            if (index <= length / 2) {
                currentNode = head;
                while (counter != index - 1) {
                    currentNode = currentNode.getNext();
                    counter++;
                }
            } else {
                currentNode = tail;
                while (counter != index - 1) {
                    currentNode = currentNode.getPrevious();
                    counter--;
                }
            }
            removeNode = currentNode.getNext();
            currentNode.setNext(removeNode.getNext());
            removeNode.getNext().setPrevious(currentNode);
            removeNode.setNext(null);
            removeNode.setPrevious(null);
        }
        length--;
        return removeNode.getData();
    }

    @Override
    public void addToFront(T t) {
        addAtIndex(0, t);
    }

    @Override
    public void addToBack(T t) {
        addAtIndex(length, t);
    }

    @Override
    public T removeFromFront() {
        if (isEmpty()) {
            return null;
        }
        return removeAtIndex(0);

    }

    @Override
    public T removeFromBack() {
        if (isEmpty()) {
            return null;
        }
        return removeAtIndex(length - 1);
    }

    @Override
    public T[] toArray() {
        if (isEmpty()) {
            return (T[]) new Object[0];
        }

        T[] linkedListArray = (T[]) new Object[length];
        Node<T> arrayNode = head;
        for (int i = 0; i < length; i++) {
            linkedListArray[i] = arrayNode.getData();
            arrayNode = arrayNode.getNext();
        }
        return linkedListArray;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        length = 0;
    }


    /**
     * Reference to the head node of the linked list.
     * Normally, you would not do this, but we need it
     * for grading your work.
     *
     * You will get a 0 if you do not implement this method.
     *
     * @return Node representing the head of the linked list
     */
    public Node<T> getHead() {
        return head;
    }

    /**
     * Reference to the tail node of the linked list.
     * Normally, you would not do this, but we need it
     * for grading your work.
     *
     * You will get a 0 if you do not implement this method.
     *
     * @return Node representing the tail of the linked list
     */
    public Node<T> getTail() {
        return tail;
    }
}
