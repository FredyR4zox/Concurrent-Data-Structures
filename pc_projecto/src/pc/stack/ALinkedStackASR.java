package pc.stack;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Lock-free stack with optional exponential back-off scheme.
 *
 * @param <E> Type of elements in the stack.
 */
public class ALinkedStackASR<E> implements Stack<E> {


  private final AtomicStampedReference<Node<E>> ref;

  private final Backoff backoff;

  /**
   * Constructor with no arguments, disabling back-off by default.
   */
  public ALinkedStackASR() {
    this(false);
  }

  /**
   * Constructor with explicit back-off setting.
   * @param enableBackoff Flag indicating if back-off should be used or not.
   */
  public ALinkedStackASR(boolean enableBackoff) {
    ref = new AtomicStampedReference<>(null, 0);
    backoff = enableBackoff ? new Backoff() : null;
  }

  @Override
  public int size() {
    return ref.getStamp(); // use ASR stamp to store the size!
  }

  @Override
  public void push(E elem) {
    int size;
    if (elem == null) {
      throw new IllegalArgumentException();
    }
    Node<E> newTop = new Node<>(elem, null);
    newTop.data = elem;
    Node<E> oldTop;
    while(true){
      size=ref.getStamp();
      oldTop = ref.getReference();
      newTop.next =oldTop;
      if(ref.compareAndSet(oldTop, newTop , size, size+1)){
        if (backoff != null) {
          backoff.diminish();
        }
        break;
      }
      if (backoff != null) {
        backoff.delay();
      }
    }
    // TODO
  }

  @Override
  public E pop() {
    Node<E> oldNode;
    Node<E> newNode;
    E elem = null;
    int size = 0;
    while(true) {
      size=ref.getStamp();
      oldNode = ref.getReference();
      if (size == 0 || oldNode == null) {
        elem = null;
        break;
      }
      newNode = oldNode.next;
      elem = oldNode.data;
      if (ref.compareAndSet(oldNode, newNode, size, size-1)) {
        if (backoff != null) {
          backoff.diminish();
        }
        break;
      }
      if (backoff != null) {
        backoff.delay();
      }
    }
    return elem;
  }

  //For tests
  @SuppressWarnings("javadoc")
  public static class Test extends StackTest {
    @Override
    public Stack<Integer> createStack() {
      return new ALinkedStackASR<>();
    }
  }
}
