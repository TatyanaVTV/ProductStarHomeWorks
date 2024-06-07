package base.structures;

import java.util.Arrays;
import java.util.Stack;

public class ValidParenthesesAlternativeHW {

    public static boolean isValid(String s) {
        Stack<Character> symbolsFromString = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            symbolsFromString.push(s.charAt(i));
        }

        boolean noErrors = true;
        int checkSum = 0;
        while (noErrors && !symbolsFromString.isEmpty()) {
            checkSum += Bracket.getBracketWeightByValue(symbolsFromString.pop());
            noErrors = (symbolsFromString.size() % 2 == 0 && checkSum == 0) || (symbolsFromString.size() % 2 != 0 && checkSum < 0);
        }

        return checkSum == 0;
    }

    enum Bracket {
        CircleOpen('(', 1), CircleClose(')', -1)
        , SquareOpen('[', 10), SquareClose(']', -10)
        , CurlyOpen('{', 100), CurlyClose('}', -100)
        , NotValidBracket('?', 0);
        private final char value;
        private final int weight;

        Bracket(char value, int weight) {
            this.value = value;
            this.weight = weight;
        }

        public static int getBracketWeightByValue(char valueToCheck) {
            return Arrays.stream(Bracket.values()).filter(bracket -> bracket.value == valueToCheck).findFirst().orElse(NotValidBracket).weight;
        }
    }

    public static void main(String[] args) {
        System.out.println(isValid("()"));
        System.out.println(isValid("()[]{}"));
        System.out.println(isValid("(]"));
        System.out.println(isValid("([)]"));
    }
}
