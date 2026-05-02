package zad03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class SLLNode<E> {
    protected E element;
    protected SLLNode<E> succ;

    public SLLNode(E elem, SLLNode<E> succ) {
        this.element = elem;
        this.succ = succ;
    }

    @Override
    public String toString() {
        return element.toString();
    }
}

class MapEntry<K extends Comparable<K>, E> {
    // Each MapEntry object is a pair consisting of a key (a Comparable object)
    // and a value (an arbitrary object).
    K key;
    E value;

    public MapEntry(K key, E val) {
        this.key = key;
        this.value = val;
    }

    public String toString() {
        return "<" + key + "," + value + ">";
    }
}

class CBHT<K extends Comparable<K>, E> {

    // An object of class CBHT is a closed-bucket hash table, containing
    // entries of class MapEntry.
    public SLLNode<MapEntry<K, E>>[] buckets;

    @SuppressWarnings("unchecked")
    public CBHT(int m) {
        // Construct an empty CBHT with m buckets.
        buckets = (SLLNode<MapEntry<K, E>>[]) new SLLNode[m];
    }

    private int hash(K key) {
        // Translate key to an index of the array buckets.
        return Math.abs(key.hashCode()) % buckets.length;
    }

    public SLLNode<MapEntry<K, E>> search(K targetKey) {
        // Find which if any node of this CBHT contains an entry whose key is equal to targetKey.
        // Return a link to that node (or null if there is none).
        int b = hash(targetKey);
        SLLNode<MapEntry<K, E>> currNode = buckets[b];
        while (currNode != null) {
            MapEntry<K, E> currEntry = currNode.element;
            if (currEntry.key.equals(targetKey)) return currNode;
            else currNode = currNode.succ;
        }
        return null;
    }

    public void insert(K key, E val) {
        // Insert the entry <key, val> into this CBHT.
        // If entry with same key exists, overwrite it.
        MapEntry<K, E> newEntry = new MapEntry<>(key, val);
        int b = hash(key);
        SLLNode<MapEntry<K, E>> currNode = buckets[b];
        while (currNode != null) {
            MapEntry<K, E> currEntry = currNode.element;
            if (currEntry.key.equals(key)) {
                // Make newEntry replace the existing entry ...
                currNode.element = newEntry;
                return;
            } else currNode = currNode.succ;
        }
        // Insert newEntry at the front of the SLL in bucket b ...
        buckets[b] = new SLLNode<>(newEntry, buckets[b]);
    }

    public void delete(K key) {
        // Delete the entry (if any) whose key is equal to key from this CBHT.
        int b = hash(key);
        SLLNode<MapEntry<K, E>> predNode = null, currNode = buckets[b];
        while (currNode != null) {
            MapEntry<K, E> currEntry = currNode.element;
            if (currEntry.key.equals(key)) {
                if (predNode == null) buckets[b] = currNode.succ;
                else predNode.succ = currNode.succ;
                return;
            } else {
                predNode = currNode;
                currNode = currNode.succ;
            }
        }
    }

    public String toString() {
        String temp = "";
        for (int i = 0; i < buckets.length; i++) {
            temp += i + ":";
            SLLNode<MapEntry<K, E>> curr = buckets[i];
            while (curr != null) {
                temp += curr.element.toString() + " ";
                curr = curr.succ;
            }
            temp += "\n";
        }
        return temp;
    }
}

class Pacient {
    public String opstina;
    public String prezime;
    public String sostojba;

    public Pacient(String opstina, String prezime, String sostojba) {
        this.opstina = opstina;
        this.prezime = prezime;
        this.sostojba = sostojba;
    }
}

public class Korona {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine().trim());

        CBHT<String, Pacient> cbht = new CBHT<String, Pacient>(2 * N);

        for (int i = 0; i < N; i++) {
            String[] s = br.readLine().trim().split(" ");
            Pacient pacient = new Pacient(s[0], s[1], s[2]);
            // ---
            //od ecode - netocno
            //cbht.insert(s[0], pacient);
            // ---
            // Уникатен клуч дури и за пациенти со исто презиме
            String key = s[0] + "_" + s[1] + "_" + i;
            cbht.insert(key, pacient);
        }

        String opstina = br.readLine();
        SLLNode<MapEntry<String, Pacient>> current = cbht.search(opstina);

        int vkupno = 0;
        int pozitivni = 0;
        float rezultat = 0;


//        *netocno, od ecode
//        if(current != null) {
//            while(current != null) {
//                vkupno++;
//                if(current.element.value.sostojba.equals("позитивен")) {
//                    pozitivni++;

//                }
//
//                current = current.succ;
//            }
//        }

        for (int i = 0; i < cbht.buckets.length; i++) {
            SLLNode<MapEntry<String, Pacient>> node = cbht.buckets[i];
            while (node != null) {
                Pacient p = node.element.value;
                if (p.opstina.equals(opstina)) {
                    vkupno++;
                    if (p.sostojba.equals("позитивен")) {
                        pozitivni++;
                    }
                }
                node = node.succ;
            }
        }
        rezultat = (float) pozitivni / vkupno;
        System.out.println(rezultat);
    }
}