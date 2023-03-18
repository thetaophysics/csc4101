// Cons -- Parse tree node class for representing a Cons node

package Tree;

import Special.Special;
import Special.Regular;
import Special.Quote;
import Special.Begin;
import Special.Cond;
import Special.Lambda;
import Special.Let;
import Special.Define;
import Special.If;
import Special.Set;

public class Cons extends Node {
    private Node car;
    private Node cdr;
    private Special form;

    public Cons(Node a, Node d) {
        car = a;
        cdr = d;
        parseList();
    }

    // parseList() `parses' special forms, constructs an appropriate
    // object of a subclass of Special, and stores a pointer to that
    // object in variable form. It would be possible to fully parse
    // special forms at this point. Since this causes complications
    // when using (incorrect) programs as data, it is easiest to let
    // parseList only look at the car for selecting the appropriate
    // object from the Special hierarchy and to leave the rest of
    // parsing up to the interpreter.
    void parseList() {
        if (car.isSymbol()){
            if (((Ident) car).getName().equals("'"))
                form = new Quote( true);
            else if (((Ident) car).getName().equals("cond"))
                form = new Cond();
            else if (((Ident) car).getName().equals("begin"))
                form = new Begin();
            else if (((Ident) car).getName().equals("define"))
                form = new Define();
            else if (((Ident) car).getName().equals("if"))
                form = new If();
            else if (((Ident) car).getName().equals("let"))
                form = new Let();
            else if (((Ident) car).getName().equals("lambda"))
                form = new Lambda();
            else if (((Ident) car).getName().equals("quote"))
                form = new Quote(false);
            else if (((Ident) car).getName().equals("set!"))
                form = new Set();

        }
        
    }

    // TODO: Add any helper functions for parseList
    // to the class hierarchy as needed.

    public void print(int n) {
        form.print(this, n, false);
    }

    public void print(int n, boolean p) {
        form.print(this, n, p);
    }

    public Node getCar() {
        return car;
    }

    public Node getCdr() {
        return cdr;
    }

    public boolean isSymbol() {
        return true;
    }

}