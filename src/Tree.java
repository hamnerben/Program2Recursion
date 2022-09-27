// ******************ERRORS********************************
// Throws UnderflowException as appropriate


import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;  // did I accidentally import this?

class UnderflowException extends RuntimeException {
    /**
     * Construct this exception object.
     *
     * @param message the error message.
     */
    public UnderflowException(String message) {
        super(message);
    }
}



public class Tree<E extends Comparable<? super E>> {


    /**
     * Class used in deepestNode() to pass information with each recursive call
     * depth: the depth the node was found
     * node: a BinaryNode to pass back
     */
    class NodeInfo {
        BinaryNode<E> node;
        int depth;

        public NodeInfo(BinaryNode<E> node, int depth){
            this.node = node;
            this.depth = depth;
        }
        public NodeInfo(BinaryNode<E> node){
            this.node = node;
        }
    }

    /**
     * Class used in countBST() to pass information with each recursive call
     * trees: how many BST trees exist within the tree
     * stillOrdered: if all below nodes follow with an ordered BST
     */
    class SubBSTInfo {
        int trees;
        boolean stillOrdered;

        public SubBSTInfo(int trees, boolean stillOrdered){
            this.trees = trees;
            this.stillOrdered = stillOrdered;
        }

    }

    private BinaryNode<E> root;  // Root of tree
    private String treeName;     // Name of tree

    public static void main(String[] args){
        Integer a = 10;
        Integer b = 14;

        System.out.println(b.compareTo(a));
    }


    /**
     * Create an empty tree
     * @param label Name of tree
     */
    public Tree(String label) {
        treeName = label;
        root = null;
    }

    /**
     * Create non ordered tree from list in preorder
     * @param arr   List of elements
     * @param label Name of tree
     */
    public Tree(E[] arr, String label, boolean ordered) {
        treeName = label;
        if (ordered) {
            root = null;
            for (int i = 0; i < arr.length; i++) {
                bstInsert(arr[i]);
            }
        } else root = buildUnordered(arr, 0, arr.length - 1);
    }

    /**
     * Build a NON BST tree by preorder
     * @param arr nodes to be added
     * @return new tree
     */
    private BinaryNode<E> buildUnordered(E[] arr, int low, int high) {
        if (low > high) return null;
        int mid = (low + high) / 2;
        BinaryNode<E> curr = new BinaryNode<>(arr[mid], null, null);
        curr.left = buildUnordered(arr, low, mid - 1);
        curr.right = buildUnordered(arr, mid + 1, high);
        return curr;
    }
    /**
     * Create BST from Array
     * @param arr   List of elements to be added
     * @param label Name of  tree
     */
    public Tree(E[] arr, String label) {
        root = null;
        treeName = label;
        for (int i = 0; i < arr.length; i++) {
            bstInsert(arr[i]);
        }
    }

    /**
     * Change name of tree
     * @param name new name of tree
     */
    public void changeName(String name) {
        this.treeName = name;
    }

    /**
     * Return a string displaying the tree contents as a tree with one node per line
     */
    public String toString() {
        if (root == null)
            return (treeName + " Empty tree\n");
        else
            return treeName+"\n" + toString(root,"");
    }

    /** internal method to go through each node and store its value to print it out
     * this runs at O(n)
     * @param t the current node being inspected
     * @param indent how many indentations need to happen
     * @return the string representation of the tree
     */

    private String toString(BinaryNode<E> t, String indent) {
        if (t == null) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(toString(t.right, indent + " "));
        sb.append(indent + t.element.toString() + "\n");
        sb.append(toString(t.left, indent + " "));
        return sb.toString();

    }
    /**
     * Return a string displaying the tree contents as a single line
     */
    public String toString2() {
        if (root == null)
            return treeName + " Empty tree";
        else
            return treeName + " " + toString2(root);
    }

