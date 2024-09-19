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
        String expression = "1/7 / 1 - 1'2/5 * 1";

        // 将带分数转化为普通分数再计算
        String processedExpression = FractionChange.convertMixedFractionsToImproperFractions(expression);
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


