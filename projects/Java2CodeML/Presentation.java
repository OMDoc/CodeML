/* Presentation - CodeML presentation markup */

import antlr.*;
import antlr.collections.*;
import antlr.collections.impl.*;
import java.io.*;
import java.util.*;

public class Presentation implements JavaTokenTypes
{
	private static int ALL = -1;
	private java.util.Stack stack = new java.util.Stack();
	private static String[] tokenNames;
	private final static int ROOT_ID = 0;
	private StringBuffer out = new StringBuffer();

	private final static int cpb = 1;
	private final static int cpb_number = 11;
	private final static int cpb_string = 12;

	private final static int cpg = 2;
	private final static int cpo = 3;
	private final static int cpo_builtin = 31;
	private final static int cpo_imported = 32;
	private final static int cpo_defined = 33;

	private final static int cpi = 4;
	private final static int cptype = 5;
	
	static {
		setupTokenNames();
	}

	/**
	 * Creates an opening element of the given type.
	 * 
	 * @param type the type of element
	 * @return the XML for the opening element
	 */
	private String startNode(int type) {
		String text;
		switch (type) {
			case cpb:
				text = "<cpb>";
				break;
			case cpb_number:
				text = "<cpb type=\"number\">";
				break;
			case cpb_string:
				text = "<cpb type=\"string\">";
				break;
			case cpg:
				text = "<cpg>";
				break;
			case cpo:
				text = "<cpo>";
				break;
			case cpo_builtin:
				text = "<cpo type=\"built-in\">";
				break;
			case cpo_imported:
				text = "<cpo type=\"imported\">";
				break;
			case cpo_defined:
				text = "<cpo type=\"defined\">";
				break;
			case cpi:
				text = "<cpi>";
				break;
			case cptype:
				text = "<cptype>";
				break;
			default:
				text = "";
		}
		return text;
	}

	/**
	 * Creates a closing element of the given type.
	 * 
	 * @param type the type of element
	 * @return the XML for the closing element
	 */
	private String endNode(int type) {
		String text;
		switch (type) {
			case cpb:
			case cpb_number:
			case cpb_string:
				text = "</cpb>";
				break;
			case cpg:
				text = "</cpg>";
				break;
			case cpo:
			case cpo_builtin:
			case cpo_imported:
			case cpo_defined:
				text = "</cpo>";
				break;
			case cpi:
				text = "</cpi>";
				break;
			case cptype:
				text = "</cptype>";
				break;
			default:
				text = "";
		}
		return text;
	}

	/**
	 * Adds text to the output buffer. This function is 
	 * not heavily used in the code below because it was
	 * causing some performace issues.
	 * 
	 * @param text the text to append
	 */
	private void print(String text) {
		out.append(text);
	}
			
	/**
	 * Adds an opening element to the output buffer.
	 * 
	 * @param type the type of element
	 */
	private void printStartNode(int type) {
		print(startNode(type));
	}

	/**
	 * Adds a closing element to the output buffer.
	 * 
	 * @param type the type of element
	 */
	private void printEndNode(int type) {
		print(endNode(type));
	}

	/**
	 * Adds an element to the output buffer, with
	 * text.
	 * 
	 * @param type the type of element
	 * @param text the text within the element
	 */
	private void printNode(int type, String text) {
		print(startNode(type) + text + endNode(type));
	}

	/**
	 * Adds a <code>cpg</code> element to the output buffer,
	 * with open and close attributes.
	 * 
	 * @param open value for the open attribute
	 * @param close value for the close attribute
	 */
	private void printCpg(String open, String close) {
		printCpg(open, close, "");
	}

	/**
	 * Adds a <code>cpg</code> element to the output buffer,
	 * with open and close attributtes and additional attributes.
	 * 
	 * @param open value for the open attribute
	 * @param close value for the close attribute
	 * @param rest addition attributes and values for the element
	 */
	private void printCpg(String open, String close, String rest) {
		if (open != "")
			open = " open=\"" + open + "\"";
		if (close != "")
			close = " close=\"" + close + "\"";
		if (rest != "")
			rest = " " + rest;
		print("<cpg" + open + close + rest + ">");
	}

