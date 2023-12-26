package Domain;

import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    private Stack<T> stack;

    public MyStack() {
        stack = new Stack<>();
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public String toString() {
        return printInfixFormat();
    }

    private String printInfixFormat() {
        if (stack.isEmpty()) {
            return "Empty Stack";
        }

        return printInfixFormatHelper(stack.peek().toString());
    }

    private String printInfixFormatHelper(String statement) {
        // Assuming statements are separated by ';'
        String[] statements = statement.split(";");
        if (statements.length == 1) {
            return statements[0];
        }
        StringBuilder result = new StringBuilder();

        for (String s : statements) {
            if (!s.trim().isEmpty()) {
                result.append(s.trim()).append("\n");
            }
        }
        String second_part = result.toString();
        second_part = second_part.substring(1, second_part.length() - 2);
        return second_part;
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
