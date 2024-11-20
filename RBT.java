/**
 * RBT
 * Red-Black Tree Insert
 * @author nk505
 */
import java.util.*;
public class RBT {
    private Node root;

    public RBT() {}

    public boolean isRed(Node x) {
        if (x == null) return false;
        return x.getColor() == Node.Color.RED;
    }
    
    public boolean isEmpty() {
        return root == null;
    }

    public boolean contains(int x) {
        return nodeContainsData(root, x);
    }

    private boolean nodeContainsData(Node r, int x) {
        while (r != null) {
            if (r.getData() - x < 0) {
                r = r.getLeft();
            } else if (r.getData() - x > 0) {
                r = r.getRight();
            } else {
                return true;
            }
        }
        return false;
    }

    public List<Integer> serializeTree() {
        return serializeTree(root);
    }

    private List<Integer> serializeTree(Node r) {
        if (r == null) return new LinkedList<>();
        int data = r.getData();
        List<Integer> left = serializeTree(r.getLeft());
        List<Integer> right = serializeTree(r.getRight());
        left.add(data);
        left.addAll(right);
        return left;
    }

    public int maxHeight() {
        return maxHeight(root);
    }

    private int maxHeight(Node r) {
        if (r==null) return 0;        
        return 1 + Math.max(maxHeight(r.getLeft()), maxHeight(r.getRight()));
    }




    // ************************************************************************
    // * INSERT INTO RED-BLACK TREE
    // ************************************************************************

    public void insert(int x) {
        root = nodeInsertData(root, x);
        root.setColor(Node.Color.BLACK);
    }

    private Node nodeInsertData(Node r, int x) {
        // If Insertion at Node r is null we just return a new Node with value x and color it red
        if (r == null) return new Node(x, Node.Color.RED);
        else {
            // Create a Node trackParent set as null to track the parent
            Node trackParent = null;
            // Create a Node traversalNode set as root to traversal the tree
            Node traversalNode = root;
            // Create new node to insert into tree
            Node insertNewNode = new Node(x, Node.Color.RED);

            // Standard BST insert
            // If traversal node not null then we proceed
            while (traversalNode != null) {
                // Assign Node trackParent as Node traversalNode at each iteration
                trackParent = traversalNode;
                // If value of x is less than traversal node data we set traversal node as the left child
                if (x < traversalNode.getData()) {
                    traversalNode = traversalNode.getLeft();
                // If value of x is more than traversal node data we set traversal node as the right child
                } else if (x > traversalNode.getData()) {
                    traversalNode = traversalNode.getRight();
                } else {
                    // Duplicate value, do not insert
                    return root;
                }
            }
            // If trackParent is null we set root as the node we want to insert
            if (trackParent == null) {
                root = insertNewNode;
            // If it's not null
            // We check its values to determine where to set the node to
            } else if (x < trackParent.getData()) {
                trackParent.setLeft(insertNewNode);
            } else {
                trackParent.setRight(insertNewNode);
            }
            // Set the node we want to insert parent to trackParent
            insertNewNode.setParent(trackParent);

            // Fix Red-Black Tree properties
            // Checking that insertNewNode is not root and the color of the parent is red
            while (insertNewNode != root && insertNewNode.getParent().getColor() == Node.Color.RED) {
                // If the insertion node parent is the grandParent left subtree we proceed
                // This these two statements are the right and left side of the tree and
                // Cover the 4 cases of fixing RBT properties
                if (insertNewNode.getParent() == insertNewNode.getParent().getParent().getLeft()) {
                    // Assign a new node called y with insertion node grandparent right subtree
                    Node y = insertNewNode.getParent().getParent().getRight();
                    // If y is not null and the color of the node is red we continue
                    if (y != null && y.getColor() == Node.Color.RED) {
                        // Case 2: X.uncle is red
                        // We set the insertion parent colour as Black
                        insertNewNode.getParent().setColor(Node.Color.BLACK);
                        // Set insertion node grandparent subtree as Black
                        y.setColor(Node.Color.BLACK);
                        // Set the insertion node grandparent as Red
                        insertNewNode.getParent().getParent().setColor(Node.Color.RED);
                        // Set variable insertNewNode as insertNewNode's grandparent
                        insertNewNode = insertNewNode.getParent().getParent();
                    } else {
                        // If insertion node equals insertion parent right subtree
                        if (insertNewNode == insertNewNode.getParent().getRight()) {
                            // Case 3: X.uncle is black (triangle)
                            // Assign insertNewNode as insertion node parent
                            insertNewNode = insertNewNode.getParent();
                            // Rotate insertion node Left
                            rotateLeft(insertNewNode);
                        }
                        // Case 4: X.uncle is black (line)
                        // Set insertion parent colour as Black
                        insertNewNode.getParent().setColor(Node.Color.BLACK);
                        // Set the Grandparent as Red
                        insertNewNode.getParent().getParent().setColor(Node.Color.RED);
                        // Rotate insertion node grandparent right
                        rotateRight(insertNewNode.getParent().getParent());
                    }
                } else {
                    // Define Node Y as insertion node's grandparent left subtree
                    Node y = insertNewNode.getParent().getParent().getLeft();
                    // If y is not null and the colour is red we continue
                    if (y != null && y.getColor() == Node.Color.RED) {
                        // Case 2: X.uncle is red
                        // Set insertion node parent colour as Black
                        insertNewNode.getParent().setColor(Node.Color.BLACK);
                        // Set the colour of Node y as Black
                        y.setColor(Node.Color.BLACK);
                        // Set the grandparent of insertion node to colour Red
                        insertNewNode.getParent().getParent().setColor(Node.Color.RED);
                        // Set insertNewNode as insertion node Grandparent
                        insertNewNode = insertNewNode.getParent().getParent();
                    } else {
                        // If insertion node equals insertion node parent left subtree
                        if (insertNewNode == insertNewNode.getParent().getLeft()) {
                            // Case 3: X.uncle is black (triangle)
                            // Assign new node as insertion node parent
                            insertNewNode = insertNewNode.getParent();
                            // Rotate new node right
                            rotateRight(insertNewNode);
                        }
                        // Case 4: X.uncle is black (line)
                        // Set colour of insertion node parent as black
                        insertNewNode.getParent().setColor(Node.Color.BLACK);
                        // Set colour of insertion grandparent as Red
                        insertNewNode.getParent().getParent().setColor(Node.Color.RED);
                        // Rotate new node grandparent left
                        rotateLeft(insertNewNode.getParent().getParent());
                    }
                }
            }

            // Using flipColors when both children of insertNewNode are red
            if (isRed(insertNewNode.getLeft()) && isRed(insertNewNode.getRight())) {
                flipColors(insertNewNode);
            }
        }
        // Then we return root
        return root;
    }
    private Node rotateRight(Node h) {
        // Assign the Parent and Left Child
        Node parent = h.getParent();
        Node leftChild = h.getLeft();
        // Set left subtree as Leftchild right child
        h.setLeft(leftChild.getRight());
        // If LeftChild right child not null we set its parent as node h
        if (leftChild.getRight() != null) {
            leftChild.getRight().setParent(h);
        }
        // We set LeftChild right child as h
        leftChild.setRight(h);
        // We set Node h parent as leftChild
        h.setParent(leftChild);
        // If parent is null we set root as leftChild
        if (parent == null) {
            root = leftChild;
        // If the parent left child is h
        // We set parent left child as the variable leftChild
        } else if (parent.getLeft() == h) {
            parent.setLeft(leftChild);
        // Else we the parent right child as leftChild variable
        } else {
            parent.setRight(leftChild);
        }
        // We set the leftChild parent as parent variable
        leftChild.setParent(parent);
        // Then we return leftChild
        return leftChild;
    }

