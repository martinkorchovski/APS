package zad01;

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
        // Find which is any node of this CBHT contains an entry whose key is equal to targetKey.
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

class Lek {
    public String ime;
    public int zaliha;
    public int cena;
    public int kolicina;

    public Lek(String ime, int zaliha, int cena, int kolicina) {
        this.ime = ime;
        this.zaliha = zaliha;
        this.cena = cena;
        this.kolicina = kolicina;
    }

    boolean naracaj(int kolicina) {
        if (kolicina > this.kolicina) {
            return false;
        }
        this.kolicina -= kolicina;
        return true;
    }

    public String toString() {
        return String.format("%s\n%s\n%d\n%d", ime.toUpperCase(), zaliha == 1 ? "POZ" : "NEG", cena, kolicina);
    }
}

public class Apteka {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine().trim());

        CBHT<String, Lek> cbht = new CBHT<String, Lek>(N);

        for (int i = 0; i < N; i++) {
            String[] s = br.readLine().trim().split(" ");
            //.trim() na sekoe br.readLine() bidejki frla exception, i pri vnes na inputot da se vnimava na praznite mesta***
            Lek lek = new Lek(s[0], Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]));
            cbht.insert(lek.ime.toUpperCase(), lek);
        }

        while (true) {
            String line = br.readLine().trim();

            if (line.equals("KRAJ")) {
                break;
            }

            int kolicina = Integer.parseInt(br.readLine());
            SLLNode<MapEntry<String, Lek>> searchForKey = cbht.search(line.toUpperCase());

            if (searchForKey != null) {
                Lek lek = searchForKey.element.value;
                System.out.println(lek);

                if (lek.naracaj(kolicina)) {
                    System.out.println("Napravena naracka");
                } else {
                    System.out.println("Nema dovolno lekovi");
                }
            } else {
                System.out.println("Nema takov lek");
            }

        }
    }
}
