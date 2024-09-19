package com.tangliwei.Arithmetic.App.util;

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
    private static Set<String> generateUniqueProblems(int numberOfProblems, int range) {
        Set<String> problems = new LinkedHashSet<>();
        while (problems.size() < numberOfProblems) {
            String problem = generateRandomProblem(range);
            if (isValidExpression(problem)) {
                problems.add(problem);
            }
        }
        return problems;
    }

    // 生成一个随机题目
    private static String generateRandomProblem(int range) {
        String[] operators = {"+", "-", "*", "/"};
        StringBuilder problem = new StringBuilder();

        // 生成操作数和运算符
        String operand1 = generateOperand(range);
        String operand2 = generateOperand(range);
        String operator = operators[RANDOM.nextInt(operators.length)];

        // 确保减法时前面的数大于后面的数
        if ("-".equals(operator)) {
            while (evaluateOperand(operand1) < evaluateOperand(operand2)) {
                operand1 = generateOperand(range);
                operand2 = generateOperand(range);
            }
        }

        problem.append("(")
                .append(operand1)
                .append(" ")
                .append(operator)
                .append(" ")
                .append(operand2)
                .append(")");

        if (RANDOM.nextBoolean()) {
            operator = operators[RANDOM.nextInt(operators.length)];
            String operand3 = generateOperand(range);
            String operand4 = generateOperand(range);

            // 确保减法时前面的数大于后面的数
            if ("-".equals(operator)) {
                while (evaluateOperand(operand3) < evaluateOperand(operand4)) {
                    operand3 = generateOperand(range);
                    operand4 = generateOperand(range);
                }
            }

            problem.append(" ")
                    .append(operator)
                    .append(" (")
                    .append(operand3)
                    .append(" ")
                    .append(operators[RANDOM.nextInt(operators.length)])
                    .append(" ")
                    .append(operand4)
                    .append(")");
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
        // 这里可以实现一个表达式求值和验证的逻辑
        // 例如，使用一个简单的数学表达式求值库或自己实现求值算法
        return true;
    }
}
