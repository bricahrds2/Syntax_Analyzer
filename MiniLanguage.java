import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.*;
import java.io.*;

public class MiniLanguage {

    public static int currentColumn = 0;
    public static int subColumn = 1;
    public static int subLine = 1;
    public static int currentLine = 1;
    public static String compChar = "";
    public static String subChar = "";
    public static char currentChar;
    public static int currentCol2 = 0;
    public static int kindVar = 0;
    public static String kindtype = "";
    public static int first = 0;
    public static int error = 2;
    public static String choice = "";

    // The Leximes
    // Keywords
    public static String[] keyword = { "if", "then", "else", "false", "program", "int", "fi", "while", "bool" };
    public static HashSet<String> keywordSet = new HashSet<>();

    // Operators
    public static String[] operator = { "+", "-", "*", "/", "=" };
    public static HashSet<String> operatorSet = new HashSet<>();

    // Symbols
    public static String[] symbol = { "(", ")", ";", ":", ":=", "<", "=", "<=", "==", ">=", ">" };
    public static HashSet<String> symbolSet = new HashSet<>();

    // Numbers
    public static String[] digit = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
    public static HashSet<String> digitSet = new HashSet<>();

    // Letters
    public static char[] letter = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
            'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
    public static HashSet<Character> letterSet = new HashSet<>();

    public static String[] end = { "end" };
    public static HashSet<String> endSet = new HashSet<>();

    public static void main(String args[]) {

        for (String element : keyword) {
            keywordSet.add(element);
        }
        for (String element : operator) {
            operatorSet.add(element);
        }
        for (String element : symbol) {
            symbolSet.add(element);
        }
        for (String element : digit) {
            digitSet.add(element);
        }
        for (char element : letter) {
            letterSet.add(element);
        }

        // Array to store the contents
        char[] array = new char[1000];
        // Intantiate the choice,q to store the quit and scan to prevent errors and
        // global search
        choice = "";
        String q = "quit";
        String ee = "exit";
        Scanner scan = new Scanner(System.in);

        // do - whie to loop endlessly so that it wont break when it reaches an
        // exception handle
        do {

            // try- catch to run the code and catch exceptions
            try {

                System.out.println("Which file do you want? If not type quit to end");

                // Do function to get user input to determine which file to

                // scanner to take the users input
                choice = scan.next();

                // Used buffer reader to read file
                File f = new File(choice);
                char[] array2 = new char[(int) f.length() + 1];
                // Buffered Reader rather than
                BufferedReader buffer = new BufferedReader(new FileReader(f));
                buffer.read(array2);

                // if statement to ensure if the file is empty, it doesnt print anything and
                // doesnt give any errors.
                if (array2 != null) {
                    // For Loop to traverse through the array to print characters at
                    // Has the nextChar(), currentChar() and position() implemented in this loop.

                    next(array2, buffer);

                    while ((int) currentChar != 0) {
                        // Reads the carraige return and end of line to only print on space
                        // System.out.println(position() + " " + kind() + " " + compChar);
                        // program(array2, buffer);
                        next(array2, buffer);
                    }
                    program(array2, buffer);
                }
                // System.out.println(array);

                buffer.close();
                currentCol2 = 0;
                currentLine = 1;
                currentColumn = 0;
                subColumn = 1;
                subLine = 1;
                error = 0;

                // scan.close();
                // catch exception to see if the file exists
            } catch (FileNotFoundException e) {
                System.out.println("File not found");

                // catch exception for IOException
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchElementException e) {
                System.out.println("No line found");
            }

        } while (!choice.equals(q) && !(choice.equals(ee)));
        // is printed when the quit is typed
        System.out.println("Ended");
        scan.close();
    }

    // next() to get the next character in the file
    public static void nextChar(char[] array2) {
        // need to fix this
        currentChar = array2[currentCol2];
    }

    // nextchar to go through the end of the file until the end and get the nextChar

