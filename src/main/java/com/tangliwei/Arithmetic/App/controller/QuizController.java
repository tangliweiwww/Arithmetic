package com.tangliwei.Arithmetic.App.controller;

import com.tangliwei.Arithmetic.App.bean.QuizRequest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class QuizController {

    @PostMapping("/generateQuiz")
    public ResponseEntity<Resource> generateQuiz(@RequestBody QuizRequest quizRequest) throws IOException {
        // 生成题目和答案的逻辑 (略)
        // 假设生成文件路径如下:
        String exercisesFilePath = "/path/to/Exercises.txt";
        String answersFilePath = "/path/to/Answers.txt"; // 保存答案到服务器

        // 将生成的题目文件返回给前端
        Path path = Paths.get(exercisesFilePath);
        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Exercises.txt\"")
                .body(resource);
    }

    @PostMapping("/judgeQuiz")
    public ResponseEntity<Resource> judgeQuiz(
            @RequestParam("exerciseFile") MultipartFile exerciseFile,
            @RequestParam("answerFile") MultipartFile answerFile) throws IOException {

        // 判题逻辑 (略)
        // 假设判题结果生成到如下文件:
        String gradeFilePath = "/path/to/Grade.txt";

        // 返回判题结果文件
        Path path = Paths.get(gradeFilePath);
        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Grade.txt\"")
                .body(resource);
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello";
    }
}

