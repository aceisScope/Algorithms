import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

  private Item[] items;
  private int size;

  // construct an empty randomized queue
  public RandomizedQueue() {
    items = (Item[]) new Object[2];
    size = 0;
  }

  // is the randomized queue empty?
  public boolean isEmpty() {
    return (size == 0);
  }

  // return the number of items on the randomized queue
  public int size()  {
    return size;
  }

  // add the item
  public void enqueue(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    if (size == items.length) {
      resize(2 * items.length);
    }

    items[size++] = item;
  }

  // remove and return a random item
  public Item dequeue() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }

    int index = StdRandom.uniform(size);
    Item item = items[index];
    // swap the item with the last item
    items[index] = items[size - 1];
    items[size - 1] = null;
    size--;

    if (size > 0 && size == items.length / 4) {
      resize(items.length / 2);
    }

    return item;
  }

  // return a random item (but do not remove it)
  public Item sample() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    int index = StdRandom.uniform(size);
    return items[index];
  }

  private void resize(int capacity) {
    Item[] temp = (Item[]) new Object[capacity];
    for (int i = 0; i < size; i++) {
      temp[i] = items[i];
    }
    items = temp;
  }

  @Override
  public Iterator<Item> iterator() {
    return new RandomIterator();
  }

  private class RandomIterator implements Iterator<Item> {

    private int i = size;
    private int[] indices;

    public RandomIterator() {
      indices = new int[size];
      for (int i = 0; i < size; i++) {
        indices[i] = i;
      }
      // rearrange the entries in random order
      StdRandom.shuffle(indices);
    }

    @Override
    public boolean hasNext() {
      return i > 0;
    }

    @Override
    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      return items[indices[--i]];
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  // unit testing (optional)
  public static void main(String[] args)  {
    RandomizedQueue<Integer> rq = new RandomizedQueue<Integer> ();
    rq.enqueue(1);
    rq.enqueue(2);
    rq.enqueue(3);
    rq.enqueue(4);
    rq.enqueue(5);
    rq.enqueue(6);
    StdOut.println("Size: " + rq.size());
    StdOut.println(rq.dequeue());
    StdOut.println("Size: " + rq.size());
    Iterator<Integer> it = rq.iterator();
    StdOut.println(it.next());
    StdOut.println(it.next());
    StdOut.println(it.next());
    StdOut.println(it.next());
    StdOut.println(it.next());
  }
}
