package pc.stack;

import java.util.Random;

import pc.util.Benchmark;

/**
 * Benchmark program for stack implementations.
 */
public class StackBenchmark {

  private static final int DURATION = 10;
  private static final int MAX_THREADS = 32;
  private static final int INITIAL_ELEMENTS_IN_STACK = 100;

  /**
   * Program to run a benchmark over stack implementations.
   * @param args Arguments are ignroed
   */
  public static void main(String[] args) {
    //double serial = runBenchmark(1, new UStack<Integer>());

    for (int n = 1; n <= MAX_THREADS; n = n * 2) {
      runBenchmark(n, new LLinkedStack<Integer>(), false);
      runBenchmark(n, new LArrayStack<Integer>(), false);
      System.out.println();
      runBenchmark(n, new ALinkedStack<Integer>(false), false);
      runBenchmark(n, new ALinkedStackASR<Integer>(false), false);
      runBenchmark(n, new AArrayStack<Integer>(false), false);
      System.out.println();
      runBenchmark(n, new ALinkedStack<Integer>(true), true);
      runBenchmark(n, new ALinkedStackASR<Integer>(true), true);
      runBenchmark(n, new AArrayStack<Integer>(true), true);
      System.out.println();
    }
  }

  private static void runBenchmark(int threads, Stack<Integer> s, boolean backoff) {
    for (int i = 0; i < INITIAL_ELEMENTS_IN_STACK; i++) {
      s.push(i);
    }
    Benchmark b = new Benchmark(threads, DURATION, new StackOperation(s));
    System.out.printf("%-3d threads using %-15s with backoff %-5b ... ", threads, s.getClass().getSimpleName(), backoff);

    double m = 0;
    for(int i=0; i<5; i++)
      m += b.run();

    System.out.printf("%.2f Mops/s%n", m/5);
  }

  private static class StackOperation implements Runnable {
    private final Random rng;
    private final Stack<Integer> stack;

    StackOperation(Stack<Integer> s) {
      this.stack = s;
      rng = new Random();
    }

    @Override
    public void run() {
      int v = rng.nextInt();
      if (v % 2 == 0) {
        stack.push(v);
      } else {
        stack.pop();
      }
    }
  }
}