	/**
	 * Maps token names to strings.
	 */
	private static void setupTokenNames() {
		tokenNames = new String[200];
		for (int i=0; i<tokenNames.length; i++) {
			tokenNames[i] = "ERROR:" + i;
		}

		tokenNames[POST_INC]="++";
		tokenNames[POST_DEC]="--";
		tokenNames[UNARY_MINUS]="-";
		tokenNames[UNARY_PLUS]="+";
		tokenNames[STAR]="*";
		tokenNames[ASSIGN]="=";
		tokenNames[PLUS_ASSIGN]="+=";
		tokenNames[MINUS_ASSIGN]="-=";
		tokenNames[STAR_ASSIGN]="*=";
		tokenNames[DIV_ASSIGN]="/=";
		tokenNames[MOD_ASSIGN]="%=";
		tokenNames[SR_ASSIGN]=">>=";
		tokenNames[BSR_ASSIGN]=">>>=";
		tokenNames[SL_ASSIGN]="<<=";
		tokenNames[BAND_ASSIGN]="&=";
		tokenNames[BXOR_ASSIGN]="^=";
		tokenNames[BOR_ASSIGN]="|=";
		tokenNames[QUESTION]="?";
		tokenNames[LOR]="||";
		tokenNames[LAND]="&&";
		tokenNames[BOR]="|";
		tokenNames[BXOR]="^";
		tokenNames[BAND]="&";
		tokenNames[NOT_EQUAL]="!=";
		tokenNames[EQUAL]="==";
		tokenNames[LT]="<";
		tokenNames[GT]=">";
		tokenNames[LE]="<=";
		tokenNames[GE]=">=";
		tokenNames[SL]="<<";
		tokenNames[SR]=">>";
		tokenNames[BSR]=">>>";
		tokenNames[PLUS]="+";
		tokenNames[MINUS]="-";
		tokenNames[DIV]="/";
		tokenNames[MOD]="%";
		tokenNames[INC]="++";
		tokenNames[DEC]="--";
		tokenNames[BNOT]="~";
		tokenNames[LNOT]="!";
		tokenNames[FINAL]="final";
		tokenNames[ABSTRACT]="abstract";
		tokenNames[LITERAL_package]="package";
		tokenNames[LITERAL_import]="import";
		tokenNames[LITERAL_void]="void";
		tokenNames[LITERAL_boolean]="boolean";
		tokenNames[LITERAL_byte]="byte";
		tokenNames[LITERAL_char]="char";
		tokenNames[LITERAL_short]="short";
		tokenNames[LITERAL_int]="int";
		tokenNames[LITERAL_float]="float";
		tokenNames[LITERAL_long]="long";
		tokenNames[LITERAL_double]="double";
		tokenNames[LITERAL_private]="private";
		tokenNames[LITERAL_public]="public";
		tokenNames[LITERAL_protected]="protected";
		tokenNames[LITERAL_static]="static";
		tokenNames[LITERAL_transient]="transient";
		tokenNames[LITERAL_native]="native";
		tokenNames[LITERAL_threadsafe]="threadsafe";
		tokenNames[LITERAL_synchronized]="synchronized";
		tokenNames[LITERAL_volatile]="volatile";
		tokenNames[LITERAL_class]="class";
		tokenNames[LITERAL_extends]="extends";
		tokenNames[LITERAL_interface]="interface";
		tokenNames[LITERAL_implements]="implements";
		tokenNames[LITERAL_throws]="throws";
		tokenNames[LITERAL_if]="if";
		tokenNames[LITERAL_else]="else";
		tokenNames[LITERAL_for]="for";
		tokenNames[LITERAL_while]="while";
		tokenNames[LITERAL_do]="do";
		tokenNames[LITERAL_break]="break";
		tokenNames[LITERAL_continue]="continue";
		tokenNames[LITERAL_return]="return";
		tokenNames[LITERAL_switch]="switch";
		tokenNames[LITERAL_throw]="throw";
		tokenNames[LITERAL_case]="case";
		tokenNames[LITERAL_default]="default";
		tokenNames[LITERAL_try]="try";
		tokenNames[LITERAL_finally]="finally";
		tokenNames[LITERAL_catch]="catch";
		tokenNames[LITERAL_instanceof]="instanceof";
		tokenNames[LITERAL_this]="this";
		tokenNames[LITERAL_super]="super";
		tokenNames[LITERAL_true]="true";
		tokenNames[LITERAL_false]="false";
		tokenNames[LITERAL_null]="null";
		tokenNames[LITERAL_new]="new";
	}

	/**
	 * Gets the string representation of an AST node.
	 * 
	 * @param ast the AST node
	 * @return value of the AST node
	 */
	private String name(AST ast) {
		return tokenNames[ast.getType()];
	}

