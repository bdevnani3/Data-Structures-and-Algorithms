import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * My AVL implementation.
 *
 * @author Bhavika Devnani
 */
public class AVL<T extends Comparable<T>> implements AVLInterface<T>,
       Gradable<T> {

    // Do not add additional instance variables
    private Node<T> root;
    private int size;

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            root = new Node<T>(data);
            updateHeightAndBF(root);
            size++;
            return;
        }
        addHelper(null, root, data, false);
        size++;
    }
    /**
     * Helper function for add
     */
    private void addHelper(Node<T> parent, Node<T> current, T data,
            boolean isRight) {
        if ((current.getData() != null && data.compareTo(current
                        .getData()) > 0)) {
            if (current.getRight() == null) {
                current.setRight(new Node<T>(data));
                updateHeightAndBF(current.getRight());
            } else {
                addHelper(current, current.getRight(), data, true);
            }
        } else if (current.getLeft() == null) {
            current.setLeft(new Node<T>(data));
            updateHeightAndBF(current.getLeft());
        } else {
            addHelper(current, current.getLeft(), data, false);
        }

        updateHeightAndBF(current);
        rotate(current);
    }
    /**
     * Use this method to update the height and balance factor for a node.
     * 
     * @param node
     * The node whose height and balance factor need to be updated.
     */
    private void updateHeightAndBF(Node<T> current) {
        if (current.getRight() == null && current.getLeft() == null) {
            current.setHeight(0);
            current.setBalanceFactor(0);
        } else if (current.getRight() == null) {
            current.setHeight(current.getLeft().getHeight() + 1);
            if (current.getLeft().getHeight() == 0) {
                current.setBalanceFactor(1);
            } else {
                current.setBalanceFactor(current.getHeight());
            }
        } else if (current.getLeft() == null) {
            current.setHeight(current.getRight().getHeight() + 1);
            if (current.getRight().getHeight() == 0) {
                current.setBalanceFactor(-1);
            } else {
                current.setBalanceFactor(current.getHeight() * -1);
            }
        } else {
            current.setHeight(1 + Math.max(current.getLeft().getHeight(),
                    current.getRight().getHeight()));
            current.setBalanceFactor(-current.getRight().getHeight()
                    + current.getLeft().getHeight());
        }
    }
    /**
     * In this method, you will check the balance factor of the node that is
     * passed in and decide whether or not to perform a rotation. If you need to
     * perform a rotation, simply call the rotation and return the new root of
     * the balanced subtree. If there is no need for a rotation, simply return
     * the node that was passed in.
     * 
     * @param node
     * - a potentially unbalanced node
     * @return The new root of the balanced subtree.
     */
    private Node<T> rotate(Node<T> node) {
        Node<T> current = node;
        if (node != null) {
            updateHeightAndBF(node);
            if (node.getBalanceFactor() < -1) {
                if (node.getRight().getBalanceFactor() > 0) {
                    current = rightLeftRotate(node);
                } else {
                    current = leftRotate(node);
                }
            } else if (node.getBalanceFactor() > 1) {
                if (node.getLeft().getBalanceFactor() < 0) {
                    current = leftRightRotate(node);
                } else {
                    current = rightRotate(node);
                }
            }
            if (node == root) {
                root = current;
            }
            return current;
        }
        return null;
    }
    /**
     * Left Rotation
     * @param node
     *            - the current root of the subtree to rotate.
     * @return The new root of the subtree
     */
    private Node<T> leftRotate(Node<T> node) {
        Node<T> nodeCopy = new Node<T>(node.getData());
        Node<T> rightNode = node.getRight();
        Node<T> rightleftNode = rightNode.getLeft();
        node.setData(rightNode.getData());
        nodeCopy.setLeft(node.getLeft());
        nodeCopy.setRight(rightleftNode);
        node.setLeft(nodeCopy);
        node.setRight(rightNode.getRight());
        rotate(nodeCopy);
        rotate(node);
        return node;
    }
    /**
     * Right Rotation
     * @param node
     *            - the current root of the subtree to rotate.
     * @return The new root of the subtree
     */
    private Node<T> rightRotate(Node<T> node) {
        Node<T> nodeCopy = new Node<T>(node.getData());
        Node<T> leftNode = node.getLeft();
        Node<T> leftRightNode = leftNode.getRight();

        node.setData(leftNode.getData());
        nodeCopy.setRight(node.getRight());
        nodeCopy.setLeft(leftRightNode);
        node.setLeft(leftNode.getLeft());
        node.setRight(nodeCopy);

        rotate(nodeCopy);
        rotate(node);

        return node;
    }
    /**
     * Left Right Rotation
     * @param node
     *            - the current root of the subtree to rotate.
     * @return The new root of the subtree
     */
    private Node<T> leftRightRotate(Node<T> node) {
        leftRotate(node.getLeft());
        return rightRotate(node);
    }
    /**
     * Right Left Rotation
     * @param node
     *            - the current root of the subtree to rotate.
     * @return The new root of the subtree
     */
    private Node<T> rightLeftRotate(Node<T> node) {
        rightRotate(node.getRight());
        return leftRotate(node);
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        if (!(contains(data))) {
            return null;
        }
        return removeHelper(null, root, data, false);
    }

    private T removeHelper(Node<T> parent, Node<T> current, T data,
            boolean isRight) {

        if (data == null && current.getData() == null) {
            throw new IllegalArgumentException();
        } else if ((data == null && current.getData() != null)
                || !data.equals(current.getData())) {
            if (data == null
                    || (current.getData() != null && data.compareTo(current
                            .getData()) > 0)) {
                if (current.getRight() != null) {
                    T result = removeHelper(current, current.getRight(), data,
                            true);
                    updateHeightAndBF(current);
                    rotate(current);
                    return result;
                } else {
                    return null;
                }
            } else if (current.getLeft() != null) {
                T result = removeHelper(current,
                    current.getLeft(), data, false);
                updateHeightAndBF(current);
                rotate(current);
                return result;
            } else {
                return null;
            }
        }

        T result = current.getData();

        if (current.getLeft() == null && current.getRight() == null) {
            if (parent == null) {
                root = null;
            } else if (isRight) {
                parent.setRight(null);
            } else {
                parent.setLeft(null);
            }
        } else if ((current.getLeft() == null && current.getRight() != null)
                || (current.getLeft() != null && current.getRight() == null)) {
            if (current.getLeft() == null) {
                if (parent == null) {
                    root = moveNode(current, current.getRight());
                } else if (isRight) {
                    parent.setRight(moveNode(current, current.getRight()));
                } else {
                    parent.setLeft(moveNode(current, current.getRight()));
                }
            } else if (parent == null) {
                root = moveNode(current, current.getLeft());
            } else if (isRight) {
                parent.setRight(moveNode(current, current.getLeft()));
            } else {
                parent.setLeft(moveNode(current, current.getLeft()));
            }
        } else {
            if (parent == null) {
                root = moveNode(root, predecessor(current, true, true));
            } else if (isRight) {
                parent.setRight(moveNode(current,
                        predecessor(current, true, false)));
            } else {
                parent.setLeft(moveNode(current,
                        predecessor(current, true, false)));
            }
        }

        size--;
        updateHeightAndBF(current);
        rotate(current);

        return result;
    }
    /**
     * Moves Node as required
     * @param node
     *            - the current root 
     * @return The new root of the subtree
     */
    private Node<T> moveNode(Node<T> toRemove, Node<T> toMove) {
        if (toMove.getLeft() == null) {
            toMove.setLeft(toRemove.getLeft());
            if (toRemove.getRight() != toMove) {
                toMove.setRight(toRemove.getRight());
            }
        } else {
            toRemove.getLeft().setRight(toMove.getLeft());
            if (toRemove.getLeft() != toMove) {
                toMove.setLeft(toRemove.getLeft());
            }
            if (toRemove.getRight() != toMove) {
                toMove.setRight(toRemove.getRight());
            }
        }
        return toMove;
    }
    /**
     * Returns predecessor
     * @param node
     *            - the current root .
     * @return predecessor
     */
    private Node<T> predecessor(Node<T> current, boolean isFirst,
            boolean isRoot) {
        if (isFirst) {
            if (current.getLeft().getRight() == null) {
                Node<T> newNode = current.getLeft();
                if (!isRoot) {
                    current.setLeft(null);
                }
                return newNode;
            }
            return predecessor(current.getLeft(), false, isRoot);
        } else {
            if (current.getRight().getRight() == null) {
                Node<T> newNode = current.getRight();
                if (!isRoot) {
                    current.setRight(null);
                }
                return newNode;
            }
            return predecessor(current.getRight(), false, isRoot);
        }
    }

    @Override
    public T get(T data) {
        if (!(contains(data))) {
            return null;
        }
        return getHelper(root, data);
    }
    /**
     * Recurses through the tree and find the data that is passed
     * in.
     * 
     * @param data
     *  The data to fetch from the tree.
     * @return The data that the user wants from the tree. Return null if not
     *         found.
     */
    private T getHelper(Node<T> current, T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        if (current == null) {
            return null;
        }
        if (data == null && current.getData() == null) {
            return current.getData();
        } else if (data.equals(current.getData())) {
            return current.getData();
        } else {
            if (current.getData() != null
                    & data.compareTo(current.getData()) > 0) {
                // data is bigger then current non-null
                if (current.getRight() != null) {
                    return getHelper(current.getRight(), data);
                }
                return null;
            } else {
                // data is smaller then current non-null
                if (current.getLeft() != null) {
                    return getHelper(current.getLeft(), data);
                }
                return null;
            }
        }
    }
    /**
     * Recurses through the tree and find the data that is passed
     * in.
     * 
     * @param data
     *            The data to fetch from the tree.
     * @return True or false
     */
    public boolean contains(T data) {
        return containsHelper(root, data);
    }

    private boolean containsHelper(Node<T> current, T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        if (current == null) {
            return false;
        }
        if (data == null && current.getData() == null) {
            return true;
        } else if (data.equals(current.getData())) {
            return true;
        } else {
            if (current.getData() != null
                    & data.compareTo(current.getData()) > 0) {
                // data is bigger then current non-null
                if (current.getRight() != null) {
                    return containsHelper(current.getRight(), data);
                }
                return false;
            } else {
                // data is smaller then current non-null
                if (current.getLeft() != null) {
                    return containsHelper(current.getLeft(), data);
                }
                return false;
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return (root == null);
    }

    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
        List<T> list = (List<T>) new ArrayList<T>();
        preorder(list, root);
        return list;
    }

    /**
     * It is a helper method, that recursively adds the
     * given node's data, in a  preorder style.
     *
     * First is the data in the node, then the data
     * on the left, followed by the data on
     * the right in pre-order. It compies this to a list.
     *
     * @param current node
     * @param list Data is being added to this list.
     * @return void return (does not return anything)
     */

    private void preorder(List<T> list, Node<T> node) {
        if (node != null) {
            list.add(node.getData());
            preorder(list, node.getLeft());
            preorder(list, node.getRight());
        } else {
            return;
        }
    }

    @Override
    public List<T> postorder() {
        List<T> list = (List<T>) new ArrayList<T>();
        postorder(list, root);
        return list;
    }

    /**
     * It is a helper method, that recursively
     * adds the given node's data, in a  postorder style.
     *
     * @param current node
     * @param list Data is being added to this list.
     * @return void return (does not return anything)
     */

    private void postorder(List<T> list, Node<T> node) {
        if (node != null) {
            postorder(list, node.getLeft());
            postorder(list, node.getRight());
            list.add(node.getData());
        } else {
            return;
        }
    }

    @Override
    public List<T> inorder() {
        List<T> list = (List<T>) new ArrayList<T>();
        inorder(list, root);
        return list;
    }

    /**
     * It is a helper method, that recursively adds
     * the given node's data, in an inorder style.
     * @param current node
     * @param list Data is being added to this list.
     * @return void return (does not return anything)
     */

    private void inorder(List<T> list, Node<T> node) {
        if (node != null) {
            inorder(list, node.getLeft());
            list.add(node.getData());
            inorder(list, node.getRight());
        } else {
            return;
        }
    }

    @Override
    public List<T> levelorder() {
        List<T> list = (List<T>) new ArrayList<T>();
        levelorder(list, root);
        return list;
    }

    /**
     * It is a helper method, that recursively adds
     * the given node's data, in an inorder style.
     *
     * All the elements are compied this to a queue, level wise.
     *
     * @param current node
     * @param list Data is being added to this list.
     * @return void return (does not return anything)
     */

    @SuppressWarnings("unchecked")
    private void levelorder(List<T> list, Node<T> node) {
        Queue<Node<T>> queue = new LinkedList<Node<T>>();
        if (node != null) {
            queue.add(node);
            while (!queue.isEmpty()) {
                Node<T> queueNode = queue.peek();
                if (queueNode.getLeft() != null) {
                    queue.add(queueNode.getLeft());
                }
                if (queueNode.getRight() != null) {
                    queue.add(queueNode.getRight());
                }
                list.add(queue.remove().getData());
            }
        } else {
            return;
        }
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int height() {
        return root.getHeight();
    }

    @Override
    public Node<T> getRoot() {
        return root;
    }
}