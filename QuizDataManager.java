/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlinequizsystem;
import java.io.*;
import java.util.*;

/**
 *
 * @author Karm
 */
public class QuizDataManager {
     private static final String QUESTIONS_FILE = "questions.txt";
     public static List<Question> loadQuestions() {
        List<Question> questions = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(QUESTIONS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String question = line.trim();
                String[] options = new String[4];
                
                for (int i = 0; i < 4; i++) {
                    options[i] = reader.readLine().trim();
                }
                int correctAnswer = Integer.parseInt(reader.readLine().trim());
                questions.add(new Question(question, options, correctAnswer));
            }
        } catch (IOException e) {
            System.err.println("Error loading questions: " + e.getMessage());
            questions = createSampleQuestions();
        }
       return questions;
    }
    private static List<Question> createSampleQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question(
            "What is the capital of France?",
            new String[]{"London", "Berlin", "Paris", "Madrid"},
            2
        ));
        questions.add(new Question(
            "Which programming language is known as the 'write once, run anywhere' language?",
            new String[]{"Python", "C++", "Java", "JavaScript"},
            2
        ));
        questions.add(new Question(
            "What is 2 + 2?",
            new String[]{"3", "4", "5", "6"},
            1
        ));
        
        questions.add(new Question(
            "Which of these is not an OOP concept?",
            new String[]{"Inheritance", "Polymorphism", "Compilation", "Encapsulation"},
            2
        ));
        questions.add(new Question(
            "What does HTML stand for?",
            new String[]{
                "Hyper Text Markup Language",
                "High Tech Modern Language", 
                "Hyper Transfer Markup Language",
                "Home Tool Markup Language"
            },
            0
        ));
        return questions;
    }
    public static void createSampleFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(QUESTIONS_FILE))) {
            String[] sampleQuestions = {
                "What is the capital of France?",
                "London",
                "Berlin", 
                "Paris",
                "Madrid",
                "2",
                "",
                "Which programming language is known as the 'write once, run anywhere' language?",
                "Python",
                "C++",
                "Java",
                "JavaScript", 
                "2",
                "",
                "What is 2 + 2?",
                "3",
                "4",
                "5",
                "6",
                "1",
                "",
                "Which of these is not an OOP concept?",
                "Inheritance",
                "Polymorphism",
                "Compilation",
                "Encapsulation",
                "2",
                "",
                "What does HTML stand for?",
                "Hyper Text Markup Language",
                "High Tech Modern Language",
                "Hyper Transfer Markup Language", 
                "Home Tool Markup Language",
                "0"
            };
          for (String line : sampleQuestions) {
                writer.println(line);
            }
            System.out.println("Sample questions file created: " + QUESTIONS_FILE);
        } catch (IOException e) {
            System.err.println("Error creating sample file: " + e.getMessage());
        }
      }
   }
