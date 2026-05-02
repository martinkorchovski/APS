package zad01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class BNode<E> {
    public E info;
    public BNode<E> left;
    public BNode<E> right;
    char ltag;
    char rtag;
    static int LEFT = 1;
    static int RIGHT = 2;

    public BNode(E info) {
        this.info = info;
        left = null;
        right = null;
        ltag = '-';
        rtag = '-';
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

    public char getLtag() {
        return ltag;
    }

    public char getRtag() {
        return rtag;
    }

    public static int getLEFT() {
        return LEFT;
    }

    public static int getRIGHT() {
        return RIGHT;
    }
}

class BTree<E> {
    public BNode<E> head;

    public BTree() {
        head = new BNode<E>(null);
        // po definicija ako nema koren, t.e. ako stebloto e prazno
        head.left = head;
        head.ltag = '-';
        // kaj vodacot sekogas desnata vrska pokazuva kon samiot sebe
        head.right = head;
        head.rtag = '+';
    }

    public BNode<E> getHead() {
        return head;
    }

    public BNode<E> makeRoot(E elem) {
        BNode<E> tmp = new BNode<E>(elem);
        head.left = tmp;
        head.ltag = '+';

        tmp.left = head;
        tmp.ltag = '-';
        tmp.right = head;
        tmp.rtag = '-';

        return tmp;
    }

    public BNode<E> makeRootNode(BNode<E> tmp) {
        head.left = tmp;
        head.ltag = '+';

        tmp.left = head;
        tmp.ltag = '-';
        tmp.right = head;
        tmp.rtag = '-';

        return tmp;
    }

    public BNode<E> addChild(BNode<E> node, int where, E elem) {
        BNode<E> tmp = new BNode<E>(elem);

        if (where == BNode.LEFT) {

            if (node.ltag == '+') // veke postoi element
            {
                return null;
            }

            tmp.left = node.left;
            tmp.ltag = '-';
            tmp.right = node;
            tmp.rtag = '-';
            node.left = tmp;
            node.ltag = '+';
        } else {

            if (node.rtag == '+') // veke postoi element
            {
                return null;
            }

            tmp.right = node.right;
            tmp.rtag = '-';
            tmp.left = node;
            tmp.ltag = '-';
            node.right = tmp;
            node.rtag = '+';
        }

        return tmp;
    }

    public BNode<E> addChildNode(BNode<E> node, int where, BNode<E> tmp) {

        if (where == BNode.LEFT) {

            if (node.ltag == '+') // veke postoi element
            {
                return null;
            }

            tmp.left = node.left;
            tmp.ltag = '-';
            tmp.right = node;
            tmp.rtag = '-';
            node.left = tmp;
            node.ltag = '+';
        } else {

            if (node.rtag == '+') // veke postoi element
            {
                return null;
            }

            tmp.right = node.right;
            tmp.rtag = '-';
            tmp.left = node;
            tmp.ltag = '-';
            node.right = tmp;
            node.rtag = '+';
        }

        return tmp;
    }

    public BNode<E> insertRight(BNode<E> parent, E info) {

        BNode<E> child = new BNode<E>(info);

        child.ltag = '-';
        child.left = parent;
        child.rtag = parent.rtag;
        child.right = parent.right;

        parent.right = child;
        parent.rtag = '+';

        if (child.rtag == '+') {
            BNode<E> temp = child.right;
            while (temp.ltag == '+') {
                temp = temp.left;
            }
            temp.left = child;
        }

        return child;
    }

    public BNode<E> predecessorInorder(BNode<E> node) {

        if (node.ltag == '-') {
            return node.left;
        }

        BNode<E> p = node.left;
        while (p.rtag == '+') {
            p = p.right;
        }

        return p;
    }

    public BNode<E> successorInorder(BNode<E> node) {

        if (node.rtag == '-') {
            return node.right;
        }

        BNode<E> p = node.right;
        while (p.ltag == '+') {
            p = p.left;
        }

        return p;
    }

    public int getNumberOfRelations() {
        int counter = 0;

        BNode<E> node = head;
        node = successorInorder(node);

        while (node != head) {
            if (node.ltag == '+' && node.left.ltag == '+') {
                counter++;
            }

            if (node.ltag == '+' && node.left.rtag == '+') {
                counter++;
            }

            if (node.rtag == '+' && node.right.ltag == '+') {
                counter++;
            }

            if (node.rtag == '+' && node.right.rtag == '+') {
                counter++;
            }

            node = successorInorder(node);
        }

        return counter;
    }
}


class Relations {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int i;

        BNode<Integer>[] nodes = new BNode[N];
        BTree<Integer> tree = new BTree<>();

        for (i = 0; i < N; i++) {
            nodes[i] = null;
        }

        for (i = 0; i < N; i++) {
            String line = br.readLine(); // 1 2 LEFT 0
            String[] parts = line.split(" "); // [1, 2, LEFT, 0]
            int index = Integer.parseInt(parts[0]);
            int value = Integer.parseInt(parts[1]);
            // od ecode
            // nodes[index] = new zad01.BNode<>(Integer.parseInt(parts[1]));
            String action = parts[2];

//            od ecode -> ne raboti
//            if (acion.equals("LEFT")) {
//                int parentIndex = Integer.parseInt(parts[3]);
//                tree.addChild(nodes[parentIndex], zad01.BNode.getLEFT(), nodes[index].getInfo());
//            } else if (acion.equals("RIGHT")) {
//                int parentIndex = Integer.parseInt(parts[3]);
//                tree.addChild(nodes[parentIndex], zad01.BNode.getRIGHT(), nodes[index].getInfo());
//            } else {
//                tree.makeRootNode(nodes[index]);
//            }


            if (action.equals("ROOT")) {
                nodes[index] = new BNode<>(value);
                tree.makeRootNode(nodes[index]);
            } else {
                int parentIndex = Integer.parseInt(parts[3]);
                if (action.equals("LEFT")) {
                    nodes[index] = tree.addChild(nodes[parentIndex], BNode.getLEFT(), value);
                } else if (action.equals("RIGHT")) {
                    nodes[index] = tree.addChild(nodes[parentIndex], BNode.getRIGHT(), value);
                }
            }
        }
        System.out.println(tree.getNumberOfRelations());

        br.close();
    }
}

