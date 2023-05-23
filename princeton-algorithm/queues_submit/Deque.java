import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int n;          // size of the Deque
    private Node first;     // first of Deque
    private Node last;     // last of Deque

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    // construct an empty deque
    public Deque() {
        n = 0;
        first = null;
        last = null;
    };

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    };

    // return the number of items on the deque
    public int size() {
        return n;
    };

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }

        Node old_first = first;

        Node new_first = new Node();
        new_first.next = first;
        new_first.prev = null;
        new_first.item = item;

        if (!isEmpty()) {
            old_first.prev = new_first;
        }

        first = new_first;
        if (isEmpty()) {
            last = new_first;
        }
        n += 1;
    };

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }

        Node oldLast = last;

        Node newLast = new Node();
        newLast.item = item;
        newLast.prev = oldLast;
        newLast.next = null;

        if (!isEmpty()) {
            oldLast.next = newLast;
        }

        last = newLast;
        if (isEmpty()) {
            first = newLast;
        }

        n += 1;
    };

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }
        Node old_first = first;

        Node new_first = old_first.next;
        if (new_first != null) {
            new_first.prev = null;
        }

        old_first.next = null;

        first = new_first;
        if (first == null) {
            last = null;
        }

        n -= 1;
        return old_first.item;
    };

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }
        Node old_last = last;

        Node new_last = old_last.prev;
        if (new_last != null) {
            new_last.next = null;
        }

        old_last.prev = null;

        last = new_last;
        if (last == null) {
            first = null;
        }

        n -= 1;
        return old_last.item;
    };

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    };

    private class DequeIterator implements Iterator<Item> {
        Node curr = first;

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("not hasnext for next");
            }
            Item item = curr.item;
            curr = curr.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("cannot remove");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        assert deque.isEmpty();
        deque.addFirst(1);
        assert !deque.isEmpty();

        deque = new Deque<Integer>();
        assert deque.size() == 0;
        deque.addFirst(1);
        assert deque.size() == 1;
        deque.addLast(2);
        assert deque.size() == 2;
        deque.removeFirst();
        assert deque.size() == 1;
        deque.removeLast();
        assert deque.size() == 0;

        deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.addFirst(2);
        assert deque.removeFirst() == 2;
        assert deque.removeFirst() == 1;

        deque = new Deque<Integer>();
        deque.addLast(1);
        deque.addLast(2);
        assert deque.removeLast() == 2;
        assert deque.removeLast() == 1;

        deque.addFirst(1);
        deque.addFirst(2);
        deque.addLast(3);
        deque.addLast(4);

        Iterator<Integer> iter = deque.iterator();
        assert iter.hasNext();
        assert iter.next() == 2;
        assert iter.next() == 1;
        assert iter.next() == 3;
        assert iter.next() == 4;
        assert !iter.hasNext();

        System.out.println("All tests passed!");
    };

}