    /**
     * Internal method to return a string of items in the tree in order
     * This routine runs in O(??)
     *
     * @param t the node that roots the subtree.
     */
    private String toString2(BinaryNode<E> t) {
        if (t == null) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(toString2(t.left));
        sb.append(t.element.toString() + " ");
        sb.append(toString2(t.right));
        return sb.toString();
    }

    /** Finds and returns the deepest node
     *
     * @return the deepest node
     */

    public E deepestNode() {
        return deepestNode(root, 0).node.element;
    }

    /**
     * Internal method to find the deepest node
     * runs at O(n)
     * @param node  the current node being inspected
     * @param depth the depth of the current node
     * @return NodeInfo object containing a node and the depth of the node
     */

    private NodeInfo deepestNode(BinaryNode<E> node, int depth){
        NodeInfo leftBiggest = null;
        NodeInfo rightBiggest = null;
        if (node.left != null){
            leftBiggest = deepestNode(node.left, depth + 1);
        }
        if (node.right != null){
            rightBiggest = deepestNode(node.right, depth + 1);
        }
        if (leftBiggest != null && rightBiggest != null){  // if both children exist, return the greater
            if (leftBiggest.depth > rightBiggest.depth){
                return leftBiggest;
            }
            else return rightBiggest;    // right wins ties
        }
        else if(leftBiggest != null || rightBiggest != null){ // if one child exists
            if (leftBiggest != null) return leftBiggest;
            else return rightBiggest;
        }
        else return new NodeInfo(node, depth);  // no children nodes exist

    }

    /**
     * reverse left and right children recursively
     */
    public void flip() {
        flip(root);
    }

    /**
     * internal method to flip the left and right children recursively
     * this runs at O(n)
     * @param t the current node to flip the children
     */
    private void flip(BinaryNode<E> t){
        if (t == null) return;
        // flip the children
        BinaryNode<E> temp = t.left;
        t.left = t.right;
        t.right = temp;
        // call flip on the children
        flip(t.left);
        flip(t.right);
    }

    /**
     * Counts number of nodes in specified level
     * @param level Level in tree, root is zero
     * @return count of number of nodes at specified level
     */
    public int nodesInLevel(int level) {
        return nodesInLevel(root, level, 0);
    }

    /**
     * internal method to count the number of nodes in specific level
     * runs at O(n)
     * @param node the current node being inspected
     * @param target the level to count all the nodes
     * @param depth the depth of the current node
     * @return the number of nodes at the targetLevel
     */

    private int nodesInLevel(BinaryNode<E> node, int target, int depth) {
        if(node == null) return 0;
        if(depth > target) return 0;
        if(depth == target) return 1;
        int left = nodesInLevel(node.left, target, depth + 1);
        int right = nodesInLevel(node.right, target, depth + 1);
        return left + right;
    }

    /**
     * Print all paths from root to leaves
     */
    public void printAllPaths() {
        printAllPaths(root,new StringBuilder(""));
    }

    /**
     * internal method that helps print all paths from root to leaves
     * runs at O(n)
     * @param node
     * @param toPrint
     */
    private void printAllPaths(BinaryNode<E> node, StringBuilder toPrint){
        toPrint.append(node.element + " ");
        if(node.left == null && node.right == null){  // leaf node found
            System.out.println(toPrint);
        }
        if(node.left != null){
            printAllPaths(node.left, new StringBuilder(toPrint));  // left subtree
        }
        if(node.right != null){
            printAllPaths(node.right, new StringBuilder(toPrint));  // right subtree
        }
    }

    /**
     * Counts all non-null binary search trees embedded in tree
     * @return Count of embedded binary search trees
     */
    public Integer countBST() {
        return countBST(root).trees;
    }

    /** internal method to count all non_null binary search trees
     * runs at O(n^2)
     * @param node the current node being accessed
     * @return treeInfo object
     */