	/**
	 * Gets the string representation of an AST node type.
	 * 
	 * @param type the type of AST node
	 * @return value of the type
	 */
	private String name(int type) {
		return tokenNames[type];
	}

	/**
	 * Find a child of the given AST that has the given type.
	 * 
	 * @param ast the ast to search
	 * @param childType the type of AST node to find
	 * @return a child AST of the given type, or null if none is found. */
	private AST getChild(AST ast, int childType) {
		AST child = ast.getFirstChild();
		while (child != null) {
			if (child.getType() == childType) {
				return child;
			}
			child = child.getNextSibling();
		}
		return null;
	}

	/**
	 * Print the children of the given AST.
	 * 
	 * @param ast the AST to print
	 * @return true iff anything was printed
	 */
	private boolean printChildren(AST ast) {
		return printChildren(ast, "", ALL);
	}

	/**
	 * Print the children of the given AST, seperated by
	 * a seperator.
	 * 
	 * @param ast the AST to print
	 * @param separator The separator to use
	 * @return true iff anything was printed
	 */
	private boolean printChildren(AST ast, String separator) {
		return printChildren(ast, separator, ALL);
	}

	/**
	 * Print the children of the given AST, enclosing each
	 * in an element.
	 * 
	 * @param ast the AST to print
	 * @param type the type of element
	 * @return true iff anything was printed
	 */
	private boolean printAllChildren(AST ast, int type) {
		return printChildren(ast, type, ALL);
	}

	/**
	 * Print the children of the given AST with a specified type.
	 * 
	 * @param ast the AST to print
	 * @param type the type of child nodes
	 * @return true iff anything was printed
	 */
	private boolean printChildren(AST ast, int nodetype) {
		return printChildren(ast, -1, nodetype); 
	}

	/**
	 * Print the children of the given AST with a specified type, 
	 * enclosing each in an element.
	 * 
	 * @param ast the AST to print
	 * @param type the type of element
	 * @param nodetype the type of child nodes
	 * @return true iff anything was printed
	 */
	private boolean printChildren(AST ast, int type, int nodetype) {
		boolean ret = false;
		AST child = ast.getFirstChild();
		while (child != null) {
			if (nodetype == ALL || child.getType() == nodetype) {
				// print a separator before each printed child (except first)
				printStartNode(type);
				print(child);
				printEndNode(type);
				ret = true;
			}
			child = child.getNextSibling();
		}
		return ret;
	}

	/**
	 * Print the children of the given AST in a sequence,
	 * enclosing each in strings, and printing only the opening
	 * string for the last child.
	 * 
	 * @param ast the AST to print
	 * @param before text to put before each child
	 * @param after text to put after each child, except the last
	 * @param last text to put after the last child
	 * @return true iff anything was printed
	 */
	private boolean printSequence(AST ast, String before, String after, String last) {
		boolean ret = false;
		AST child = ast.getFirstChild();
		while (child != null) {
			if (child.getNextSibling() != null) {
				print(before);
			} else {
				print(last);
			}
			print(child);
			print(after);
			ret = true;
			child = child.getNextSibling();
		}
		return ret;
	}

	/**
	 * Print all of the children of the given AST that are of the given type.
	 *
	 * @param ast the AST to print
	 * @param separator the separator to use
	 * @param type The type of child AST to print
	 * @return true iff anything was printed
	 */
	private boolean printChildren(AST ast, String separator, int type) {
		boolean ret = false;
		AST child = ast.getFirstChild();
		while (child != null) {
			if (type == ALL || child.getType() == type) {
				if (child != ast.getFirstChild()) {
					out.append(separator);
				}
				ret = true;
				print(child);
			}
			child = child.getNextSibling();
		}
		return ret;
	}
	
	/**
	 * Checks if an AST has children.
	 *
	 * @param ast the AST to check
	 * @return true iff the AST has at least one child
	 */
	private boolean hasChildren(AST ast) {
		return (ast.getFirstChild() != null);
	}

