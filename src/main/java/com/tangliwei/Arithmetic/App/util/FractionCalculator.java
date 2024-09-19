package com.tangliwei.Arithmetic.App.util;

public class FractionCalculator {

    public static void main(String[] args) {
        // 定义两个分数
        String fraction1 = "21/6";
        String fraction2 = "1/8";

        // 进行分数加法运算
        String result = addFractions(fraction1, fraction2);

        // 输出加法结果
        System.out.println(fraction1 + " + " + fraction2 + " = " + result);
    }

    // 分数加法运算
    public static String addFractions(String frac1, String frac2) {
        // 解析分数
        int[] fraction1 = parseFraction(frac1);
        int[] fraction2 = parseFraction(frac2);

        // 求两个分母的最小公倍数(LCM)
        int lcm = lcm(fraction1[1], fraction2[1]);

        // 将分子转换为同分母后进行加法
        int numerator1 = fraction1[0] * (lcm / fraction1[1]);
        int numerator2 = fraction2[0] * (lcm / fraction2[1]);

        // 分子相加
        int numeratorSum = numerator1 + numerator2;

        // 计算最大公约数(GCD)，用于化简
        int gcd = gcd(numeratorSum, lcm);

        // 化简后的分子和分母
        int simplifiedNumerator = numeratorSum / gcd;
        int simplifiedDenominator = lcm / gcd;

        // 返回带分数形式的结果
        return convertToMixedFraction(simplifiedNumerator, simplifiedDenominator);
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

    // 求两个数的最小公倍数(LCM)
    public static int lcm(int a, int b) {
        return a * (b / gcd(a, b));
    }

    // 将分数转换为带分数形式
    public static String convertToMixedFraction(int numerator, int denominator) {
        // 如果分子小于分母，直接返回普通分数
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

        // 否则返回带分数形式
        return wholeNumber + "'" + remainder + "/" + denominator;
    }
}
