/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlinequizsystem;

/**
 *
 * @author Karm
 */
public class Question {
        private String question;
    private String[] options;
    private int correctAnswer;
    
    public Question(String question, String[] options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
    
    public String getQuestion() { return question; }
    public String[] getOptions() { return options; }
    public int getCorrectAnswer() { return correctAnswer; }
    
    public boolean isCorrect(int answer) {
        return answer == correctAnswer;
    }}
