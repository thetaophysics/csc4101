// Environment.java -- a data structure for Scheme environments

// An Environment is a list of frames.  Each frame represents a scope
// in the program and contains a set of name-value pairs.  The first
// frame in the environment represents the innermost scope.

// For the code below, I am assuming that a scope is implemented
// as an association list, i.e., a list of two-element lists.  E.g.,
// the association list ((x 1) (y 2)) associates the value 1 with x
// and the value 2 with y.

// To implement environments in an object-oriented style, it would
// be better to define a Frame class and make an Environment a list
// of such Frame objects.  If we simply use the parse tree structure
// for lists of association lists, we end up having to write the
// lookup functions in a more functional style.

// You need the following methods for modifying environments:
//  - constructors:
//      - create the empty environment (an environment with an empty frame)
//      - add an empty frame to the front of an existing environment
//  - lookup the value for a name (for implementing variable lookup)
//      if the name exists in the innermost scope, return the value
//      if it doesn't exist, look it up in the enclosing scope
//      if we don't find the name, it is an error
//  - define a name (for implementing define and parameter passing)
//      if the name already exists in the innermost scope, update the value
//      otherwise add a name-value pair as first element to the innermost scope
//  - assign to a name (for implementing set!)
//      if the name exists in the innermost scope, update the value
//      if it doesn't exist, perform the assignment in the enclosing scope
//      if we don't find the name, it is an error

package Tree;

public class Environment extends Node {

	// An Environment is implemented like a Cons node, in which
	// every list element (every frame) is an association list.
	// Instead of a Nil object, we use null to terminate the list.

	private Node scope; // the innermost scope, an association list
	private Environment env; // the enclosing environment

	public Environment() {
		scope = Nil.getInstance();
		env = null;
	}

	public Environment(Environment e) {
		scope = Nil.getInstance();
		env = e;
	}

	public boolean isEnvironment() {
		return true;
	}

	public void print(int n) {
		// there got to be a more efficient way to print n spaces
		for (int i = 0; i < n; i++)
			System.out.print(' ');
		System.out.println("#{Environment");
		if (scope != null)
			scope.print(Math.abs(n) + 2);
		if (env != null)
			env.print(Math.abs(n) + 2);
		for (int i = 0; i < Math.abs(n); i++)
			System.out.print(' ');
		System.out.println('}');
	}

	// This is not in an object-oriented style, it's more or less a
	// translation of the Scheme assq function.
	private static Node find(Node id, Node alist) {
		if (!alist.isPair())
			return null; // in Scheme we'd return #f
		else {
			Node bind = alist.getCar();
			if (id.getName().equals(bind.getCar().getName()))
				// return a list containing the value as only element
				return bind.getCdr();
			else
				return find(id, alist.getCdr());
		}
	}

	public Node lookup(Node id) {
		Node val = find(id, scope);
		if (val == null && env == null) {
			System.out.println("undefined variable");
			return Nil.getInstance();
		} else if (val == null)
			// look up the identifier in the enclosing scope
			return env.lookup(id);
		else
			// get the value out of the list we got from find()
			return val.getCar();
	}

	public void define(Node id, Node val) {
		// TODO: implement this function
		Node targetVal = find(id, scope);
		if (targetVal == null) { //If target isn't found in this environment (it doesn't matter if it exists in other ones)
			Cons temp = new Cons(id, val);
			scope.setCar(temp);
		} else { 
			targetVal.setCar(val);
		}
	}

	
	public void assign(Node id, Node val) {
		// TODO: implement this function

		// You can use find() to get a list containing the value and
		// then update the value using setCar()
		Node targetVal = find(id, scope);
		if (targetVal == null && env == null) { //If target isn't found in this environment (it doesn't matter if it exists in other ones)
			System.out.println("undefined variable");
		} else if (targetVal == null) { //If it is
			env.assign(id, val);
		}
		else {
			targetVal.setCar(val);
		}
	}
}