    private Node rotateLeft(Node h) {
        // Define Parent and rightChild variables
        Node parent = h.getParent();
        Node rightChild = h.getRight();
        // Set the node h right child as rightChild left child
        h.setRight(rightChild.getLeft());
        // If not rightChild left child not null
        // We set rightChild left child parent as node h
        if (rightChild.getLeft() != null) {
            rightChild.getLeft().setParent(h);
        }
        // Set rightChild left child as node h
        rightChild.setLeft(h);
        // Set node h parent as rightChild variable
        h.setParent(rightChild);
        // If parent is null we set the root as the rightChild variable
        if (parent == null) {
            root = rightChild;
        // If the parent variable left child is equal to node h
        // We set the parent variable left child as rightChild variable
        } else if (parent.getLeft() == h) {
            parent.setLeft(rightChild);
        // Else we set the parent right child as the variable rightChild
        } else {
            parent.setRight(rightChild);
        }
        // We set the rightChild parent as the variable parent
        rightChild.setParent(parent);
        // We then return rightChild
        return rightChild;
    }

    private void flipColors(Node h) {
        // We set the node h depending on the return of Node class method
        // flipColor which returns the opposite colour of the given color
        h.setColor(Node.flipColor(h.getColor()));
        // If Node h left child not null
        // We set the colour of the left child opposite of the current colour
        if (h.getLeft() != null) {
            h.getLeft().setColor(Node.flipColor(h.getLeft().getColor()));
        }
        // If Node h right child not null
        // We set the colour of the right child opposite of the current colour
        if (h.getRight() != null) {
            h.getRight().setColor(Node.flipColor(h.getRight().getColor()));
        }
    }
}
