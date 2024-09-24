package com.tangliwei.Arithmetic.App;

import com.tangliwei.Arithmetic.App.util.FractionCalculator;
import com.tangliwei.Arithmetic.App.util.FractionChange;
import org.apache.commons.math3.fraction.BigFraction;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

public class Judge {
    public static void main(String[] args) {
        // 创建StringBuilder对象并添加内容
        StringBuilder sb = new StringBuilder();
        sb.append("Hello, ");
        sb.append("World!");

        // 指定要写入的文件路径
        String filePath = "output.txt";

        try (FileWriter fileWriter = new FileWriter(filePath);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            // 使用write方法将StringBuilder的内容写入文件
            writer.write(sb.toString());
//            writer.newLine(); // 添加新行
            System.out.println("内容已写入文件：" + filePath);
        } catch (IOException e) {
            System.err.println("写入文件时发生错误：" + e.getMessage());
            e.printStackTrace();
        }
    }



    public static void JudgeFile(MultipartFile exerciseFile, MultipartFile answerFile, String GradeFile){

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(GradeFile))) {
            BufferedReader ExReader = new BufferedReader(new InputStreamReader(exerciseFile.getInputStream()));
            BufferedReader AnReader = new BufferedReader(new InputStreamReader(answerFile.getInputStream()));
            Map<String,String> map1 = new HashMap<>();
            Map<String,String> map2 = new HashMap<>();
            String line1;
            String line2;
            //处理问题
            while ((line1 = ExReader.readLine()) != null) {
                // 处理每一行数据
                String problem = line1.split("\\.")[1];
                String ChangedProblem = FractionChange.convertMixedFractionsToImproperFractions(problem);//将带分数转化为普通分数
                BigFraction answer = FractionCalculator.evaluateFractionExpression(ChangedProblem);
                String ChangedAnswer = null;
                if(answer!=null){
                    ChangedAnswer = FractionChange.convertExpressionToMixedFractions(answer.toString().replaceAll(" ", ""));
                }else {
                    ChangedAnswer = "wrong";
                }
                map1.put(line1.split("\\.")[0],ChangedAnswer);
            }
            //处理答案
            while ((line2 = AnReader.readLine())!=null) {
                String answer = line2.split("\\.")[1];
                map2.put(line2.split("\\.")[0],answer);
            }

            List<Integer> Correct = new ArrayList<>();
            List<Integer> Wrong = new ArrayList<>();
            for (int i=1;i< map1.size();i++){
                if(!map1.get(String.valueOf(i)).equals(map2.get(String.valueOf(i)))){
                    Wrong.add(i);
                }else {
                    Correct.add(i);
                }
            }

            StringBuilder CorrectBuilder = new StringBuilder();
            StringBuilder WrongBuilder = new StringBuilder();

            CorrectBuilder.append("Correct:").append(Correct.size()).append("(");
            WrongBuilder.append("Wrong:").append(Wrong.size()).append("(");
            for (Integer i : Correct) {
                CorrectBuilder.append(i).append(",");
            }
            for (Integer i : Wrong) {
                WrongBuilder.append(i).append(",");
            }
            CorrectBuilder.deleteCharAt(CorrectBuilder.length()-1).append(")");
            WrongBuilder.deleteCharAt(WrongBuilder.length()-1).append(")");


            writer.write(CorrectBuilder.toString());
            writer.write("\n");
            writer.write(WrongBuilder.toString());


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}
