# Toy-Language-Interpreter

A Java project using the model-view-controller architectural pattern and the object-oriented concepts and providing a graphical user interface (JavaFX) that consists in an interpreter for a toy programming language. The mini interpreter uses three main structures:
- Execution Stack (ExeStack): a stack of statements to execute the current program;
- Table of Symbols(SymTable): a table that keeps the variables values;
- Output Table (OutTable): a table that keeps all the messages printed by the program;
- Heap (HeapTable): a table that manages the heap memory;
- Table of Files (FileTable): a table that keeps all the files used to read data.

All these three main structures denote the program state. The interpreter can execute multiple programs but for each one of them it uses different Program State structures (different Execution Stack, Table of Symbols and Output Table). While running more than one program, threads are used (and synchronized using synchronization methods, such as semaphores and locks). 


At the beginning, ExeStack contains the original program, and SymTable and Out are empty.
After the evaluation has started, ExeStack contains the remaining part of the program that
must be evaluated, SymTable contains the variables (from the variable declarations
statements evaluated so far) with their assigned values, and Out contains the values printed so
far.

The toy language is able to:
- work on multiple threads;
- execute compound expressions and imbricated structures;
- perform basic arithmetic expressions (addition, subtraction, multiplication and division) on integer values;
- perform basic logic "or" and logic "and" on boolean values;
- negate a boolean value;
- read from the heap;
- execute repetitive loops: while loops, for loops, repeat until loops;
- execute conditional clauses: if clauses, switch clauses, conditional assignments;
- print results;
- perform read, write, open and close operations on files;
- introduce pauses in execution with the sleep() command;
- manage threads using the wait() command;
- fork threads.
- handle exceptions.


