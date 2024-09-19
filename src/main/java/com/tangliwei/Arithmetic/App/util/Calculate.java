package com.tangliwei.Arithmetic.App.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculate {
    public static void main(String[] args) {
        // 带分数的表达式字符串
        String expression = "(4 + 10/3) / (7/10 / 10/1)";

        // 将带分数转化为普通分数再计算
        String processedExpression = preprocessMixedFractions(expression);
        System.out.println("处理后的表达式: " + processedExpression);

        double result = calculateExpression(processedExpression);

        // 输出结果
        System.out.println("计算结果是: " + (result));
    }

    // 使用 exp4j 来解析和计算表达式
    public static double calculateExpression(String expression) {
        // 创建表达式解析器
        Expression e = new ExpressionBuilder(expression).build();
        // 计算并返回结果
        return e.evaluate();
    }

    // 将带分数 (如 2'1/3) 转化为普通分数
    public static String preprocessMixedFractions(String expression) {
        // 正则表达式匹配带分数的模式: 整数部分'分子/分母
        Pattern pattern = Pattern.compile("(\\d+)'(\\d+)/(\\d+)");
        Matcher matcher = pattern.matcher(expression);

        StringBuffer result = new StringBuffer();

        // 对匹配到的带分数进行处理并替换为普通分数
        while (matcher.find()) {
            int wholeNumber = Integer.parseInt(matcher.group(1));
            int numerator = Integer.parseInt(matcher.group(2));
            int denominator = Integer.parseInt(matcher.group(3));

            // 将带分数转换为普通分数: (wholeNumber * denominator + numerator) / denominator
            int newNumerator = wholeNumber * denominator + numerator;
            String replacement = "(" + newNumerator + "/" + denominator + ")";

            // 将匹配的部分替换为转换后的普通分数
            matcher.appendReplacement(result, replacement);
        }

        // 将剩余的部分添加到结果中
        matcher.appendTail(result);

        return result.toString();
    }
    public static String decimalToFraction(double decimal) {
        if (decimal == 0) {
            return "0";  // 处理 0 的情况
        }

        final double tolerance = 1.0E-6;  // 设置一个允许的误差范围
        final long precision = 1000000;   // 精度设置为 1000000
        long numerator = (long) (decimal * precision);
        long denominator = precision;

        // 化简分数
        long gcd = gcd(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;

        // 检查误差是否在容忍范围内，并返回化简后的分数
        if (Math.abs(decimal - (double) numerator / denominator) < tolerance) {
            return numerator + "/" + denominator;
        }

        // 返回化简后的分数
        return numerator + "/" + denominator;
    }

    // 求最大公约数(GCD)
    public static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
}

//    private static final String[] OPERATORS = {"+", "-", "*", "/"};
//
//    public static void main(String[] args) {
//        String question = "(3 + 5) * 2 - (4 / 2)";
//        int answer = calculateAnswer(question);
//        System.out.println("Answer: " + answer);
//    }
//
//    // 根据生成的算式计算答案，支持括号
//    private static int calculateAnswer(String question) {
//        // 将中缀表达式转换为后缀表达式
//        String[] postfix = infixToPostfix(question);
//        // 计算后缀表达式的结果
//        return evaluatePostfix(postfix);
//    }
//
//    // 将中缀表达式转换为后缀表达式 (逆波兰表达式)
//    private static String[] infixToPostfix(String expression) {
//        Stack<String> operatorStack = new Stack<>();
//        StringBuilder output = new StringBuilder();
//        String[] tokens = expression.split(" ");
//
//        for (String token : tokens) {
//            if (isNumeric(token)) {
//                // 如果是数字，直接输出
//                output.append(token).append(" ");
//            } else if (isOperator(token)) {
//                // 如果是运算符，处理栈中的运算符，确保正确的优先级顺序
//                while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) >= precedence(token)) {
//                    output.append(operatorStack.pop()).append(" ");
//                }
//                operatorStack.push(token);
//            } else if (token.equals("(")) {
//                // 左括号直接入栈
//                operatorStack.push(token);
//            } else if (token.equals(")")) {
//                // 右括号，弹出运算符直到遇到左括号
//                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
//                    output.append(operatorStack.pop()).append(" ");
//                }
//                operatorStack.pop(); // 弹出左括号
//            }
//        }
//
//        // 弹出栈中剩余的运算符
//        while (!operatorStack.isEmpty()) {
//            output.append(operatorStack.pop()).append(" ");
//        }
//
//        return output.toString().split(" ");
//    }
//
//    // 计算后缀表达式的结果
//    private static int evaluatePostfix(String[] postfix) {
//        Stack<Integer> stack = new Stack<>();
//
//        for (String token : postfix) {
//            if (isNumeric(token)) {
//                stack.push(Integer.parseInt(token));
//            } else if (isOperator(token)) {
//                int rightOperand = stack.pop();
//                int leftOperand = stack.pop();
//                stack.push(calculate(leftOperand, rightOperand, token));
//            }
//        }
//
//        return stack.pop();
//    }
//
//    // 判断是否为数字
//    private static boolean isNumeric(String token) {
//        try {
//            Integer.parseInt(token);
//            return true;
//        } catch (NumberFormatException e) {
//            return false;
//        }
//    }
//
//    // 判断是否为运算符
//    private static boolean isOperator(String token) {
//        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
//    }
//
//    // 运算符的优先级
//    private static int precedence(String operator) {
//        switch (operator) {
//            case "+":
//            case "-":
//                return 1;
//            case "*":
//            case "/":
//                return 2;
//            default:
//                return 0;
//        }
//    }
//
//    // 执行四则运算
//    private static int calculate(int leftOperand, int rightOperand, String operator) {
//        switch (operator) {
//            case "+":
//                return leftOperand + rightOperand;
//            case "-":
//                return leftOperand - rightOperand;
//            case "*":
//                return leftOperand * rightOperand;
//            case "/":
//                return leftOperand / rightOperand; // 整除，注意处理真分数时需调整
//            default:
//                throw new IllegalArgumentException("Unsupported operator: " + operator);
//        }
//    }

