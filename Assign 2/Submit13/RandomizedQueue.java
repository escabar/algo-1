/**
 * Created by supatel on 2/10/2016.
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private int N;             // Size
    private Item[] a;         // array of item

    // construct an empty randomized queue
    public RandomizedQueue() {
        a = (Item[]) new Object[2];
        N = 0;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (N == a.length) resize(2 * a.length);    // double size of array if necessary
        if (item != null) {
            a[N++] = item;
        } else throw new NullPointerException("cannot add null to Queue");
    }

    // remove and return a random item
    public Item dequeue() {
        boolean getItem = false;
        Item item = null;
        Item last;

        if (isEmpty()) throw new NoSuchElementException("Stack underflow");

        do {
            int x = StdRandom.uniform(a.length);
            if (a[x] != null) {
                item = a[x];
                last = a[N-1];
                a[x] = last;
                a[N-1] = null;
                getItem = true;
                N--;
                if (N > 0 && N == a.length/4){resize(a.length/2);}
            }
        } while (!getItem);


        return item;
    }

    // return (but do not remove) a random item
    public Item sample() {
        boolean getItem;
        Item item = null;
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        do {
            getItem = false;
            int x = StdRandom.uniform(a.length);
            if (a[x] != null) {
                item = a[x];
                getItem = true;
            }
        } while (!getItem);

        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {return new RandomizedQueueIterator();}

    private class RandomizedQueueIterator implements Iterator<Item> {
        public int i;

        public RandomizedQueueIterator() {
            i = N -1 ;
        }

        public boolean hasNext() {
            return i > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[StdRandom.uniform(i--)];
        }
    }


    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        int j = 0;
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    // unit testing
   public static void main(String[] args){

       RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

       rq.enqueue(10);
       rq.enqueue(20);
       System.out.println(rq.sample());


   }
}
