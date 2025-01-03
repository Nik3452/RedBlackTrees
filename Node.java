/**
 * The Node class represents a node in a red-black tree.
 */
public class Node {
    /**
     * A Color is either RED or BLACK.
     */
    public enum Color {
        RED, BLACK
    }

    private Color color;
    private int data;
    private Node left;
    private Node right;
    private Node parent;

    /**
     * Create a new node given an int and a color. 
     * Sets the left and right sub-trees to null.
     * @param data The value stored in the node
     * @param color The initial color of the node
     */
    public Node(int data, Color color) {
        left = null;
        right = null;
        parent = null;
        this.color = color;
        this.data = data;
    }

    /**
     * Create a new node given an int, a color, 
     * the left and right sub-trees, and the parent.
     * @param data The value stored in the node
     * @param color The initial color of the node
     * @param left The left sub-tree
     * @param right The right sub-tree
     * @param parent The parent node
     */
    public Node(int data, Color color,
                Node left, Node right, Node parent) {
        this.left = left;
        this.right = right;
        this.parent = parent;
        this.color = color;
        this.data = data;
    }

    /**
     * Get the current color of the node
     * @return Color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Return the opposite of the supplied color
     * @param c Color
     * @return Color
     */
    public static Color flipColor(Color c) {
        if (c == Color.RED)
            return Color.BLACK;
        return Color.RED;
    }

    /**
     * Set the color of the node
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Return the data stored in the node
     * @return
     */
    public int getData() {
        return data;
    }

    /**
     * Set the data stored in the node
     * @param data
     */
    public void setData(int data) {
        this.data = data;
    }

    /**
     * Get the left sub-tree, and null if none exists
     * @return
     */
    public Node getLeft() {
        return left;
    }

    /**
     * Set the left sub-tree
     * @param left
     */
    public void setLeft(Node left) {
        this.left = left;
    }

    /**
     * Get the right sub-tree, and null if none exists
     * @return
     */
    public Node getRight() {
        return right;
    }

    /**
     * Set the right sub-tree
     * @param right
     */
    public void setRight(Node right) {
        this.right = right;
    }

    /**
     * Get the parent node, and null if none exists
     * @return
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Set the parent node
     * @param parent
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }
}