	/**
	 * Prints the binary operator and operands.
	 *
	 * @param ast the AST specifying the binary operator
	 */
	private void printBinaryOperator(AST ast) {
		String text = name(ast);
		if (text == "<") 
			text = "&lt;";
		else if (text == ">")
			text = "&gt;";
		else if (text == "<=")
			text = "&lt;=";
		else if (text == ">=")
			text = "&gt;=";
		else if (text == "<>")
			text = "&lt;&gt;";
		else if (text == ">>")
			text = "&gt;&gt;";
		else if (text == "<<")
			text = "&lt;&lt;";
		printWithParens(ast, ast.getFirstChild());
		printStartNode(cpo_builtin);
		out.append(text);
		printEndNode(cpo_builtin);
		printWithParens(ast, ast.getFirstChild().getNextSibling());
	}

	/**
	 * Prints an AST, adding parenthises if they are needed.
	 * 
	 * @param parent the parent of the AST
	 * @param ast the AST to print
	 * */
	private void printWithParens(AST parent, AST ast) {
		boolean parensNeeded = (getPrecedence(parent) < getPrecedence(ast));
		if (parensNeeded) {
			printCpg("(", ")");
		}

		if (ast.getType() == IDENT) {
			printStartNode(cpi);
		}

		print(ast);

		if (ast.getType() == IDENT) {
			printEndNode(cpi);
		}

		if (parensNeeded) {
			printEndNode(cpg);
		}
	}

	/**
	 * Check if an ast requires a semicolon after it.
	 *
	 * @param parent the parent of the ast
	 * @return true if a semicolon is required, false otherwise
	 */
	private boolean printSemi(AST parent) {
		if (parent!= null && parent.getType() == SLIST) {
			return true;
		}
		return false;
	}

