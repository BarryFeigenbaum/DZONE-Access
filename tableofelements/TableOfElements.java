package tableofelements;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/*
Java Code Challenge is a new regular segment taking the best challenge from Reddit's dailyprogrammer. 
Things are a little different here as we're focused on Java. 
A working solution is not enough; we're looking for the cleanest Java code with tests. 
3rd party libraries are welcome but if you can do it without it will be easier for others to comprehend.

If you can fit your solution in the comments then go for it, but preferably put your answer in GitHub and link in the comments. 
Next week we'll be sharing the best solutions and sharing the best code practices we see!

This is a beefier 2-part challenge and may take a bit longer to do- I'm really excited to see what solutions you come up with!

Description
The inhabitants of the planet Splurth are building their own periodic table of the elements. 
Just like Earth's periodic table has a chemical symbol for each element (H for Hydrogen, Li for Lithium, etc.), so does Splurth's. 
However, their chemical symbols must follow certain rules:

All chemical symbols must be exactly two letters, so B is not a valid symbol for Boron.
Both letters in the symbol must appear in the element name, but the first letter of the element name does not necessarily need to appear in the symbol. So Hg is not valid for Mercury, but Cy is.
The two letters must appear in order in the element name. So Vr is valid for Silver, but Rv is not. 
To be clear, both Ma and Am are valid for Magnesium, because there is both an a that appears after an m, and an m that appears after an a.
If the two letters in the symbol are the same, it must appear twice in the element name. So Nn is valid for Xenon, but Xx and Oo are not.

As a member of the Splurth Council of Atoms and Atom-Related Paraphernalia, you must determine whether a proposed chemical symbol fits these rules.

Details
Write a function that, given two strings, one an element name and one a proposed symbol for that element, determines whether the symbol follows the rules. 
If you like, you may parse the program's input and output the result, but this is not necessary.

The symbol will have exactly two letters. Both element name and symbol will contain only the letters a-z. 
Both the element name and the symbol will have their first letter capitalized, with the rest lowercase. (If you find that too challenging, it's okay to instead assume that both will be completely lowercase.)

Examples
Zeddemorium, Zr -> true
Venkmine, Kn -> true
Stantzon, Zt -> false
Melintzum, Nn -> false
Tullium, Ty -> false

Optional Bonus Challenges
Given an element name, find the valid symbol for that name that's first in alphabetical order. E.g.Gozerium -> Ei, Slimyrine -> Ie.
Given an element name, find the number of distinct valid symbols for that name. E.g. Zuulon -> 11.
 */

/*
 * "Clean code" is an ambiguous term.  It can mean any of best style, most minimal, most readable, most changeable and more.  
 * I take it to mean easy to read and maintain and not trickiest/minimal code.
 * I also take it to mean not exploiting Java 8 features just to do that.
 */
public class TableOfElements {

    // no instances needed
    private TableOfElements() {
    }

    /**
     * Validate that a symbol is a legal symbol for element name.
     * 
     * @param name
     *            element name
     * @param symbol
     *            possible element symbol
     * @return true if a valid symbol
     */
    public static boolean validateElementSymbol(String name, String symbol) {
        validateValidateElementSymbolArguments(name, symbol);
        boolean result = false;
        name = name.toLowerCase();
        symbol = symbol.toLowerCase();
        int index1 = name.indexOf(symbol.charAt(0));
        if (index1 >= 0) {
            int index2 = name.indexOf(symbol.charAt(1), index1 + 1);
            result = index2 >= 0;
        }
        return result;
    }
    
    /**
     * Get first symbol for an element.
     * 
     * @param name element name
     * @return first symbol
     */
    public static String getFirstSymbol(String name) {
        return getAllSymbols(name).iterator().next();
    }

    /**
     * Get the count of symbols for an element.
     * 
     * @param name element name
     * @return number of possible symbols
     */
    public static int getSymbolCount(String name) {
        return getAllSymbols(name).size();
    }
    
    /**
     * Get all symbols for a name.
     * 
     * @param name
     *            element name
     * @return set of all possible symbols
     */
    public static Set<String> getAllSymbols(String name) {
        validateGetAllSymbolsArguments(name);
        name = name.toLowerCase();
        Set<String> result = new TreeSet<>();  // sorts values
        addSymbols(name.substring(0, 1), name.substring(1), result);
        return result;
    }

