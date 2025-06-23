import java.io.PrintStream;
import java.io.PrintWriter;

public class Tree<T extends Comparable<T>> {
    private Node<T> root;

    public Tree() {
        root = null;
    }

    public void insert(T value) {
        root = insertRecursive(root, value);
    }

    private Node<T> insertRecursive(Node<T> node, T value) {
        if (node == null)
            return new Node<>(value);

        int cmp = value.compareTo(node.getValue());
        if (cmp <= 0) {
            node.setLeft(insertRecursive(node.getLeft(), value)); // duplicates go left
        } else {
            node.setRight(insertRecursive(node.getRight(), value));
        }

        return node;
    }

    public boolean search(T value) {
        return searchRecursive(root, value);
    }

    private boolean searchRecursive(Node<T> node, T value) {
        if (node == null)
            return false;

        int cmp = value.compareTo(node.getValue());
        if (cmp == 0)
            return true;
        else if (cmp < 0)
            return searchRecursive(node.getLeft(), value);
        else
            return searchRecursive(node.getRight(), value);
    }

    public void delete(T value) {
        root = deleteRecursive(root, value);
    }

    private Node<T> deleteRecursive(Node<T> node, T value) {
        if (node == null)
            return null;

        int cmp = value.compareTo(node.getValue());
        if (cmp < 0) {
            node.setLeft(deleteRecursive(node.getLeft(), value));
        } else if (cmp > 0) {
            node.setRight(deleteRecursive(node.getRight(), value));
        } else {
            // node to be deleted
            if (node.getLeft() == null)
                return node.getRight();
            if (node.getRight() == null)
                return node.getLeft();

            // node with two children: replace with in-order successor
            Node<T> successor = findMin(node.getRight());
            node.setValue(successor.getValue());
            node.setRight(deleteRecursive(node.getRight(), successor.getValue()));
        }

        return node;
    }

    private Node<T> findMin(Node<T> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    // Draw tree in console (sideways hierarchy)
    public void draw(PrintWriter out) {
        drawRecursive(root, "", out);
        out.println("END_DRAW");
    }

    private void drawRecursive(Node<T> node, String indent, PrintWriter out) {
        if (node != null) {
            drawRecursive(node.getRight(), indent + "   ", out);
            out.println(indent + node.getValue());
            drawRecursive(node.getLeft(), indent + "   ", out);
        }
    }

    // Optional: Print values in sorted (in-order) order
    public void inOrder(PrintStream out) {
        inOrderRecursive(root, out);
    }

    private void inOrderRecursive(Node<T> node, PrintStream out) {
        if (node != null) {
            inOrderRecursive(node.getLeft(), out);
            out.println(node.getValue());
            inOrderRecursive(node.getRight(), out);
        }
    }
}
