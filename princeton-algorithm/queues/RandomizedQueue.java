import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    // initial capacity of underlying resizing array
    private static final int INIT_CAPACITY = 8;

    private Item[] a;         // array of items
    private int n;            // number of elements on stack


    // construct an empty randomized queue
    public RandomizedQueue() {
        a = (Item[]) new Object[INIT_CAPACITY];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= n;

        // textbook implementation
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = a[i];
        }
        a = copy;

        // alternative implementation
        // a = java.util.Arrays.copyOf(a, capacity);
    }
    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("empty item");
        }
        if (n == a.length) resize(2*a.length);    // double size of array if necessary
        a[n++] = item;                            // add item
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        // shrink size of array if necessary
        if (n == a.length/4) resize(a.length/2);

        int randomIndex = StdRandom.uniformInt(n);
        Item removedItem = a[randomIndex];
        a[randomIndex] = a[n-1];
        a[n-1] = null;
        n--;
        // StdOut.println(removedItem.toString());
        return removedItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        int randomIndex = StdRandom.uniformInt(n);
        Item removedItem = a[randomIndex];
        return removedItem;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        RandomQueueIterator iterator = new RandomQueueIterator<Item>();
        // StdOut.println("iterator size: " + iterator.remain_size);
        // int test_size = iterator.remain_size;
        return iterator;
    }

    // a array iterator, in reverse order
    private class RandomQueueIterator<I> implements Iterator<Item> {
        private Item[] b;
        private int remainSize;

        public RandomQueueIterator() {
            remainSize = n;
            b = (Item[]) new Object[n];
            for (int i = 0; i < n; i++) {
                b[i] = a[i];
            };
        }

        public boolean hasNext() {
            // StdOut.println("iterator remain size: " + remain_size);
            return remainSize > 0;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            int randomIndex = StdRandom.uniformInt(remainSize);
            Item removedItem = b[randomIndex];
            b[randomIndex] = b[remainSize -1];
            b[remainSize -1] = null;
            remainSize--;
            return removedItem;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("cannot remove");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();

        // Test isEmpty() and size() on an empty queue
        assert rq.isEmpty();
        assert rq.size() == 0;

        // Test enqueue() and size()
        rq.enqueue(1);
        assert !rq.isEmpty();
        assert rq.size() == 1;

        // Test sample() and dequeue()
        int sample = rq.sample();
        int removed = rq.dequeue();
        assert sample == removed;
        assert rq.isEmpty();
        assert rq.size() == 0;

        // Test enqueue() and iterator()
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        Iterator<Integer> iter = rq.iterator();
        int sum = 0;
        while (iter.hasNext()) {
            sum += iter.next();
        }
        assert sum == 6;

        // Test dequeue() and iterator()
        rq.dequeue();
        rq.dequeue();
        iter = rq.iterator();
        assert iter.hasNext();
    }

}