    /**
     * Recursive worker to enumerate all possible symbols for a name.
     * 
     * @param start
     *            prefix string (generally first letter)
     * @param rest
     *            rest of string (letters to build variants from)
     * @param result
     *            added variants
     */
    private static void addSymbols(String start, String rest, Set<String> result) {
        if (rest.length() > 0) {
            for (char c : rest.toCharArray()) {
                result.add(start.toUpperCase() + c);
            }
            addSymbols(rest.substring(0, 1), rest.substring(1), result);
        }
    }

    /**
     * Validate inputs. Not strictly needed by parameters of the project.
     * 
     * @param name
     *            element name
     */
    private static void validateGetAllSymbolsArguments(String name) {
        if (name == null || name.length() < 2) {
            throw new IllegalArgumentException("invalid name: " + name);
        }
    }

    /**
     * Validate inputs. Not strictly needed by parameters of the project.
     * 
     * @param name
     *            element name
     * @param symbol
     *            symbol
     */
    private static void validateValidateElementSymbolArguments(String name, String symbol) {
        if (symbol == null || symbol.length() != 2) {
            throw new IllegalArgumentException("invalid symbol: " + symbol);
        }
        if (name == null || name.length() < symbol.length()) {
            throw new IllegalArgumentException("invalid name: " + name);
        }
        validateChars(name);
        validateChars(symbol);
    }

    /**
     * Validate inputs. Not strictly needed by parameters of the project.
     * 
     * @param s
     *            string to check
     */
    private static void validateChars(String s) {
        for (char c : s.toCharArray()) {
            if (!Character.isAlphabetic(c)) {
                throw new IllegalArgumentException("invalid value: " + s);
            }
        }
    }

    private static int numberOfFails;

    private static void addFail(boolean pass) {
        if (!pass) {
            numberOfFails++;
        }
    }

    /**
     * Test method.
     */
    private static void testValidateSymbol(String name, String symbol, boolean expected) {
        boolean f = validateElementSymbol(name, symbol);
        System.out.printf("%s: validateSymbol(%s, %s) -> %b%n", (f == expected ? "PASS" : "FAIL"), name, symbol, f);
    }

    /**
     * Test method.
     */
    private static void testGetAllSymbols(String name, Set<String> expected) {
        Set<String> made = getAllSymbols(name);
        boolean pass = made.equals(expected);
        addFail(pass);
        System.out.printf("%s: getAllSymbols(%s, %s)%n", (pass ? "PASS" : "FAIL"), name, made);
    }

    /**
     * Test method.
     */
    private static void testFirstAndLength(String testName, String expected, int length) {
        String first = getFirstSymbol(testName);
        boolean pass1 = first.equals(expected);
        addFail(pass1);
        System.out.printf("%s: first alphabetical symbol %s = %s%n", (pass1 ? "PASS" : "FAIL"), testName, first);
        if (length > 0) {
            boolean pass2 = getSymbolCount(testName) == length;
            addFail(pass2);
            System.out.printf("%s: number of variants of %s == %d %s%n", (pass2 ? "PASS" : "FAIL"), testName, length, getAllSymbols(testName));
        }
    }

    /**
     * Test method.
     */
    private static void testFirst(String testName, String expected) {
        testFirstAndLength(testName, expected, -1);
    }

    /**
     * Helper to make a set from a list of values.
     * 
     * @param va
     *            values
     * @return built set
     */
    private static Set<String> makeSet(String... va) {
        Set<String> result = new TreeSet<>();
        result.addAll(Arrays.asList(va));
        return result;
    }

    /**
     * Test case. Try a representative sample of inputs.
     */
    public static void main(String[] args) {
        try {
            testValidateSymbol("Zeddemorium", "Zr", true);
            testValidateSymbol("Venkmine", "Kn", true);
            testValidateSymbol("Stantzon", "Zt", false);
            testValidateSymbol("Melintzum", "Mn", true);
            testValidateSymbol("Melintzum", "Nm", true);
            testValidateSymbol("Melintzum", "Nn", false);
            testValidateSymbol("Tullium", "Ty", false);

            testGetAllSymbols("Zed", makeSet("Ze", "Zd", "Ed"));
            testGetAllSymbols("Venk", makeSet("Ve", "Vn", "Vk", "En", "Ek", "Nk"));

            testFirstAndLength("Zu", "Zu", 1);
            testFirstAndLength("Zuu", "Uu", 2);
            testFirstAndLength("Zuulon", "Ln", 11);
            testFirst("Gozerium", "Ei");
            testFirst("Slimyrine", "Ie");

            System.out.printf("Number of fails: %d%n", numberOfFails);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

}
