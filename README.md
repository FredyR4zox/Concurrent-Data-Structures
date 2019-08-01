# Concurrent-Data-Structures
Developed data structures that could be used simultaneously by more than one thread.


## Implementation
Java and Cooperari.


## Usage
![#f03c15](https://placehold.it/15/f03c15/000000?text=+) Attention: java-8 required.
### Commands
```bash
export PATH=$PWD/cooperari-0.3/bin:$PATH
cd pc_projecto
cjavac                        # Compile all code.
cjunit pc.stack.AllTests      # Execute cooperari tests for the stack implementations.
cjunitp pc.stack.AllTests     # Execute cooperari tests for the stack implementations but in preemptive mode.
cjava pc.stack.StackBenchmark # Benchmark the stack implementations.
cjava pc.set.SetTest          # Execute tests for the set implementations.
cjava pc.set.SetBenchmark     # Benchmark the set implementations.
```


## Authors
| Name                            | UP Number   | GitHub                                          |
| ------------------------------- | ----------- | ----------------------------------------------- |
| Diogo Filipe Pinto Pereira      | up201605323 | [DiogoP98](https://github.com/DiogoP98)         |
| Frederico Emanuel Almeida Lopes | up201604674 | [FredyR4zox](https://www.github.com/FredyR4zox) |
