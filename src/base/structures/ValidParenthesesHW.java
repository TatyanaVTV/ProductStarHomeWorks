package base.structures;

import java.util.Stack;

public class ValidParenthesesHW {

    public static boolean isValid(String s) {
        Stack<Character> symbolsFromString = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            symbolsFromString.push(s.charAt(i));
        }

        boolean noErrors = true;
        int checkSum = 0;
        while (noErrors && !symbolsFromString.isEmpty()) {
            switch (symbolsFromString.pop()) {
                case '(':
                    checkSum += 1;
                    break;
                case ')':
                    checkSum -= 1;
                    break;
                case '[':
                    checkSum += 10;
                    break;
                case ']':
                    checkSum -= 10;
                    break;
                case '{':
                    checkSum += 100;
                    break;
                case '}':
                    checkSum -= 100;
                default:
                    break;
            }
            noErrors = (symbolsFromString.size() % 2 == 0 && checkSum == 0) || (symbolsFromString.size() % 2 != 0 && checkSum < 0);
        }

        return checkSum == 0;
    }

    public static void main(String[] args) {
        System.out.println(isValid("()") == true);
        System.out.println(isValid("()[]{}") == true);
        System.out.println(isValid("(]") == false);
        System.out.println(isValid("([)]") == false);
    }
}
