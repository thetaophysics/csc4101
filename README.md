# csc4101

I have done 2 programs on Scheme Pretty-Printer in CSC 4101 Programming Languages
The pretty-printer will reads a Scheme program from standard input, parse it, and print it back out onto standard output. I used object-oriented programming style using inheritance and virtual functions in the code. 

Program 1: Scheme Pretty Printer
  Structures of the Pretty Printer:
  1. A lexical analyzer that splits the input text into tokens
  2. A recusive-descent parser that analyses the structure of the input program and builds a parse tree
  3. A parse-tree traversal that pretty-prints the input program

Program 2: Scheme Interpreter built on top of program 1
  I added additional implementation for the interpreter including:
  1. symbols, 32-but integers, booleans, strings, lists, and closures
  2. the test symbol? for identifying an identifier;
  3. the test number? for identifying integers and the binary arithmetic operations b+, b-, b*, b/,
b=, b<;
  4. the list operations car, cdr, cons, set-car!, set-cdr!, null?, pair?, eq?;
  5. the test procedure? for identifying a closure or built-in function;
  6. the I/O functions read, write, display, newline (without the optional port argument);
  7. the functions eval, apply, and interaction-environment;
  8. the function load;
  
