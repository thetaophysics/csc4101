// Ident -- Parse tree node class for representing identifiers

package Tree;

import javax.print.DocFlavor.STRING;

public class Ident extends Node {
    private String name;

    public Ident(String n) {
        name = n;
    }
    public String getName(){
        return name;
    }
    public void print(int n) {
        for (int i = 0; i < n; i++)
            System.out.print(" ");

        System.out.println(name);
    }

    public boolean isSymbol() {
        return true;
    }
}