    private SubBSTInfo countBST(BinaryNode<E> node){
        SubBSTInfo treeInfo = new SubBSTInfo(0, true);
        if(node == null) return treeInfo;
        SubBSTInfo rTreeInfo = countBST(node.right);
        SubBSTInfo lTreeInfo = countBST(node.left);
        boolean childrenCorrect = true;
        if(node.left != null){  // check if the immediate left child is correct
            childrenCorrect = (node.element.compareTo(node.left.element) > 0);
        }
        if(node.right != null){ // check if the immediate right child is correct
            childrenCorrect = (childrenCorrect && (node.element.compareTo(node.right.element) < 0));
        }
        // if the left subtree, right subtree, and immediate children are correct, true
        treeInfo.stillOrdered = (rTreeInfo.stillOrdered && lTreeInfo.stillOrdered && childrenCorrect);
        treeInfo.trees = rTreeInfo.trees + lTreeInfo.trees;
        if(treeInfo.stillOrdered){
            treeInfo.trees++;
        }
    return treeInfo;
    }


    /**
     * Insert into a bst tree; duplicates are allowed
     * @param x the item to insert.
     */
    public void bstInsert(E x) {

        root = bstInsert(x, root);
    }

    /**
     * Internal method to insert into a subtree.
     * In tree is balanced, this routine runs in O(log n)
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<E> bstInsert(E x, BinaryNode<E> t) {
        if (t == null)
            return new BinaryNode<E>(x, null, null);
        int compareResult = x.compareTo(t.element);
        if (compareResult < 0) {
            t.left = bstInsert(x, t.left);
        } else {
            t.right = bstInsert(x, t.right);
        }
        return t;
    }

    /**
     * Determines if item is in tree
     * @param item the item to search for.
     * @return true if found.
     */
    public boolean contains(E item) {
        return contains(item, root);
    }

    /**
     * Internal method to find an item in a subtree.
     * This routine runs in O(log n) as there is only one recursive call that is executed and the work
     * associated with a single call is independent of the size of the tree: a=1, b=2, k=0
     *
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     * @return node containing the matched item.
     */
    private boolean contains(E x, BinaryNode<E> t) {
        if (t == null)
            return false;

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            return contains(x, t.left);
        else if (compareResult > 0)
            return contains(x, t.right);
        else {
            return true;    // Match
        }
    }
    /**
     * Remove all paths from tree that sum to less than given value
     * @param sum: minimum path sum allowed in final tree
     */
    public void pruneK(Integer sum) {
        prune((BinaryNode<Integer>) root, sum, 0);
    }


    /** Internal method to remove all paths from a tree that sum ot less than the given value
     * this method runs at O(nlog(n))
     * @param node: the current node being inspected
     * @param TARGET_SUM: the sum that the nodes must equate to
     * @param pathSum: the sum of from the path from the leaf to the current node
     * @return the sum of the path
     */
    private int prune(BinaryNode<Integer> node, Integer TARGET_SUM, int pathSum){
        if(node == null) return pathSum;
        int left = prune(node.left, TARGET_SUM, pathSum + node.element);
        int right = prune(node.right, TARGET_SUM, pathSum + node.element);
        if(left < TARGET_SUM){
            node.left = null;  // left child is too small
        }
        if(right < TARGET_SUM){
            node.right = null;  // left child is too small
        }
        return Math.max(left, right);
    }

    /**
     * Build tree given inOrder and preOrder traversals.  Each value is unique
     * @param inOrder  List of tree nodes in inorder
     * @param preOrder List of tree nodes in preorder
     */
    public void buildTreeTraversals(E[] inOrder, E[] preOrder) {
        root = null;
    }

    /**
     * Find the least common ancestor of two nodes
     * @param a first node
     * @param b second node
     * @return String representation of ancestor
     */
    public String lca(E a, E b) {
        BinaryNode<E> ancestor = null;
        if (a.compareTo(b) < 0) {
            ancestor = lca(root, a, b);
        } else {
            ancestor = lca(root, b, a);
        }
        if (ancestor == null) return "none";
        else return ancestor.toString();
    }

