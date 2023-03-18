// Quote -- Parse tree node strategy for printing the special form quote

package Special;

import Tree.Node;

public class Quote extends Special {
    private boolean isQuote;
    public void print(Node t, int n, boolean p) {
    }

    public Quote(boolean isQuote) {
        this.isQuote = isQuote;
    }
}