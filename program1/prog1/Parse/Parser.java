// Parser -- the parser for the Scheme printer and interpreter
//
// Defines
//
//   class Parser;
//
// Parses the language
//
//   exp  ->  ( rest
//         |  #f
//         |  #t
//         |  ' exp
//         |  integer_constant
//         |  string_constant
//         |  identifier
//    rest -> )
//         |  exp+ [. exp] )
//
// and builds a parse tree.  Lists of the form (rest) are further
// `parsed' into regular lists and special forms in the constructor
// for the parse tree node class Cons.  See Cons.parseList() for
// more information.
// 
// The parser is implemented as an LL(0) recursive descent parser.
// I.e., parseExp() expects that the first token of an exp has not
// been read yet.  If parseRest() reads the first token of an exp
// before calling parseExp(), that token must be put back so that
// it can be reread by parseExp() or an alternative version of
// parseExp() must be called.
//
// If EOF is reached (i.e., if the scanner returns a NULL) token,
// the parser returns a NULL tree.  In case of a parse error, the
// parser discards the offending token (which probably was a DOT
// or an RPAREN) and attempts to continue parsing with the next token.

package Parse;

import Tree.Node;
import Tree.BooleanLit;
import Tree.Cons;
import Tree.Ident;
import Tree.IntLit;
import Tree.Nil;
import Tree.StrLit;

import Tokens.Token;
import Tokens.TokenType;
//import Tokens.IdentToken;
//import Tokens.IntToken; probably won't need
//import Tokens.StrToken;

public class Parser {
    private Scanner scanner;

// A constructor.
    public Parser(Scanner s) {
        scanner = s;
    }
    //exp without lookahead
    public Node parseExp() {
        Token exp = scanner.getNextToken();
        return parseExp(exp);
    }

    //recursive descent with lookahead
    private Node parseExp(Token tok){
        if (tok.getType() == null){
            return null;
        }
        else if (tok.getType() == TokenType.LPAREN){ 
            tok = scanner.getNextToken();
            return parseRest();
        }
        else if(tok.getType() == TokenType.FALSE){
            tok = scanner.getNextToken();
            return BooleanLit.getInstance(false);    //#f
        }
        else if(tok.getType() == TokenType.TRUE){
            tok = scanner.getNextToken();
            return BooleanLit.getInstance(true);   //#t
        }
        else if (tok.getType() == TokenType.QUOTE){
            return new Cons(new Ident("quote"), parseExp());
                               /*  Nil.getInstance()));
                Nil.getInstant = Nil.java mean whenever call Nil(), get the same object
            */
        }
        else if (tok.getType() == TokenType.INT){
            int temp = tok.getIntVal();
            tok = scanner.getNextToken();
            return new IntLit(temp);
        }
        else if (tok.getType() == TokenType.STRING){
            String temp = tok.getStrVal();
            tok = scanner.getNextToken();
            return new StrLit(temp); 
        }
        else if (tok.getType() == TokenType.IDENT) //tok is type IDENT
            String temp = tok.getName();
            tok = scanner.getNextToken();
            return new Ident(temp);
        }
        else{
            tok = scanner.getNextToken();
            return parseRest();
        }

    }
    private Node parseRest() {
        // TODO: write code for parsing rest
        return parseRest(scanner.getNextToken());
    }

    private Node parseRest(Token tok) {
        if (tok.getType() == null) {
            return null;
        } else if (tok.getType() == TokenType.RPAREN) {
            return Nil.getInstance();   
        } else {
            Node a = parseExp(scanner.getNextToken());
            Node d = parseRest(scanner.getNextToken());
            return new Cons(a, d);
        }
    }

    // TODO: Add any additional methods you might need.
}