	/**
	 * Print the given AST.
	 *
	 * @param ast the AST to print
	 */
	public void print (AST ast) {
		if (ast == null) {
			return;
		}

		AST parent = null;
		if (!stack.isEmpty()) {
			parent = (AST) stack.peek();
		}
		stack.push(ast);

		AST child1 = ast.getFirstChild();
		AST child2 = null;
		AST child3 = null;
		if (child1 != null) {
			child2 = child1.getNextSibling();
			if (child2 != null) {
				child3 = child2.getNextSibling();
			}
		}

		switch(ast.getType()) {
			// Root of tree
			case ROOT_ID:
				printStartNode(cpg);
				print(getChild(ast, PACKAGE_DEF));
				printChildren(ast, IMPORT);

				// there will be either class or interface
				print(getChild(ast, CLASS_DEF));    
				print(getChild(ast, INTERFACE_DEF));  
				printEndNode(cpg);
				break;

			// both package and import are one-line statements
			case PACKAGE_DEF:
			case IMPORT:
				printCpg("", ";", "Cbreak=\"hard\"");
				if (ast.getType() == PACKAGE_DEF) {
					printNode(cpo_builtin, "package");
					printStartNode(cpo_defined);
				} else {
					printNode(cpo_builtin, "import");
					printStartNode(cpo_imported);
				}
				print(ast.getFirstChild());
				printEndNode(cpo);
				printEndNode(cpg);
				break;

			// class and interface begin blocks, with a declaration on top
			case CLASS_DEF:
			case INTERFACE_DEF:
				printStartNode(cpg);
				print(getChild(ast, MODIFIERS));
				if (ast.getType() == CLASS_DEF) {
					printNode(cpo_builtin, "class");
				} else {
					printNode(cpo_builtin, "imported");
				}
				printStartNode(cpo_defined);
				print(getChild(ast, IDENT));
				printEndNode(cpo_defined);
				print(getChild(ast, EXTENDS_CLAUSE));
				print(getChild(ast, IMPLEMENTS_CLAUSE));
				printEndNode(cpg);

				printCpg("{", "}", "breakO=\"hard\"");
				print(getChild(ast, OBJBLOCK));
				printEndNode(cpg);
				break;

			// such as "private". It exists even if none are given,
			// so we must check for children first. ditto for the
			// next two
			case MODIFIERS:
				if (hasChildren(ast)) {
					printChildren(ast);
				}
				break;

			// class extends
			case EXTENDS_CLAUSE:
				if (hasChildren(ast)) {
					printNode(cpo_builtin, "extends");
					printAllChildren(ast, cpo_imported);
					out.append(" ");
				}
				break;

			// class implements
			case IMPLEMENTS_CLAUSE:
				if (hasChildren(ast)) {
					printNode(cpo_builtin, "implements");
					printAllChildren(ast, cpo_imported);
					out.append(" ");
				}
				break;

			// eg: java.io
			// java <-- dot --> io
			case DOT:
				print(child1);
				out.append(".");
				print(child2);
				break;

			// the typical order of things within a class is:
			// variable definitions
			// static initialization block
			// instance initialization block
			// constructors
			// methods
			// inner classes
			case OBJBLOCK:
				printAllChildren(ast, VARIABLE_DEF);
				printAllChildren(ast, STATIC_INIT);
				printAllChildren(ast, INSTANCE_INIT);
				printAllChildren(ast, CTOR_DEF);
				printAllChildren(ast, METHOD_DEF);
				printAllChildren(ast, CLASS_DEF);
				break;

			// methods/functions
			case CTOR_DEF:
			case METHOD_DEF:
				printStartNode(cpg);			// for method
					
				printStartNode(cpg);			// definition
				print(getChild(ast, MODIFIERS));
				if (ast.getType() != CTOR_DEF) {
					print(getChild(ast, TYPE));
				}
				printStartNode(cpo_defined);
				print(getChild(ast, IDENT));		// method name
				printEndNode(cpo_defined);
				print(getChild(ast, PARAMETERS));
				print(getChild(ast, LITERAL_throws));
				printEndNode(cpg);
				
				print(getChild(ast, SLIST));		// body
				printEndNode(cpg);
				break;

			// function arguments
			case PARAMETERS:
				printCpg("(", ")");
				if (child2 == null) {
					printStartNode(cpg);
					print(getChild(ast, PARAMETER_DEF));
					printEndNode(cpg);
				} else {
					printSequence(ast, "<cpg close=\",\">" , "</cpg>", "<cpg>");
				}
				out.append("</cpg>");
				break;

			// the actual arguments
			case PARAMETER_DEF:
				print(getChild(ast, MODIFIERS));
				print(getChild(ast, TYPE));
				printStartNode(cpi);
				print(getChild(ast, IDENT));	// variable name				
				printEndNode(cpi);
				break;

			// variable decleration
			// we also need to take care of the case when it appears
			// within for ex, a for loop intialisation statement
			case VARIABLE_DEF:
				if (printSemi(parent) || (parent != null && parent.getType() == OBJBLOCK)) {
					printCpg("", ";", "Cbreak=\"hard\"");	
				} 

				print(getChild(ast, MODIFIERS));
				print(getChild(ast, TYPE));
				printStartNode(cpi);
				print(getChild(ast, IDENT));	// variable name
				printEndNode(cpi);
				print(getChild(ast, ASSIGN));

				if (printSemi(parent) || (parent != null && parent.getType() == OBJBLOCK)) {
					printEndNode(cpg);
				}
				
				break;

			// variable type
			case TYPE:
				printStartNode(cptype);
				print(ast.getFirstChild());
				printEndNode(cptype);
				break;

			// an array is being declared
			case ARRAY_DECLARATOR:
				if (child1 == null) {		
					out.append("[]");
				} else if (child1.getType() == EXPR) {
					out.append("[");
					print(child1);
					out.append("]");
				} else {
					print(child1);
					out.append("[]");
				}
				break;

			// assignment
			case ASSIGN:
				if (child2 != null) {
					if (child1.getType() == IDENT) {
						printStartNode(cpi);
					}
					print(child1);
					if (child1.getType() == IDENT) {
						printEndNode(cpi);
					}
					printNode(cpo_builtin, "=");
					if (child2.getType() == IDENT) {
						printStartNode(cpi);
					}
					print(child2);
					if (child2.getType() == IDENT) {
						printEndNode(cpi);
					}
				} else {				// LHS already handled
					printNode(cpo_builtin, "=");
					if (!printChildren(ast, cpi, IDENT)) 
						print(child1);
				}
				break;


			case EXPR:
				if (printSemi(parent) || (parent != null && parent.getType() == OBJBLOCK)) {
					printCpg("", ";", "Cbreak=\"hard\"");	
				}
				if (!printChildren(ast, cpi, IDENT)) 
					print(child1);
				if (printSemi(parent) || (parent != null && parent.getType() == OBJBLOCK)) {
					printEndNode(cpg);
				}
				break;

			case ARRAY_INIT:
				printCpg("{", "}");
				printSequence(ast, "<cpg close=\",\">" , "</cpg>", "<cpg>");
				printEndNode(cpg);
				break;

			// list of statements (with a method)
			case SLIST:
				printCpg("{", "}", "breakO=\"hard\"");
				printChildren(ast);
				printEndNode(cpg);
				break;

			// binary operators
			case PLUS:
			case MINUS:
			case DIV:
			case MOD:
			case NOT_EQUAL:
			case EQUAL:
			case LT:
			case GT:
			case LE:
			case GE:
			case LOR:
			case LAND:
			case BOR:	
			case BXOR:
			case BAND:
			case SL:
			case SR:
			case BSR:
			case LITERAL_instanceof: 
			case PLUS_ASSIGN:
			case MINUS_ASSIGN:
			case STAR_ASSIGN:
			case DIV_ASSIGN:
			case MOD_ASSIGN:
			case SR_ASSIGN:
			case BSR_ASSIGN:
			case SL_ASSIGN:
			case BAND_ASSIGN:
			case BXOR_ASSIGN:
			case BOR_ASSIGN:
				printBinaryOperator(ast);
				break;

			// for loop
			case LITERAL_for:
				printNode(cpo_builtin, "for");

				printCpg("(", ")");
				
				printCpg("", ";");
				print(getChild(ast, FOR_INIT));
				printEndNode(cpg);

				printCpg("", ";");
				print(getChild(ast, FOR_CONDITION));
				printEndNode(cpg);

				printStartNode(cpg);
				print(getChild(ast, FOR_ITERATOR));
				printEndNode(cpg);

				printEndNode(cpg);

				print(getChild(ast, SLIST));
				break;

			case FOR_INIT:
			case FOR_CONDITION:
			case FOR_ITERATOR:
				print(child1);
				break;

			// list of parameters
			// we enclose each parameter in a cpg, and if there are
			// multiple, give commas at the close
			case ELIST:
				if (child2 == null) {
					print(getChild(ast, EXPR));
				} else {
					printSequence(ast, "<cpg close=\",\">" , "</cpg>", "<cpg>");
				}
				break;

			// ++, --
			case POST_INC:
			case POST_DEC:
				if (child1.getType() == IDENT) {
					printStartNode(cpi);
				}
				print(child1);
				if (child1.getType() == IDENT) {
					printEndNode(cpi);
				}
				printNode(cpo_builtin, name(ast));
				break;

			case BNOT:
			case LNOT:
			case INC:
			case DEC:
			case UNARY_MINUS:
			case UNARY_PLUS:
				out.append(name(ast));
				printWithParens(ast, child1);
				break;

			case LITERAL_new:
				printNode(cpo_builtin, "new");
				printStartNode(cpo);
				print(child1);
				printEndNode(cpo);
				if (child2.getType() != ARRAY_DECLARATOR) {
					printCpg("(", ")");
				}
				print(child2);
				if (child2.getType() != ARRAY_DECLARATOR) {
					printEndNode(cpg);
				}
				// "new String[] {...}": the stuff in {} is child3
				if (child3 != null) {
					print(child3);
				}
				break;

			// method call
			case METHOD_CALL:
				printStartNode(cpo);
				print(child1); // ident
				printEndNode(cpo);
				
				printCpg("(", ")");
				print(child2); // elist
				printEndNode(cpg);
				break;

			case LITERAL_return:
				printCpg("", ";", "Cbreak=\"hard\"");
				printNode(cpo_builtin, "return");
				print(child1);
				printEndNode(cpg);
				break;

			case INSTANCE_INIT:
				printCpg("{", "}", "Obreak=\"hard\"");
				print(child1);
				printEndNode(cpg);
				break;

			case STATIC_INIT:
				printNode(cpo_builtin, "static");
				printCpg("{", "}", "Obreak=\"hard\"");
				print(child1);
				printEndNode(cpg);
				break;

			case TYPECAST:
				printCpg("(", ")");
				print(child1);
				printEndNode(cpg);
				print(child2);
				break;

			case LITERAL_switch:
				printNode(cpo_builtin, "switch");

				printCpg("(", ")");
				print(child1);	// the EXPR to switch on
				printEndNode(cpg);

				printCpg("{", "}", "Obreak=\"hard\"");
				printChildren(ast, "",  CASE_GROUP);
				printEndNode(cpg);

				break;

			case CASE_GROUP:
				printChildren(ast, LITERAL_case);
				printChildren(ast, LITERAL_default);
				printChildren(ast, SLIST);
				break;

			case LITERAL_case:
				printCpg("", ":", "Cbreak=\"hard\"");
				printNode(cpo_builtin, "case");
				print(child1);	// an EXPR
				printEndNode(cpg);
				break;

			case LITERAL_default:
				printCpg("", ":", "Cbreak=\"hard\"");
				printNode(cpo_builtin, "default");
				print(child1);	// an EXPR
				printEndNode(cpg);
				break;

			// In codeml, this could be either cpo or cpi,
			// so we handle it in parent
			case IDENT:
				out.append(ast.getText());
				break;

			// Strings
			case CHAR_LITERAL:
			case STRING_LITERAL:
				printNode(cpb_string, ast.getText());
				break;

			// Numbers
			case NUM_INT:
			case NUM_LONG:
			case NUM_FLOAT:
			case NUM_DOUBLE:
				printNode(cpb_number, ast.getText());
				break;

			// Misc builtin names
			// markup handled in parent
			case LITERAL_void:
			case LITERAL_boolean:
			case LITERAL_byte:
			case LITERAL_char:
			case LITERAL_short:
			case LITERAL_int:
			case LITERAL_float:
			case LITERAL_long:
			case LITERAL_double:
			case SEMI:
				out.append(name(ast));
				break;

			case LITERAL_true:
			case LITERAL_false:
			case LITERAL_null:
			case LITERAL_this:
			case LITERAL_super:
			case LITERAL_private:
			case LITERAL_package:
			case LITERAL_public:
			case LITERAL_protected:
			case LITERAL_static:
			case LITERAL_transient:
			case LITERAL_native:
			case LITERAL_threadsafe:
			case LITERAL_synchronized:
			case LITERAL_volatile:
			case FINAL:
			case ABSTRACT:
				printNode(cpo_builtin, name(ast));
				break;


			case LITERAL_continue:
			case LITERAL_break:
				printCpg("", ";", "Cbreak=\"hard\"");
				out.append(name(ast));
				printEndNode(cpg);
				break;

			case INDEX_OP:
				printStartNode(cpo);
				print(child1);		// an IDENT
				printEndNode(cpo);
				printCpg("[", "]");
				print(child2);	// an EXPR
				printEndNode(cpg);
				break;

			case EMPTY_STAT:
				out.append(";");	// empty statement
				break;

			// Distinguish between "import x.y.*" and "x = 1 * 3"
			case STAR:
				if (hasChildren(ast)) {	// the binary mult. operator
					printBinaryOperator(ast);
				}
				else {	// the special "*" in import:
					out.append("*");
				}
				break;

			case LITERAL_throws:
				out.append("throws ");
				printChildren(ast, ", ");
				break;

			case LITERAL_if:
				out.append("<cpo>if</cpo>");
				out.append("<cpg open=\"(\" close=\")\">");
				print(child1);	// the "if" condition: an EXPR
				out.append("</cpg>");
				print(child2);	// the "then" clause is an SLIST
				if (child3 != null) {
					out.append("else ");
					print(child3);	// optional "else" clause: an SLIST
				}
				break;

			case LITERAL_while:
				out.append("while (");
				print(child1);	// the "while" condition: an EXPR
				out.append(") ");
				print(child2);	// an SLIST
				break;

			case LITERAL_do:
				out.append("do ");
				print(child1);		// an SLIST
				out.append(" while (");
				print(child2);		// an EXPR
				out.append(");");
				break;

			case LITERAL_try:
				out.append("try ");
				print(child1);	// an SLIST
				printChildren(ast, " ", LITERAL_catch);
				break;

			case LITERAL_catch:
				out.append("catch (");
				print(child1);	// a PARAMETER_DEF
				out.append(") ");
				print(child2);	// an SLIST
				break;

			// the first child is the "try" and the second is the SLIST
			case LITERAL_finally:
				print(child1);
				out.append(" finally ");
				print(child2);	// an SLIST
				break;

			case LITERAL_throw:
				out.append("throw ");
				print(child1);
				out.append(";");
				break;

			// the dreaded trinary operator
			case QUESTION:
				print(child1);
				out.append(" ? ");
				print(child2);
				out.append(" : ");
				print(child3);
				break;

			// (note: the java.g provided by default skips comments)
			case SL_COMMENT:
				break;

			case ML_COMMENT:
				break;

			case LITERAL_class:
				out.append("class");
				break;
					
			/*case LITERAL_assert:
				out.append("assert ");
				print(child1);
				if (child2 != null) {
					out.append(" : ");
					print(child2);
				}
				break;*/
					
			default:
				break;


/* The following are tokens, but I don't think JavaRecognizer 
   ever produces an AST with one of these types:
			case COMMA:
			case LITERAL_implements:
			case LITERAL_class:
			case LITERAL_extends:
			case EOF:
			case NULL_TREE_LOOKAHEAD:
			case BLOCK:
			case LABELED_STAT:	// refuse to implement on moral grounds :)
			case LITERAL_import:
			case LBRACK:
			case RBRACK:
			case LCURLY:
			case RCURLY:
			case LPAREN:
			case RPAREN:
			case LITERAL_else:	// else is a child of "if" AST
			case COLON:		// part of the trinary operator
			case WS:		// whitespace
			case ESC:
			case HEX_DIGIT:
			case VOCAB:

			case EXPONENT:	// exponents and float suffixes are left in the NUM_FLOAT
			case FLOAT_SUFFIX
*/

		}


		stack.pop();
	}



