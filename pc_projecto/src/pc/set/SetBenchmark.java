package pc.set;

import java.util.Random;

import pc.util.Benchmark;

/**
 * Benchmark program for stack implementations.
 */
public class SetBenchmark {

  private static final int DURATION = 10;
  private static final int MAX_THREADS = 32;
  private static final int N = 4096;

  /**
   * Program to run a benchmark over set implementations.
   * @param args Arguments are ignored
   */
  public static void main(String[] args) {
    //double serial = runBenchmark(1, new UStack<Integer>());

    for (int n = 1; n <= MAX_THREADS; n = n * 2) {
      runBenchmark(n, new LHashSetRL<Integer>(false), false);
      runBenchmark(n, new LHashSetRLArray<Integer>(false), false);
      runBenchmark(n, new LHashSetRRWLArray<Integer>(false), false);
      System.out.println();
      runBenchmark(n, new LHashSetRL<Integer>(true), true);
      runBenchmark(n, new LHashSetRLArray<Integer>(true), true);
      runBenchmark(n, new LHashSetRRWLArray<Integer>(true), true);
      System.out.println();
    }
  }

  private static void runBenchmark(int threads, Set<Integer> s, boolean fair) {
    for (int i = 0; i < N; i++) {
      s.add(i);
    }
    Benchmark b = new Benchmark(threads, DURATION, new SetOperation(s));
    System.out.printf("%-3d threads using %-17s with fairness %-5b ... ", threads, s.getClass().getSimpleName(), fair);

    double m = 0;
    for(int i=0; i<5; i++)
      m += b.run();

    System.out.printf("%.2f Mops/s%n", m/5);
  }

  private static class SetOperation implements Runnable {
    private final Random rng;
    private final Set<Integer> set;

    SetOperation(Set<Integer> s) {
      this.set = s;
      rng = new Random();
    }

    @Override
    public void run() {
      int op = rng.nextInt(10);
      int v = rng.nextInt(N);
      switch (op) {
        case 0:
          set.add(v);
          break;
        case 1:
          set.add(v);
          break;
        case 2:
          set.remove(v);
          break;
        case 3:
          set.remove(v);
          break;
        default:
          set.contains(v);
      }
    }
  }
}


