package com.tangliwei.Arithmetic.App.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FractionChange {

    public static void main(String[] args) {
//        // 定义一个包含分数的算式
//        String expression = "21/6 + 1/8";
//
//        // 处理并将分式转换为带分数的算式
//        String result = convertExpressionToMixedFractions(expression);
//
//        // 输出转换后的结果
//        System.out.println("Original expression: " + expression);
//        System.out.println("Converted expression: " + result);

        // 示例带分数转换为普通分数
        String mixedFraction = "73/20";
        String improperFraction = convertExpressionToMixedFractions(mixedFraction);
        System.out.println("Mixed fraction: " + mixedFraction);
        System.out.println("Improper fraction: " + improperFraction);
    }

    // 将算式中的分式转换为带分数
    public static String convertExpressionToMixedFractions(String expression) {
        // 正则表达式匹配分数
        Pattern fractionPattern = Pattern.compile("(\\d+/\\d+)");
        Matcher matcher = fractionPattern.matcher(expression);
        StringBuffer result = new StringBuffer();

        // 查找并转换分数
        while (matcher.find()) {
            String fraction = matcher.group(1);
            int[] parsedFraction = parseFraction(fraction);
            String mixedFraction = convertToMixedFraction(parsedFraction[0], parsedFraction[1]);
            matcher.appendReplacement(result, mixedFraction);
        }
        matcher.appendTail(result);

        return result.toString();
    }



    // 将字符串形式的分数解析为整数数组，返回[分子, 分母]
    public static int[] parseFraction(String fraction) {
        String[] parts = fraction.split("/");
        int numerator = Integer.parseInt(parts[0]);
        int denominator = Integer.parseInt(parts[1]);
        return new int[] {numerator, denominator};
    }

    // 求最大公约数(GCD)
    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    // 将分数转换为带分数形式并化简
    public static String convertToMixedFraction(int numerator, int denominator) {
        // 化简分数部分
        int gcd = gcd(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;

        // 如果分子小于分母，直接返回简化后的普通分数
        if (numerator < denominator) {
            return numerator + "/" + denominator;
        }

        // 计算整数部分和余数
        int wholeNumber = numerator / denominator;
        int remainder = numerator % denominator;

        // 如果没有余数，返回整数
        if (remainder == 0) {
            return Integer.toString(wholeNumber);
        }

        // 否则返回带分数形式（化简后的结果）
        return wholeNumber + "'" + remainder + "/" + denominator;
    }

    // 将带分数转换为普通分数
    // 将带分数转换为普通分数
    public static String convertMixedFractionsToImproperFractions(String expression) {
        // 正则表达式匹配带分数
        Pattern mixedFractionPattern = Pattern.compile("(\\d+)'(\\d+)/(\\d+)");
        Matcher matcher = mixedFractionPattern.matcher(expression);
        StringBuffer result = new StringBuffer();

        // 查找并转换带分数
        while (matcher.find()) {
            String mixedFraction = matcher.group();
            String improperFraction = convertMixedFractionToImproperFraction(mixedFraction);
            System.out.println("Converting: " + mixedFraction + " to " + improperFraction); // Debug statement
            matcher.appendReplacement(result, improperFraction);
        }
        matcher.appendTail(result);

        return result.toString();
    }

    // 将带分数形式转换为普通分数
    public static String convertMixedFractionToImproperFraction(String mixedFraction) {
        // 正则表达式匹配带分数
        Pattern mixedFractionPattern = Pattern.compile("(\\d+)'(\\d+)/(\\d+)");
        Matcher matcher = mixedFractionPattern.matcher(mixedFraction);

        if (matcher.matches()) {
            int wholeNumber = Integer.parseInt(matcher.group(1));
            int numerator = Integer.parseInt(matcher.group(2));
            int denominator = Integer.parseInt(matcher.group(3));

            // 将带分数转换为普通分数
            int improperNumerator = wholeNumber * denominator + numerator;
            return improperNumerator + "/" + denominator;
        } else {
            // 处理未匹配带分数形式的情况（可以是普通分数形式）
            return mixedFraction; // 返回原分数形式
        }
    }
}
