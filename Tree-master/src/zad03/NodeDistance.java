package zad03;

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

class NodeDistance {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int i;
        int N = Integer.parseInt(br.readLine());

        BNode<String>[] nodes = new BNode[N];
        BTree<String> tree = new BTree<>();

        for (i = 0; i < N; i++) {
            nodes[i] = null;
        }

        for (i = 0; i < N; i++) {
            String line = br.readLine();
            String[] parts = line.split(" ");
            int index = Integer.parseInt(parts[0]);
            nodes[index] = new BNode<>(parts[1]);
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

        int cases = Integer.parseInt(br.readLine());

        for (i = 0; i < cases; i++) {
            String line = br.readLine();
            String[] parts = line.split(" ");
            String from = parts[0];
            String to = parts[1];

            if (findNode(tree.getRoot(), from) == null || findNode(tree.getRoot(), to) == null) {
                System.out.println("ERROR");
            } else if (from.equals(to)) {
                System.out.println("0");
            } else {
                BNode<String> fromNode = findNode(tree.getRoot(), from);
                BNode<String> toNode = findNode(tree.getRoot(), to);

                int sumOfDistances = 2 * (getDepth(tree.getRoot(), fromNode, 0) + getDepth(tree.getRoot(), toNode, 0));
                int LCADistance = 2 * getDepth(tree.getRoot(), getLCA(tree.getRoot(), fromNode, toNode, 0), 0);

                System.out.println(sumOfDistances - LCADistance);

            }
        }

        br.close();
    }

    public static BNode<String> findNode(BNode<String> root, String from) {
        if (root == null) return null;

        if (root.info.equals(from)) return root;

        BNode<String> node = findNode(root.getLeft(), from);

        return node != null ? node : findNode(root.getRight(), from);
    }

    public static int getDepth(BNode<String> root, BNode<String> fromNode, int index) {
        if (root == null) return -1;

        if (root.info.equals(fromNode.info)) return index;

        int depth = getDepth(root.getLeft(), fromNode, index + 1);

        return depth != -1 ? depth : getDepth(root.getRight(), fromNode, index + 1);


    }

    private static BNode<String> getLCA(BNode<String> root, BNode<String> fromNode, BNode<String> toNode, int index) {
        if (root == null) return null;

        if (root == fromNode || root == toNode) return root;

        BNode<String> leftNode = getLCA(root.getLeft(), fromNode, toNode, index + 1);
        BNode<String> rightNode = getLCA(root.getRight(), fromNode, toNode, index + 1);

        if (leftNode != null && rightNode != null) return root;

        return leftNode != null ? leftNode : rightNode;
    }
}
