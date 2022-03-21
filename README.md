# Toy-Language-Interpreter

A Java project using the model-view-controller architectural pattern and the object-oriented concepts that consists in an interpreter for a toy programming language. The mini interpreter uses three main structures:
- Execution Stack: a stack of statements to execute the current program;
- Table of Symbols: a table that keeps the variables values;
- Output Table: a table that keeps all the messages printed by the program.

All these three main structures denote the program state. The interpreter can execute multiple programs but for each one of them it uses different Program State structures (different Execution Stack, Table of Symbols and Output Table). While running more than one program, threads are used (and synchronized using synchronization methods, such as semaphores and locks). 


At the beginning, ExeStack contains the original program, and SymTable and Out are empty.
After the evaluation has started, ExeStack contains the remaining part of the program that
must be evaluated, SymTable contains the variables (from the variable declarations
statements evaluated so far) with their assigned values, and Out contains the values printed so
far.

The toy language is able to:
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


