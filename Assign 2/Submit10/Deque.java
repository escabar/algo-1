import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by supatel on 2/10/2016.
 */


public class Deque<Item> implements Iterable<Item> {
    private int N; //size of the array
    private Node first;
    private Node last;
    private Node current;

    private class Node{
        private Item item;
        private Node next;
        private Node previous;
    }

    // construct an empty deque
    public Deque(){
        first = null;
        last = first;
        current = first;
        N = 0;
    }

    // is the deque empty?
    public boolean isEmpty(){ return first == null; }

    // return the number of items on the deque
    public int size(){ return N; }

    // add the item to the front
    public void addFirst(Item item){

        if (item != null) {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
            if (oldfirst != null) {
                oldfirst.previous = first;
            }
            if (oldfirst == null) {
                last = first;
            }
            current = first;
            N++;
        }else throw new NullPointerException("Cannot add null to Deque");
    }

    // add the item to the end
    public void addLast(Item item){

        if (item != null) {
            Node oldlast = last;
            last = new Node();
            last.item = item;
            last.previous = oldlast;
            if (oldlast != null) {
                oldlast.next = last;
            }
            if (oldlast == null) {
                first = last;
            }
            N++;
        }else throw new NullPointerException("Cannot add null to Deque");
    }

    // remove and return the item from the front
    public Item removeFirst(){
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        current = first;
        Item item = first.item;
        if (current.next != null) {
            first = current.next;
            current = null;
        }
        N--;
        if (N == 0){
            first = null;
            last = first;
        }
        return item;
    }

    // remove and return the item from the end
    public Item removeLast(){
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        current = last;
        Item item = last.item;
        if (current.previous !=null) {
            last = current.previous;
            current = null;
        }
        N--;
        if (N == 0){
                first = null;
                last = first;
        }
        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator(){ return new DequeIterator(); }

    private class DequeIterator implements Iterator<Item> {
        private  Node cur = first;
        public boolean hasNext() {
            return cur != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = cur.item;
            cur = cur.next;

            return item;
        }
    }

    // unit testing
    public static void main(String[] args){

    }
}