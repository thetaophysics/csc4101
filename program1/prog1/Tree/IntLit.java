// IntLit -- Parse tree node class for representing integer literals

package Tree;

public class IntLit extends Node {
    private int intVal;

    public IntLit(int i) {
        intVal = i;
    }

    public void print(int n) {
        for (int i = 0; i < n; i++)
            System.out.print(" ");

        System.out.println(intVal);
    }

    public boolean isNumber() {
        return true;
    }
}