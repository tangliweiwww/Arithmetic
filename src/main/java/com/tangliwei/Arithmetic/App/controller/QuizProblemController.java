package com.tangliwei.Arithmetic.App.controller;

import com.tangliwei.Arithmetic.App.WriteToFile_ProblemAndAnswers;
import com.tangliwei.Arithmetic.App.bean.QuizRequest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
public class QuizProblemController {

    @PostMapping("/generateQuiz")
    public ResponseEntity<Resource> generateQuiz(@RequestBody QuizRequest quizRequest) throws IOException {
        String currentDir = System.getProperty("user.dir");
        String exercisesFilePath = currentDir + "/Exercises.txt";
        String answersFilePath = currentDir + "/Answers.txt";
        String zipFilePath = currentDir + "/QuizFiles.zip";

        try {
            // 生成题目和答案文件
            WriteToFile_ProblemAndAnswers writeToFileProblemAndAnswers = new WriteToFile_ProblemAndAnswers(
                    quizRequest.getNumQuestions(), quizRequest.getRange(), exercisesFilePath, answersFilePath);
            writeToFileProblemAndAnswers.Write();

            // 创建 ZIP 文件并将文件打包
            try (FileOutputStream fos = new FileOutputStream(zipFilePath);
                 ZipOutputStream zos = new ZipOutputStream(fos)) {

                addToZipFile(exercisesFilePath, zos);
                addToZipFile(answersFilePath, zos);
            }

            // 返回 ZIP 文件
            Path zipPath = Paths.get(zipFilePath);
            if (Files.exists(zipPath) && Files.isReadable(zipPath)) {
                Resource resource = new UrlResource(zipPath.toUri());

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"QuizFiles.zip\"")
                        .header(HttpHeaders.CONTENT_TYPE, "application/zip")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private void addToZipFile(String filePath, ZipOutputStream zos) throws IOException {
        File file = new File(filePath);

        if (!file.exists() || !file.isFile()) {
            System.err.println("File not found: " + filePath);
            return;
        }

        System.out.println("Adding file to ZIP: " + filePath);

        try (FileInputStream fis = new FileInputStream(file)) {
            ZipEntry zipEntry = new ZipEntry(file.getName());
            zos.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }

            zos.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to add file to ZIP: " + filePath);
        }
    }
}

