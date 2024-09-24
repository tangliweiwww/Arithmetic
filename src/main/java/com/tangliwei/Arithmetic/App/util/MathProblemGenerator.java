package com.tangliwei.Arithmetic.App.util;


import org.apache.commons.math3.fraction.BigFraction;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.*;

public class MathProblemGenerator {

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        // 示例设置
        int numberOfProblems = 10; // 生成的题目个数
        int range = 10;             // 数值范围

        // 生成题目
        Set<String> problems = generateUniqueProblems(numberOfProblems, range);

        // 打印题目
        for (String problem : problems) {
            System.out.println(problem);
        }
    }

    // 生成唯一题目集合
    public static Set<String> generateUniqueProblems(int numberOfProblems, int range) {
        Set<String> problems = new LinkedHashSet<>();
        while (problems.size() < numberOfProblems) {
            String problem = generateRandomProblem(range);
            if (isValidExpression(problem)) {
                problems.add(FractionChange.convertExpressionToMixedFractions(problem));
            }
        }
        return problems;
    }

    // 生成一个随机题目
    private static String generateRandomProblem(int range) {
        String[] operators = {"+", "-", "*", "/"};
        StringBuilder problem = new StringBuilder();
        Stack<Boolean> parenthesesStack = new Stack<>();

        // 生成操作数和运算符
        String operand1 = generateOperand(range);
        String operand2 = generateOperand(range);
        String operator = operators[RANDOM.nextInt(operators.length)];

        // 确保减法时前面的数大于等于后面的数
        if ("-".equals(operator)) {
            while (evaluateOperand(operand1) < evaluateOperand(operand2)) {
                operand1 = generateOperand(range);
                operand2 = generateOperand(range);
            }
        }

        // 随机决定是否生成括号
        if (RANDOM.nextBoolean()) {
            problem.append("(");
            parenthesesStack.push(true);  // 打开括号
        }

        problem.append(operand1)
                .append(" ")
                .append(operator)
                .append(" ")
                .append(operand2);

        // 随机决定是否生成第二部分的表达式
        if (RANDOM.nextBoolean()) {
            operator = operators[RANDOM.nextInt(operators.length)];
            String operand3 = generateOperand(range);
            String operand4 = generateOperand(range);

            // 确保减法时前面的数大于等于后面的数
            if ("-".equals(operator)) {
                while (evaluateOperand(operand3) < evaluateOperand(operand4)) {
                    operand3 = generateOperand(range);
                    operand4 = generateOperand(range);
                }
            }

            problem.append(" ")
                    .append(operator)
                    .append(" ");

            // 随机决定是否给第二部分加括号
            if (RANDOM.nextBoolean()) {
                problem.append("(");
                parenthesesStack.push(true);  // 打开括号
            }

            problem.append(operand3)
                    .append(" ")
                    .append(operators[RANDOM.nextInt(operators.length)])
                    .append(" ")
                    .append(operand4);

            // 关闭括号
            while (!parenthesesStack.isEmpty()) {
                problem.append(")");
                parenthesesStack.pop();
            }
        }

        // 如果还有未关闭的括号，最后关闭它
        while (!parenthesesStack.isEmpty()) {
            problem.append(")");
            parenthesesStack.pop();
        }

        return problem.toString();
    }

    // 生成操作数
    private static String generateOperand(int range) {
        if (RANDOM.nextBoolean()) {
            // 生成整数
            return String.valueOf(RANDOM.nextInt(range) + 1);
        } else {
            // 生成分数
            int numerator = RANDOM.nextInt(range) + 1;
            int denominator = RANDOM.nextInt(range) + 1;
            return numerator + "/" + denominator;
        }
    }

    // 计算操作数的值
    private static double evaluateOperand(String operand) {
        if (operand.contains("/")) {
            String[] parts = operand.split("/");
            return Double.parseDouble(parts[0]) / Double.parseDouble(parts[1]);
        } else {
            return Double.parseDouble(operand);
        }
    }

    // 检查计算结果是否符合要求
    private static boolean isValidExpression(String expression) {
        // 创建表达式解析器
        Expression e = new ExpressionBuilder(expression).build();
        // 计算并返回结果
        double result = e.evaluate();
        // 将结果转换为BigFraction
        if (result < 0){return false;}
        return true;
    }
}