    /** Internal method to find the least common ancestor of two nodes
     * this method runs at O(log(n))
     * @param node: the node being inspected
     * @param a: the first node
     * @param b: the second node
     * @return the least common ancestor of a and b
     */

    private BinaryNode<E> lca(BinaryNode<E> node, E a, E b){
        BinaryNode<E> ancestor = null;
        if(node.element != a){  // still looking for `a`
            if(a.compareTo(node.element) < 0){
                ancestor = lca(node.left, a, b);

            }
            else
                ancestor = lca(node.right, a, b);
        }
        if(contains(b, node) && ancestor == null) { // if the above lca() call found the ancestor
            ancestor = node;                        // already, skip this portion
        }
        return ancestor;
        }


    /**
     * Balance the tree
     */
    public void balanceTree() {
        ArrayList<E> sorted = balanceTree(root, new ArrayList<E>());  // does a binary traversal to sort the elements
        root = null;
        System.out.println(sorted);
        balanceTree(sorted, 0, sorted.size() - 1);
    }


    /**
     * Creates a sorted list of keys by inorder traversal
     * runs at O(n)
     * @param node current node being traversed
     * @param sorted ArrayList<E> containing keys
     * @return ArrayList<E> of keys ordered
     */
    private ArrayList<E> balanceTree(BinaryNode<E> node, ArrayList<E> sorted) {
        if(node == null) return sorted;
        sorted = balanceTree(node.left, sorted);
        sorted.add(node.element);
        sorted = balanceTree(node.right, sorted);
        return sorted;
    }

    /**
     * second internal method that creates a balanced bst
     * by inserting the nodes in a specific order
     * this runs at O(n)
     * @param sorted ArrayList<E> containing keys
     * @param start the beginning index of the slice of sorted
     * @param end the end index of the slice of sorted
     */

    private void balanceTree(ArrayList<E> sorted, int start, int end){
        if(start >= end){
            bstInsert(sorted.get(start));
            return;
        }
        int middle = (start + end) / 2;
        bstInsert(sorted.get(middle));
        balanceTree(sorted, start, middle - 1);
        balanceTree(sorted, middle + 1, end);
        return;
    }

    /**
     * In a BST, keep only nodes between range
     *
     * @param a lowest value
     * @param b highest value
     */
    public void keepRange(E a, E b) {
        root = keepRange(root, a, b);
    }

    /**
     * internal method to keep only the nodes in the range
     * runs at O(log(n))
     * @param node the current node being inspected
     * @param a the lower number the range to be kept
     * @param b the higher number in the range to be kept
     * @return the root of the new tree containing only nodes in the range
     */
    private BinaryNode<E> keepRange(BinaryNode<E> node, E a, E b){
        if(node == null) return null;
        else if(node.element.compareTo(b) <= 0 && node.element.compareTo(a) >= 0){
            node.left = keepRange(node.left, a, b);
            node.right = keepRange(node.right, a, b);
            return node;
        }

        else if(node.element.compareTo(a) < 0){
            return keepRange(node.right, a, b);
        }
        else if(node.element.compareTo(b) > 0){
            return keepRange(node.left, a, b);
        }
        return node;
    }

    // Basic node stored in unbalanced binary  trees
    private static class BinaryNode<E> {
        E element;            // The data in the node
        BinaryNode<E> left;   // Left child
        BinaryNode<E> right;  // Right child

        // Constructors
        BinaryNode(E theElement) {
            this(theElement, null, null);
        }

        BinaryNode(E theElement, BinaryNode<E> lt, BinaryNode<E> rt) {
            element = theElement;
            left = lt;
            right = rt;
        }

        // toString for BinaryNode
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Node:");
            sb.append(element);
            return sb.toString();
        }

    }

}
