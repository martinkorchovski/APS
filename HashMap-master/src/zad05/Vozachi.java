package zad05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

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

class Vozach implements Comparable<Vozach> {
    public String tablica;
    public String ime;
    public String prezime;
    public String vreme;

    public Vozach(String tablica, String ime, String prezime, String vreme) {
        this.tablica = tablica;
        this.ime = ime;
        this.prezime = prezime;
        this.vreme = vreme;
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }

    @Override
    public int compareTo(Vozach o) {
        return this.vreme.compareTo(o.vreme);
    }
}

public class Vozachi {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine().trim());

        CBHT<String, Vozach> cbht = new CBHT<String, Vozach>(2 * N);

        for (int i = 0; i < N; i++) {
            String[] s = br.readLine().trim().split(" ");
            Vozach vozach = new Vozach(s[0], s[1], s[2], " ");
            cbht.insert(s[0], vozach);
        }

        String dozvolenaBrzina = br.readLine().trim();
        String[] podatociOdMerenje = br.readLine().trim().split(" ");
        ArrayList<Vozach> vozachi = new ArrayList<>();

        for (int i = 0; i < podatociOdMerenje.length - 3; i += 3) {
            String tablica = podatociOdMerenje[i];
            String brzina = podatociOdMerenje[i + 1];
            String vreme = podatociOdMerenje[i + 2];

            SLLNode<MapEntry<String, Vozach>> current = cbht.search(tablica);

            if (current != null) {
                if (Integer.parseInt(brzina) > Integer.parseInt(dozvolenaBrzina)) {
                    Vozach v = new Vozach(current.element.value.tablica, current.element.value.ime,
                            current.element.value.prezime, vreme);
                    vozachi.add(v);
                }
            }
        }

        Collections.sort(vozachi);

        for (Vozach v : vozachi) {
            System.out.println(v);
        }
    }
}