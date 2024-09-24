package com.tangliwei.Arithmetic.App.util;

import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.fraction.BigFraction;
import java.util.*;

public class FractionCalculator {

    public static void main(String[] args) {
        String expression = "(2 *1'1/3 * 10 * 6 )";
        try {
            BigFraction result = evaluateFractionExpression(expression);
            System.out.println("结果是: " + result);
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }
    }

    public static BigFraction evaluateFractionExpression(String expression) throws IllegalArgumentException {
        Stack<BigFraction> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        StringTokenizer tokenizer = new StringTokenizer(expression, "()+-*/", true);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();

            if (token.isEmpty()) continue;

            if (token.equals("(")) {
                operators.push('(');
            } else if (token.equals(")")) {
                while (operators.peek() != '(') {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop(); // pop the '('
            } else if (isOperator(token.charAt(0))) {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(token.charAt(0))) {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(token.charAt(0));
            } else {
                values.push(parseFraction(token));
            }
        }

        while (!operators.isEmpty()) {
            values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    private static BigFraction parseFraction(String token) {
        if (token.contains("/")) {
            String[] parts = token.split("/");
            return new BigFraction(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        } else {
            return new BigFraction(Integer.parseInt(token), 1);
        }
    }

    private static boolean isOperator(char c) {
        return c == '*' || c == '/' || c == '+' || c == '-';
    }

    private static int precedence(char operator) {
        if (operator == '*' || operator == '/') {
            return 2;
        } else if (operator == '+' || operator == '-') {
            return 1;
        }
        return -1;
    }

    private static BigFraction applyOperator(char operator, BigFraction b, BigFraction a) {
        try {
            switch (operator) {
                case '+': return a.add(b);
                case '-': return a.subtract(b);
                case '*': return a.multiply(b);
                case '/':if (b.equals(BigFraction.ZERO)) {
                    System.err.println("Division by zero is not allowed.");
                    return null;
                }
                    return a.divide(b);
                default: throw new IllegalArgumentException("Invalid operator: " + operator);
            }
        }catch (NullArgumentException e){
            System.err.println(e.getMessage());
            return null;
        }

    }
}
