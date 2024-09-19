package com.tangliwei.Arithmetic.App.bean;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class QuizRequest {
    private int numQuestions;
    private int range;
}
