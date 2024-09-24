package com.tangliwei.Arithmetic.App;

import com.beust.ah.A;
import com.tangliwei.Arithmetic.App.util.FractionCalculator;
import com.tangliwei.Arithmetic.App.util.FractionChange;
import com.tangliwei.Arithmetic.App.util.MathProblemGenerator;
import org.apache.commons.math3.fraction.BigFraction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class WriteToFile_ProblemAndAnswers {

    private int numberOfProblems;//题目个数
    private int range;//数值范围
    String problem_filePath ;
    String answer_filePath ;


    public WriteToFile_ProblemAndAnswers(int numberOfProblems, int range,String problem_filePath,String answer_filePath) {
        this.numberOfProblems = numberOfProblems;
        this.range = range;
        this.answer_filePath = answer_filePath;
        this.problem_filePath = problem_filePath;
    }

    public  void Write(){
        //获取题目
        Set<String> problems = MathProblemGenerator.generateUniqueProblems(numberOfProblems, range);
        //写题目
        int i = 1;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(problem_filePath, false))) {
            for (String problem : problems) {
                writer.write(i + ". " + problem + "\n");
                i++;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //写答案
        int i1 = 1;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(answer_filePath, false))) {
            for (String problem : problems) {
                String ChangedProblem = FractionChange.convertMixedFractionsToImproperFractions(problem);//将带分数转化为普通分数
                BigFraction answer = FractionCalculator.evaluateFractionExpression(ChangedProblem);
                if (answer == null) {
                    writer.write(i1 + "." + "worng" + "\n");
                    i1++;
                } else {
                    String ChangedAnswer = FractionChange.convertExpressionToMixedFractions(answer.toString().replaceAll(" ", ""));
                    writer.write(i1 + "." + ChangedAnswer + "\n");
                    i1++;
                }
            }
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