    public static void next(char[] arr, BufferedReader buff) throws IOException {
        currentColumn = subColumn;
        currentLine = subLine;
        for (int i = currentCol2; i < arr.length; currentCol2++) {
            i = currentCol2;
            currentChar = arr[i];

            // if the first character is a letter, it will check if the next character is a
            // letter or digit, if it is, it will add it to the compChar, if not, it will

            // if statement to check if the currentChar is a whitespace or new line, adds
            // the character to a compChar to create a token
            if (currentChar == 47) {
                if (arr[i + 1] == '/') {
                    while (arr[i] != '\n') {
                        i++;
                    }
                    currentCol2 = i;
                    currentChar = arr[i];
                    subLine++;
                    subColumn = 1;
                    currentColumn = subColumn;
                    currentLine = subLine;
                    break;
                }
            }
            if ((int) currentChar != 32 && (int) currentChar != 10 && (int) currentChar != 13) {
                // subColumn++;
                if (subChar.isEmpty()) {
                    currentColumn = subColumn;
                }
                if (!subChar.isEmpty() && letterSet.contains(subChar.charAt(0))) {
                    if (currentChar != '_' && symbolSet.contains(Character.toString(currentChar))) {
                        compChar = subChar;
                        subChar = "";
                        // currentColumn = subColumn;
                        break;
                    }
                }
                if (!subChar.isEmpty() && digitSet.contains(subChar.substring(0, 1))) {
                    if (symbolSet.contains(Character.toString(currentChar)) || letterSet.contains(currentChar)) {
                        compChar = subChar;
                        subChar = "";
                        // currentColumn = subColumn;
                        break;
                    }
                }
                // error when illegal character isnt found in any of the sets
                if (!subChar.isEmpty()) {
                    if (!letterSet.contains(currentChar) && !digitSet.contains(currentChar + "")
                            && !symbolSet.contains(Character.toString(currentChar)) && currentChar != '_'
                            && !operatorSet.contains(Character.toString(currentChar)) && currentChar != 0) {
                        System.out.println(">>>>>>>> " + position() + " " + "Illegal Character" + " " + currentChar);
                        subChar = "";
                        currentChar = 0;
                        error = 1;
                        break;
                    }
                }
                subChar = subChar + currentChar;

            }
            // subColumn++;
            if ((int) currentChar == 10) {
                subLine++;
                subColumn = 1;
                currentCol2++;
            } else {
                subColumn++;
            }

            if ((int) currentChar == 32 || (int) currentChar == 10 || (int) currentChar == 0) {
                compChar = subChar;
                if ((int) currentChar == 0) {
                    System.out.println(position() + " " + kind() + " " + compChar);
                    error = 0;
                    System.out.println("end of text");
                }
                if (!subChar.isEmpty()) {
                    subChar = "";
                    break;
                } else {
                    // subColumn++;
                    // currentColumn = subColumn;
                }
            }

            if ((int) currentChar == 0 && !compChar.isEmpty()) {
                compChar = subChar;
            }

        }

        // call noErrorReported() to check if there are any errors
        if (error == 0) {
            System.out.println("Concluded lexical analysis on " + choice);
        }
    }

    public static String kind() {
        // String to store the kind of token
        String kind = "";
        int matchL = 0;
        // if statement to check if the compChar is a keyword
        for (int i = 0; i < keyword.length; i++) {
            if (compChar.contains(keyword[i])) {
                kind = compChar;
                compChar = "";
                return kind;
                // break;
            } else {
                // kind = "ID";
            }
        }
        for (int i = 0; i < compChar.length(); i++) {
            char c = compChar.charAt(i);
            if (letterSet.contains(c)) {
                matchL++;
            } else if (c == '_') {
                matchL++;
            }
        }
        if (matchL == compChar.length()) {
            return "ID";
        }
        for (String i : symbol) {
            if (compChar.contains(i)) {
                kind = compChar;
                compChar = "";
                return kind;
            }
        }
        for (String i : operator) {
            if (compChar.contains(i)) {
                kind = compChar;
                compChar = "";
                return kind;
            }
        }
        for (int i = 0; i < end.length; i++) {
            if (compChar.contains(end[i])) {
                return kind;
            }
        }
        return "NUM";
    }

    public static String position() {
        return currentLine + ":" + currentColumn;
    }

    public static void program(char[] a, BufferedReader b) throws IOException {
        match("program", a, b);
        match("ID", a, b);
        match(":", a, b);
        body(a, b);
        match("end", a, b);
    }

    public static void match(String symbol, char[] arr, BufferedReader buff) throws IOException {
        if (kind() == symbol) {
            next(arr, buff);
        } else {
            // Error report
            System.out.print("-->>>> Its seems at the position " + position());
            System.out.print(" There is " + kind());
            System.out.print(" Instead there should be " + symbol);
            System.out.println(" <<<<--");
            // get out of the program function and go back to the main
            throw new IllegalArgumentException("Symbol mismatch");
        }
    }

    public static void body(char[] a, BufferedReader b) throws IOException {
        declarations(a, b);
        statements(a, b);
    }

    // bool or inte followed by a ;
    public static void declarations(char[] a, BufferedReader b) throws IOException {
        declaration(a, b);
        match("{", a, b);
        declaration(a, b);
        match("}", a, b);
    }

    public static void declaration(char[] a, BufferedReader b) throws IOException {
        if (compChar.equals("int")) {
            match("int", a, b);
        } else if (compChar.equals("bool")) {
            match("bool", a, b);
        }
        Identifier(a, b);
    }

