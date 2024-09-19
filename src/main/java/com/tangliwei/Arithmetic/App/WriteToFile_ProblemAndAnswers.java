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
    String Problem_filePath = "Exercises.txt";
    String Answer_filePath = "Answers.txt";


    public WriteToFile_ProblemAndAnswers(int numberOfProblems, int range) {
        this.numberOfProblems = numberOfProblems;
        this.range = range;
        //获取题目
        Set<String> problems = MathProblemGenerator.generateUniqueProblems(numberOfProblems, range);

        //写题目
        int i =1;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Problem_filePath,false))){
            for (String problem : problems) {
                writer.write(i+". "+problem+"\n");
                i++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //写答案
        int i1 = 1;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Answer_filePath,false))){
            for (String problem : problems) {
                String ChangedProblem = FractionChange.convertMixedFractionsToImproperFractions(problem);//将带分数转化为普通分数
                BigFraction answer = FractionCalculator.evaluateFractionExpression(ChangedProblem);
                if(answer==null){
                    writer.write(i1+"."+"worng"+"\n");
                    i++;
                }else {
                    String ChangedAnswer = FractionChange.convertExpressionToMixedFractions(answer.toString().replaceAll(" ",""));
                    writer.write(i1+"."+ChangedAnswer+"\n");
                    i1++;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new WriteToFile_ProblemAndAnswers(1000,10);
    }

}
