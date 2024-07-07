package base.structures;

import java.util.Arrays;
import java.util.Stack;

public class ReversedStringHW {

    public static  void reverseString(char[] s) {
        Stack<Character> reversedSequence = new Stack<>();

        for (char c : s) {
            reversedSequence.push(c);
        }

        for (int i = 0; i < s.length; i++) {
            s[i] = reversedSequence.pop();
        }

        System.out.println(Arrays.toString(s));
    }

    public static void main(String[] args) {
        reverseString(new char[]{'h', 'e', 'l', 'l', 'o'});
        reverseString(new char[]{'H', 'a', 'n', 'n', 'a', 'h'});
    }
}