    public static void statements(char[] a, BufferedReader b) throws IOException {
        statement(a, b);
        match("{", a, b);
        match(";", a, b);
        statement(a, b);
        match("}", a, b);

    }

    public static void statement(char[] a, BufferedReader b) throws IOException {
        if (compChar.equals("if")) {
            conditionalStatement(a, b);
        } else if (compChar.equals("while")) {
            loopStatement(a, b);
        } else if (compChar.equals("ID")) {
            assignmentStatement(a, b);
        } else if (compChar.equals("fi")) {
            match("fi", a, b);
        } else if (compChar.equals("end")) {
            match("end", a, b);
        }
    }

    public static void assignmentStatement(char[] a, BufferedReader b) throws IOException {
        match("ID", a, b);
        match(":=", a, b);
        Expression(a, b);
    }

    public static void conditionalStatement(char[] a, BufferedReader b) throws IOException {
        match("if", a, b);
        Expression(a, b);
        match("then", a, b);
        body(a, b);
        match("else", a, b);
        body(a, b);
        match("fi", a, b);
    }

    public static void loopStatement(char[] a, BufferedReader b) throws IOException {
        match("while", a, b);
        Expression(a, b);
        match("do", a, b);
        body(a, b);
        match("od", a, b);
    }

    // Prints the assignment statement
    public static void PrintStatement(char[] a, BufferedReader b) throws IOException {
        match("print", a, b);
        Expression(a, b);
    }

    public static void Expression(char[] a, BufferedReader b) throws IOException {
        simpleExpression(a, b);
        RelationalOperator(a, b);
        match(compChar, a, b);
        simpleExpression(a, b);
    }

    public static void RelationalOperator(char[] a, BufferedReader b) throws IOException {
        if (compChar.equals("<")) {
            match("<", a, b);
        } else if (compChar.equals("=")) {
            match("=", a, b);
        } else if (compChar.equals("<=")) {
            match("<=", a, b);
        } else if (compChar.equals("==")) {
            match("==", a, b);
        } else if (compChar.equals(">=")) {
            match(">=", a, b);
        } else if (compChar.equals(">")) {
            match(">", a, b);
        }
    }

    public static void simpleExpression(char[] a, BufferedReader b) throws IOException {
        term(a, b);
        // if (compChar.equals("+") || compChar.equals("-")) {
        AdditiveOperator(a, b);
        term(a, b);
    }

    public static void AdditiveOperator(char[] a, BufferedReader b) throws IOException {
        if (compChar.equals("+")) {
            match("+", a, b);
        } else if (compChar.equals("-")) {
            match("-", a, b);
        } else if (compChar.equals("or")) {
            match("or", a, b);
        }
    }

    public static void term(char[] a, BufferedReader b) throws IOException {
        Factor(a, b);
        // if (compChar.equals("*") || compChar.equals("/") || compChar.equals("and"))
        // {
        MultiplicativeOperator(a, b);
        Factor(a, b);
    }

    public static void MultiplicativeOperator(char[] a, BufferedReader b) throws IOException {
        if (compChar.equals("*")) {
            match("*", a, b);
        } else if (compChar.equals("/")) {
            match("/", a, b);
        } else if (compChar.equals("and")) {
            match("and", a, b);
        }
    }

    // factor uses the unary operator and literal and identifier and expression
    public static void Factor(char[] a, BufferedReader b) throws IOException {
        UnaryOperator(a, b);
        if (compChar.equals("true") || compChar.equals("false")) {
            Literal(a, b);
        } else if (compChar.equals("ID")) {
            Identifier(a, b);
        } else if (compChar.equals("(")) {
            Expression(a, b);
            if (compChar.equals(")")) {
                match(")", a, b);
            }
        }
    }

    // unary operator
    public static void UnaryOperator(char[] a, BufferedReader b) throws IOException {
        if (compChar.equals("-")) {
            match("-", a, b);
        } else if (compChar.equals("not")) {
            match("not", a, b);
        }
    }

    // literal operator
    public static void Literal(char[] a, BufferedReader b) throws IOException {
        if (compChar.equals("true") || compChar.equals("false")) {
            BooleanLiteral(a, b);
        } else if (compChar.equals("NUM")) {
            IntegerLiteral(a, b);
        }
    }

    // Boolean Literal
    public static void BooleanLiteral(char[] a, BufferedReader b) throws IOException {
        if (compChar.equals("true")) {
            match("true", a, b);
        } else if (compChar.equals("false")) {
            match("false", a, b);
        }
    }

    // Integer Literal
    public static void IntegerLiteral(char[] a, BufferedReader b) throws IOException {
        Digit(a, b);
        match("{", a, b);
        Digit(a, b);
        match("}", a, b);
    }

