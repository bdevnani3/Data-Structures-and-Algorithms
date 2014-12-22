import java.util.HashSet;
import java.util.Set;

public class SkipList<T extends Comparable<? super T>>
    implements SkipListInterface<T> {
    private CoinFlipper coinFlipper;
    private int size;
    private Node<T> head;


    /**
     * constructs a SkipList object that stores data in ascending order
     * when an item is inserted, the flipper is called until it returns a tails
     * if for an item the flipper returns n heads, the corresponding node has
     * n + 1 levels
     *
     * @param coinFlipper the source of randomness
     */
    public SkipList(CoinFlipper coinFlipper) {
        this.coinFlipper = coinFlipper;
        size = 0;
        head = new Node<T>(null, 1);
    }

    @Override
    public T first() {
        if (size() == 0) {
            return null;
        }
        Node<T> curr = head;
        while (curr.getDown() != null) {
            curr = curr.getDown();
        }
        return curr.getNext().getData();
    }

    @Override
    public T last() {
        if (size != 0) {
            Node<T> current = head;
            while ((current.getNext() != null) || (current.getDown() != null)) {
                if (current.getNext() == null) {
                    current = current.getDown();

                } else if (current.getDown() == null) {
                    current = current.getNext();

                } else {
                    current = current.getNext();
                }
            }
            return current.getData();
        }
        return null;
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data");
        }
        if (size() == 0) {
            return false;
        }
        Node<T> current = head;
        while (current.getNext() != null
            && !current.getNext().getData().equals(data)) {
            if (current.getNext().getNext() != null
                && current.getNext().getNext().getData().compareTo(data) < 0) {
                current = current.getNext().getNext();

            } else if (current.getNext().getNext() != null
                && current.getNext().getNext().getData().compareTo(data) > 0) {
                current = current.getNext().getDown();

            } else if (current.getNext().getNext() == null
                && current.getNext().getData().compareTo(data) < 0) {
                current = current.getDown();
            }
            if (current.getDown() == null
                && current.getNext().getNext() == null) {
                return false;
            }
        }

        while (current != null) {
            if (current.getNext().getData().compareTo(data) == 0) {
                return true;

            } else if (current.getNext().getData().compareTo(data) < 0) {
                current = current.getNext();
            }
        }
        return false;
    }

    @Override
    public void put(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        int level = 1;
        while (coinFlipper.flipCoin() == CoinFlipper.Coin.HEADS) {
            level++;
        }
        if (level > head.getLevel()) {
            Node<T> node = new Node<T>(null, head.getLevel() + 1);
            node.setDown(head);
            head.setUp(node);
            head = node;
        }

        Node<T> node = head;
        while (node.getLevel() > level) {
            node = node.getDown();
        }
        Node<T> last = null;
        while (node != null && node.getLevel() >= 1) {
            while (node.getNext() != null
                && node.getNext().getData().compareTo(data) < 0) {
                node = node.getNext();
            }
            Node<T> temp = new Node<>(data, node.getLevel());
            temp.setNext(node.getNext());
            node.setNext(temp);
            node.setUp(last);
            if (last != null) {
                last.setDown(temp);
            }
            last = temp;
            node = node.getDown();
        }
        size++;
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Can't check for null data");
        }
        if (size == 0) {
            return null;
        }
        
        Node<T> current = head.getNext();
        int counter = 0;        //counter keeps track of the number of nodes that have 
                                //been looked up. It can never exceed the size;
        while (counter <= size) {
            if (data.compareTo(current.getNext().getData()) == 0) {
                return current.getNext().getData();
            
            } else if (data.compareTo(current.getNext().getData()) > 0) {
                current = current.getNext();
            
            } else if (data.compareTo(current.getNext().getData()) < 0) {
                current = current.getDown();
            }
            
            counter++;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = new Node<T>(null, 1);
        size = 0;
    }

    @Override
    public Set<T> dataSet() {
        HashSet<T> set = new HashSet<T>(size());
        if (size == 0 || head == null) {
            return set;
        }
        Node<T> current = head;
        
        if (head.getLevel() > 1) {
        for (int i = head.getLevel(); i > 1; i--) {
            current = current.getDown();
            }

        } else {
            current = head;
        }
        if (current.getNext() != null) {
        current = current.getNext();
        }
        while (current != null) {
            set.add(current.getData());
            current = current.getNext();
        }
        return set;
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data");
        }
        if (size() == 0) {
            return null;
        }
        Node<T> current = head;
        while (current.getNext() != null
            && !current.getNext().getData().equals(data)) {

            if (current.getNext().getNext() != null
                && current.getNext().getNext().getData().compareTo(data) < 0) {
                current = current.getNext().getNext();
                
            } else if (current.getNext().getNext() != null
                && current.getNext().getNext().getData().compareTo(data) > 0) {
                current = current.getNext().getDown();

            } else if (current.getNext().getNext() == null
                && current.getNext().getData().compareTo(data) < 0) {
                current = current.getNext();
                
            } else if (current.getNext().getNext() == null
                    && current.getNext().getData().compareTo(data) > 0) {
                    current = current.getDown();
                
            }
            
            if ((current == null) || (current.getDown() == null
                && current.getNext().getNext() == null)) {
                return null;
            }
        } 


        T old = null;
        while (current != null) {
            if (current.getNext().getData().compareTo(data) == 0) {
                old = current.getNext().getData();
                current.setNext(current.getNext().getNext());
                current = current.getDown();
            } else if (current.getNext().getData().compareTo(data) < 0) {
                current = current.getNext();
            }
        }
        size--;
        return old;
    }

}
