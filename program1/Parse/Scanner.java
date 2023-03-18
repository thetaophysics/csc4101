// Scanner -- The lexical analyzer for the Scheme printer and interpreter.

package Parse;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

import Tokens.Token;
import Tokens.TokenType;
import Tokens.IdentToken;
import Tokens.IntToken;
import Tokens.StrToken;

public class Scanner {
	private PushbackInputStream in;	//read() return an int; EOF = -1
									//we read from stdin

	// Maximum length of strings and identifers
	private int BUFSIZE = 1000;	
	private byte[] buf = new byte[BUFSIZE];	//byte array bc byte = 8 bit unsigned
											//characters are ASCII integers
											//byte array is used for STRING CONST AND ID

	public Scanner(InputStream i) {
		in = new PushbackInputStream(i);
	}


	public Token getNextToken() {
		int ch;

		try {
			// It would be more efficient if we'd maintain our own
			// input buffer and read characters out of that
			// buffer, but reading individual characters from the
			// input stream is easier.
			ch = in.read();

			// TODO: Skip white space and comments
			
			//Old solution
			// \n 
			// tab character
			//Skip white space
			if (ch == -1){
				return null;
			}

			if((ch == ' ') || (ch == '\n')) {
				ch = in.read();

				while((ch == ' ') || (ch == '\n')) {
					ch = in.read();
				}
			//2 nested loops: 
			//1st loop: comment, whrite space
			//2nd inner loop: throwing away until you find a new line char or you reach EOF
			}

			//Skip comments
			if(ch == ';') {
				ch = in.read();
				while(ch != '\n') {
					ch = in.read();
				}
				ch = in.read();
			}

			// Return null on EOF
			if (ch == -1)
				return null;

			// Special characters
			else if (ch == '\'')
				return new Token(TokenType.QUOTE);
			else if (ch == '(')
				return new Token(TokenType.LPAREN);
			else if (ch == ')')
				return new Token(TokenType.RPAREN);
			else if (ch == '.')
				// We ignore the special identifier `...'.
				return new Token(TokenType.DOT);

			// Boolean constants
			else if (ch == '#') {
				ch = in.read();
				if (ch == 't')
					return new Token(TokenType.TRUE);
				else if (ch == 'f')
					return new Token(TokenType.FALSE);
				else if (ch == -1) {
					System.err.println("Unexpected EOF following #");
					return null;
				} else {
					System.err.println("Illegal character '" +
							(char)ch + "' following #");
					return getNextToken();
				}
			}

			// String constants
			else if (ch == '"') {
				// TODO: Scan a string into the buffer variable buf

				String buf;
				ch = in.read();
				while(ch != '"') {
					buf = buf + ch;

					ch = in.read();
				}

				return new StrToken(buf);	//Gerald: create a string object,[GG] take a byte array as elements
									//Count hhow many char read from string --> construct string object
			}

			// Integer constants
				// class integer have method parse int
			else if (ch >= '0' && ch <= '9') {
				int i = ch - '0';
				// TODO: scan the number and convert it to an integer

				ch = in.read();
				while(ch >= '0' && ch <= '9') {
					i = i * 10;
					i += (ch - '0');

					ch = in.read();
				}

				// Put the character after the integer back into the input
				in.unread(ch);
				return new IntToken(i);
			}

			// Identifiers
			//convert char -> ID = 
					//convert char -> int: when read, character = ASCII code
			else if ((ch >= 'A' && ch <= 'Z') || (ch == '!') || (ch >= '$' && ch <= '&')
					|| (ch >= '*' && ch <= '+') || (ch >= '-' && ch <= '/') || 
					(ch == ':') || (ch >= '<' && ch <= '@') || (ch >= '^' && ch <= '_')
					|| (ch == '~'))
					//if (ch = peculiar id) -> go on
				/* or ch is some other valid first character for an identifier */ {
				// TODO: scan an identifier into the buffer variable buf
				//int buf = in.next(); 
				String buf;
				buf += ch;

				while (ch != 32){
					buf += ch;
					ch = in.read();
				}
				// Put the character after the identifier back into the input
				in.unread(ch);
				return new IdentToken(buf);	// return new IdentToken(new String(buf, [construct-of_LENGTH]);
				
			}

			// Illegal character
			else {
				System.err.println("Illegal input character '" + (char)ch + '\'');
				return getNextToken();
			}
		} catch (IOException e) {
			System.err.println("IOException: " + e.getMessage());
			return null;
		}

		
	}
}
