import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

  private int size;
  private Node first;
  private Node last;

  /**
   *  double linked list node.
   */

  private class Node {
    private Item item;
    private Node previous;
    private Node next;
  }

  /**
   * construct an empty deque.
   */
  public Deque() {
    first = null;
    last = null;
    size = 0;
  }

  /**
   * is the deque empty.
   * @return true if the deque is empty, else false
   */
  public boolean isEmpty() {
    return (size == 0);
  }

  /**
   * the number of items on the deque.
   * @return size of deque
   */
  public int size() {
    return size;
  }

  /**
   * add the item to the front.
   * @param item to the added
   */
  public void addFirst(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }

    Node oldfirst = first;
    first = new Node();
    first.item = item;
    if (isEmpty()) {
      last = first;
    } else {
      first.next = oldfirst;
      oldfirst.previous = first;
    }
    size++;
  }

  /**
   * add the item to the end.
   * @param item to bhe added
   */
  public void addLast(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }

    Node oldlast = last;
    last = new Node();
    last.item = item;
    if (isEmpty()) {
      first = last;
    } else {
      last.previous = oldlast;
      oldlast.next = last;
    }
    size++;
  }

  /**
   * remove the item from the front.
   * @return the removed item
   */
  public Item removeFirst() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }

    Item item = first.item;
    Node next = first.next;

    first.item = null;
    first.next = null;
    first = next;

    size--;

    if (isEmpty()) {
      last = null;
    } else {
      first.previous = null;
    }

    return item;
  }

  /**
   * remove the item from the end.
   * @return the removed item
   */
  public Item removeLast() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }

    Item item = last.item;
    Node previous = last.previous;

    last.item = null;
    last.previous = null;
    last = previous;

    size--;

    if (isEmpty()) {
      first = null;
    } else {
      last.next = null;
    }

    return item;
  }

  /**
   * an iterator over items in order from front to end.
   * @return the iterator
   */
  public Iterator<Item> iterator() {
    return new Iterator<Item>() {
      private Node current = first;

      @Override
      public boolean hasNext() {
        return (current != null);
      }

      @Override
      public Item next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        Item item = current.item;
        current = current.next;
        return item;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }
}
