package zad02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class BNode<E> {

    public E info;
    public BNode<E> left;
    public BNode<E> right;

    static int LEFT = 1;
    static int RIGHT = 2;

    public BNode(E info) {
        this.info = info;
        left = null;
        right = null;
    }

    public BNode() {
        this.info = null;
        left = null;
        right = null;
    }

    public BNode(E info, BNode<E> left, BNode<E> right) {
        this.info = info;
        this.left = left;
        this.right = right;
    }

    public E getInfo() {
        return info;
    }

    public BNode<E> getLeft() {
        return left;
    }

    public BNode<E> getRight() {
        return right;
    }

    public static int getLEFT() {
        return LEFT;
    }

    public static int getRIGHT() {
        return RIGHT;
    }
}

class BTree<E extends Comparable<E>> {

    public BNode<E> root;

    public BTree() {
        root = null;
    }

    public BTree(E info) {
        root = new BNode<E>(info);
    }

    public void makeRoot(E elem) {
        root = new BNode(elem);
    }

    public void makeRootNode(BNode<E> node) {
        root = node;
    }

    public BNode<E> getRoot() {
        return root;
    }

    public BNode<E> addChild(BNode<E> node, int where, E elem) {

        BNode<E> tmp = new BNode<E>(elem);

        if (where == BNode.LEFT) {
            if (node.left != null)  // veke postoi element
                return null;
            node.left = tmp;
        } else {
            if (node.right != null) // veke postoi element
                return null;
            node.right = tmp;
        }

        return tmp;
    }

    public BNode<E> addChildNode(BNode<E> node, int where, BNode<E> tmp) {

        if (where == BNode.LEFT) {
            if (node.left != null)  // veke postoi element
                return null;
            node.left = tmp;
        } else {
            if (node.right != null) // veke postoi element
                return null;
            node.right = tmp;
        }

        return tmp;
    }
}

class ValidityCheck {
    public static void main(String[] args) throws IOException {
        int i;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        BNode<Integer>[] nodes = new BNode[N];
        BTree<Integer> tree = new BTree<>();

        for (i = 0; i < N; i++) {
            nodes[i] = null;
        }

        for (i = 0; i < N; i++) {
            String line = br.readLine();
            String[] parts = line.split(" ");
            int index = Integer.parseInt(parts[0]);
            nodes[index] = new BNode<>(Integer.parseInt(parts[1]));
            String action = parts[2];

            if (action.equals("LEFT")) {
                int parentIndex = Integer.parseInt(parts[3]);
                tree.addChildNode(nodes[parentIndex], BNode.getLEFT(), nodes[index]);
            } else if (action.equals("RIGHT")) {
                int parentIndex = Integer.parseInt(parts[3]);
                tree.addChildNode(nodes[parentIndex], BNode.getRIGHT(), nodes[index]);
            } else {
                tree.makeRootNode(nodes[index]);
            }
        }

        System.out.println(validTree(tree.getRoot()));
    }

    private static boolean validTree(BNode<Integer> root) {
        if (root == null) {
            return true;
        }

        if ((root.left != null && root.left.info > root.info) || (root.right != null && root.right.info > root.info)) {
            return false;
        }

        return validTree(root.left) && validTree(root.right);
    }
}