    // Indentifier letter followed by a letter or digit or _
    public static void Identifier(char[] a, BufferedReader b) throws IOException {
        Letter(a, b);
        match("{", a, b);
        Letter(a, b);
        match("}", a, b);
    }

    // Digit
    public static void Digit(char[] a, BufferedReader b) throws IOException {
        if (compChar.equals("0")) {
            match("0", a, b);
        } else if (compChar.equals("1")) {
            match("1", a, b);
        } else if (compChar.equals("2")) {
            match("2", a, b);
        } else if (compChar.equals("3")) {
            match("3", a, b);
        } else if (compChar.equals("4")) {
            match("4", a, b);
        } else if (compChar.equals("5")) {
            match("5", a, b);
        } else if (compChar.equals("6")) {
            match("6", a, b);
        } else if (compChar.equals("7")) {
            match("7", a, b);
        } else if (compChar.equals("8")) {
            match("8", a, b);
        } else if (compChar.equals("9")) {
            match("9", a, b);
        }
    }

    // Letter and Capital Letter
    public static void Letter(char[] a, BufferedReader b) throws IOException {
        if (compChar.equals("a")) {
            match("a", a, b);
        } else if (compChar.equals("b")) {
            match("b", a, b);
        } else if (compChar.equals("c")) {
            match("c", a, b);
        } else if (compChar.equals("d")) {
            match("d", a, b);
        } else if (compChar.equals("e")) {
            match("e", a, b);
        } else if (compChar.equals("f")) {
            match("f", a, b);
        } else if (compChar.equals("g")) {
            match("g", a, b);
        } else if (compChar.equals("h")) {
            match("h", a, b);
        } else if (compChar.equals("i")) {
            match("i", a, b);
        } else if (compChar.equals("j")) {
            match("j", a, b);
        } else if (compChar.equals("k")) {
            match("k", a, b);
        } else if (compChar.equals("l")) {
            match("l", a, b);
        } else if (compChar.equals("m")) {
            match("m", a, b);
        } else if (compChar.equals("n")) {
            match("n", a, b);
        } else if (compChar.equals("o")) {
            match("o", a, b);
        } else if (compChar.equals("p")) {
            match("p", a, b);
        } else if (compChar.equals("q")) {
            match("q", a, b);
        } else if (compChar.equals("r")) {
            match("r", a, b);
        } else if (compChar.equals("s")) {
            match("s", a, b);
        } else if (compChar.equals("t")) {
            match("t", a, b);
        } else if (compChar.equals("u")) {
            match("u", a, b);
        } else if (compChar.equals("v")) {
            match("v", a, b);
        } else if (compChar.equals("w")) {
            match("w", a, b);
        } else if (compChar.equals("x")) {
            match("x", a, b);
        } else if (compChar.equals("y")) {
            match("y", a, b);
        } else if (compChar.equals("z")) {
            match("z", a, b);
        } else if (compChar.equals("A")) {
            match("A", a, b);
        } else if (compChar.equals("B")) {
            match("B", a, b);
        } else if (compChar.equals("C")) {
            match("C", a, b);
        } else if (compChar.equals("D")) {
            match("D", a, b);
        } else if (compChar.equals("E")) {
            match("E", a, b);
        } else if (compChar.equals("F")) {
            match("F", a, b);
        } else if (compChar.equals("G")) {
            match("G", a, b);
        } else if (compChar.equals("H")) {
            match("H", a, b);
        } else if (compChar.equals("I")) {
            match("I", a, b);
        } else if (compChar.equals("J")) {
            match("J", a, b);
        } else if (compChar.equals("K")) {
            match("K", a, b);
        } else if (compChar.equals("L")) {
            match("L", a, b);
        } else if (compChar.equals("M")) {
            match("M", a, b);
        } else if (compChar.equals("N")) {
            match("N", a, b);
        } else if (compChar.equals("O")) {
            match("O", a, b);
        } else if (compChar.equals("P")) {
            match("P", a, b);
        } else if (compChar.equals("Q")) {
            match("Q", a, b);
        } else if (compChar.equals("R")) {
            match("R", a, b);
        } else if (compChar.equals("S")) {
            match("S", a, b);
        } else if (compChar.equals("T")) {
            match("T", a, b);
        } else if (compChar.equals("U")) {
            match("U", a, b);
        } else if (compChar.equals("V")) {
            match("V", a, b);
        } else if (compChar.equals("W")) {
            match("W", a, b);
        } else if (compChar.equals("X")) {
            match("X", a, b);
        } else if (compChar.equals("Y")) {
            match("Y", a, b);
        } else if (compChar.equals("Z")) {
            match("Z", a, b);
        }
    }

}
