package zad16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

class SLLNode<E> {
    protected E element;
    protected SLLNode<E> succ;

    public SLLNode(E elem, SLLNode<E> succ) {
        this.element = elem;
        this.succ = succ;
    }
}

class SLL<E> {
    private SLLNode<E> first;

    public SLL() {
        // Construct an empty SLL
        this.first = null;
    }

    public void deleteList() {
        first = null;
    }

    public int size() {
        int listSize = 0;
        SLLNode<E> tmp = first;
        while(tmp != null) {
            listSize++;
            tmp = tmp.succ;
        }
        return listSize;
    }

    @Override
    public String toString() {
        String ret = new String();
        if (first != null) {
            SLLNode<E> tmp = first;
            ret += tmp.element;
            while (tmp.succ != null) {
                tmp = tmp.succ;
                ret += "->" + tmp.element;
            }
        } else
            ret = "Prazna lista!!!";
        return ret;
    }

    public void insertFirst(E o) {
        SLLNode<E> ins = new SLLNode<E>(o, null);
        ins.succ = first;
        //SLLNode<E> ins = new SLLNode<E>(o, first);
        first = ins;
    }

    public void insertAfter(E o, SLLNode<E> node) {
        if (node != null) {
            SLLNode<E> ins = new SLLNode<E>(o, node.succ);
            node.succ = ins;
        } else {
            System.out.println("Dadenot jazol e null");
        }
    }
    public void insertBefore(E o, SLLNode<E> before) {

        if (first != null) {
            SLLNode<E> tmp = first;
            if(first==before){
                this.insertFirst(o);
                return;
            }
            //ako first!=before
            while (tmp.succ != before && tmp.succ!=null)
                tmp = tmp.succ;
            if (tmp.succ == before) {
                tmp.succ = new SLLNode<E>(o, before);;
            } else {
                System.out.println("Elementot ne postoi vo listata");
            }
        } else {
            System.out.println("Listata e prazna");
        }
    }

    public void insertLast(E o) {
        if (first != null) {
            SLLNode<E> tmp = first;
            while (tmp.succ != null)
                tmp = tmp.succ;
            tmp.succ = new SLLNode<E>(o, null);
        } else {
            insertFirst(o);
        }
    }

    public E deleteFirst() {
        if (first != null) {
            SLLNode<E> tmp = first;
            first = first.succ;
            return tmp.element;
        } else {
            System.out.println("Listata e prazna");
            return null;
        }
    }

    public E delete(SLLNode<E> node) {
        if (first != null) {
            SLLNode<E> tmp = first;
            if(first == node) {
                return this.deleteFirst();
            }
            while (tmp.succ != node && tmp.succ.succ != null)
                tmp = tmp.succ;
            if (tmp.succ == node) {
                tmp.succ = tmp.succ.succ;
                return node.element;
            } else {
                System.out.println("Elementot ne postoi vo listata");
                return null;
            }
        } else {
            System.out.println("Listata e prazna");
            return null;
        }

    }

    public SLLNode<E> getFirst() {
        return first;
    }

    public SLLNode<E> find(E o) {
        if (first != null) {
            SLLNode<E> tmp = first;
            while (!tmp.element.equals(o) && tmp.succ != null)
                tmp = tmp.succ;
            if (tmp.element.equals(o)) {
                return tmp;
            } else {
                System.out.println("Elementot ne postoi vo listata");
            }
        } else {
            System.out.println("Listata e prazna");
        }
        return null;
    }

    public void merge (SLL<E> in){
        if (first != null) {
            SLLNode<E> tmp = first;
            while(tmp.succ != null)
                tmp = tmp.succ;
            tmp.succ = in.getFirst();
        }
        else{
            first = in.getFirst();
        }
    }

    public void mirror() {
        if (first != null) {
            //m=nextsucc, p=tmp,q=next
            SLLNode<E> tmp = first;
            SLLNode<E> newsucc = null;
            SLLNode<E> next;

            while(tmp != null){
                next = tmp.succ;
                tmp.succ = newsucc;
                newsucc = tmp;
                tmp = next;
            }
            first = newsucc;
        }
    }
}

class Player implements Comparable<Player> {
    int number;
    int rating;
    int years;

    public Player(int number, int rating, int years) {
        this.number = number;
        this.rating = rating;
        this.years = years;
    }

    @Override
    public int compareTo(Player o) {
        if (o.rating > this.rating)
            return -1;
        if (o.rating < this.rating)
            return 1;
        if (o.rating == this.rating) {
            if (o.years > this.years)
                return 1;
            if (o.years < this.years)
                return -1;
        }
        return 0;
    }
}

class Football {
    public static void changePlayers(SLL<Player> representative_team, SLL<Player> under_21_team, int N) {
        for (int i = 0; i < N; i++) {
            SLLNode<Player> iteratorRepresentative = representative_team.getFirst().succ;
            SLLNode<Player> mostYears = representative_team.getFirst();
            while (iteratorRepresentative != null) {
                if (iteratorRepresentative.element.years >= mostYears.element.years) {
                    mostYears = iteratorRepresentative;
                }
                iteratorRepresentative = iteratorRepresentative.succ;
            }

            SLLNode<Player> iteratorUnder_21_Team = under_21_team.getFirst().succ;
            SLLNode<Player> bestPlayer = under_21_team.getFirst();

            while (iteratorUnder_21_Team != null) {
                if (iteratorUnder_21_Team.element.rating >= bestPlayer.element.rating) {
                    bestPlayer = iteratorUnder_21_Team;
                }
                iteratorUnder_21_Team = iteratorUnder_21_Team.succ;
            }

            representative_team.insertBefore(bestPlayer.element, mostYears);
            representative_team.delete(mostYears);
            under_21_team.delete(bestPlayer);
        }
    }

    public static void sort(SLL<Player> list) {
        SLLNode<Player> tmp1 = list.getFirst();
        while (tmp1 != null) {
            SLLNode<Player> tmp2 = tmp1.succ;
            while (tmp2 != null) {
                if (tmp1.element.compareTo(tmp2.element) < 0) {
                    Player pom = tmp1.element;
                    tmp1.element = tmp2.element;
                    tmp2.element = pom;
                }
                tmp2 = tmp2.succ;
            }

            tmp1 = tmp1.succ;
        }
    }


    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        SLL<Player> representative_team = new SLL<Player>();
        SLL<Player> under_21_team = new SLL<Player>();
        for (int i = 0; i < 11; i++) {
            String[] pom = bf.readLine().split(" ");
            Player p = new Player(Integer.parseInt(pom[0]), Integer.parseInt(pom[1]), Integer.parseInt(pom[2]));
            representative_team.insertLast(p);
        }

        for (int i = 0; i < 11; i++) {
            String[] pom = bf.readLine().split(" ");
            Player p = new Player(Integer.parseInt(pom[0]), Integer.parseInt(pom[1]), Integer.parseInt(pom[2]));
            under_21_team.insertLast(p);
        }

        sort(under_21_team);
        int N = Integer.parseInt(bf.readLine());

        changePlayers(representative_team, under_21_team, N);
        SLLNode<Player> tmp = representative_team.getFirst();

        while (tmp != null) {
            System.out.print(tmp.element.number + " ");
            tmp = tmp.succ;
        }
    }
}
