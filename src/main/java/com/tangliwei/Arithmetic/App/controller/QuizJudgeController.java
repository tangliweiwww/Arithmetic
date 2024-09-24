package com.tangliwei.Arithmetic.App.controller;

import com.tangliwei.Arithmetic.App.Judge;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class QuizJudgeController {

    @PostMapping("/judgeQuiz")
    public ResponseEntity<Resource> judgeQuiz(@RequestParam("exerciseFile") MultipartFile exerciseFile,
                                              @RequestParam("answerFile") MultipartFile answerFile) {
        // 处理上传的文件
        try {
            // 读取和处理文件的逻辑（例如判题）
            String currentDir = System.getProperty("user.dir");
            String resultFilePath = currentDir + "/Grade.txt";
            // 假设你有一个方法来处理题目和答案并生成结果
            Judge.JudgeFile(exerciseFile, answerFile, resultFilePath);

            // 将结果文件返回给前端
            Path path = Paths.get(resultFilePath);
            Resource resource = new UrlResource(path.toUri());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Grade.txt\"")
                    .header(HttpHeaders.CONTENT_TYPE, "text/plain")
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private void processFiles(MultipartFile exerciseFile, MultipartFile answerFile, String resultFilePath) {
        // 处理判题逻辑，并将结果写入 resultFilePath 文件中
        // ...
    }
}