	/**
	 * Checks the precedence of a give ast.
	 * A Precendence table. Here are operator precendences (from java.g):
	 *     lowest  (13)  = *= /= %= += -= <<= >>= >>>= &= ^= |=
	 *             (12)  ?:
	 *             (11)  ||
	 *             (10)  &&
	 *             ( 9)  |
	 *             ( 8)  ^
	 *             ( 7)  &
	 *             ( 6)  == !=
	 *             ( 5)  < <= > >=
	 *             ( 4)  << >>
	 *             ( 3)  +(binary) -(binary)
	 *             ( 2)  * / %
	 *             ( 1)  ++ -- +(unary) -(unary)  ~  !  (type)
	 *                   []   () (method call)  . (dot -- identifier qualification)
	 *                   new   ()  (explicit parenthesis)
	 *
	 * @param ast the ast to check
	 * @return an integer value of the precedence. To be used for comparisons.
	 */
	private static int getPrecedence(AST ast) {
		if (ast == null) {
			return -2;				
		}
		switch (ast.getType()) {
			case EXPR:
				return getPrecedence(ast.getFirstChild());

			case ASSIGN:
			case PLUS_ASSIGN:
			case MINUS_ASSIGN:
			case STAR_ASSIGN:
			case DIV_ASSIGN:
			case MOD_ASSIGN:
			case SR_ASSIGN:
			case BSR_ASSIGN:
			case SL_ASSIGN:
			case BAND_ASSIGN:
			case BXOR_ASSIGN:
			case BOR_ASSIGN:
				return 13;

			case QUESTION:
				return 12;
			case LOR:
				return 11;
			case LAND:
				return 10;
			case BOR:
				return 9;
			case BXOR:
				return 8;
			case BAND:
				return 7;

			case NOT_EQUAL:
			case EQUAL:
				return 6;

			case LT:
			case GT:
			case LE:
			case GE:
			case LITERAL_instanceof: 
				return 5;

			case SL:
			case SR:
			case BSR:	// not in chart above, but I would guess it goes here
				return 4;

			case PLUS:
			case MINUS:
				return 3;

			case DIV:
			case MOD:
			case STAR:
				return 2;

			case INC:
			case DEC:
			case POST_INC:
			case POST_DEC:
			case UNARY_PLUS:
			case UNARY_MINUS:
			case LNOT:
			case BNOT:
			case TYPE:
				return 1;

			case METHOD_CALL:
			case ARRAY_DECLARATOR:
			case DOT:
				return 0;

			case LITERAL_new:
				return -1;

		}
		// for any non-operator, return a value which will cause it to NOT need parens.
		return -2;				
	}

	/**
	 * Parses file, constructs ast and determines output.
	 *
	 * @param fileName the Java source file
	 */
	public Presentation(String fileName) throws FileNotFoundException {
		FileReader fr = new FileReader(fileName);
		
		try {
			JavaLexer lexer = new JavaLexer(fr);
			lexer.setFilename(fileName);

			JavaRecognizer parser = new JavaRecognizer(lexer);
			parser.setFilename(fileName);

			parser.compilationUnit();
			
			ASTFactory factory = new ASTFactory();
			AST ast = factory.create(ROOT_ID,"AST ROOT");
			ast.setFirstChild(parser.getAST());
			print(ast);
		} catch (Exception e) {
			System.out.println("Error parsing file: " + e.getMessage());
		}
	}

	/**
	 * Gets the CodeML markup.
	 *
	 * @return a string
	 */
	public String getMarkup() {
		String result = "<pcode format=\"java\">" + out.toString() + "</pcode>";
		return result;
	}
}
