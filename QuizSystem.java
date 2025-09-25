/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.onlinequizsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class QuizSystem extends JFrame {
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int[] userAnswers;
    private Timer timer;
    private int timeLeft = 30;
    
    private JLabel questionLabel, timerLabel, questionNumberLabel;
    private JRadioButton[] optionButtons;
    private ButtonGroup optionGroup;
    private JButton prevButton, nextButton, submitButton;
    private JPanel mainPanel;
    
    public QuizSystem() {
        initializeQuiz();
        setupGUI();
        loadQuestion(0);
        startTimer();
    }
    
    private void initializeQuiz() {
        QuizDataManager.createSampleFile();
        questions = QuizDataManager.loadQuestions();
        userAnswers = new int[questions.size()];
        
        for (int i = 0; i < userAnswers.length; i++) {
            userAnswers[i] = -1;
        }
    }
    
    private void setupGUI() {
        setTitle("Online Quiz System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
    
        JPanel headerPanel = new JPanel(new BorderLayout());
        questionNumberLabel = new JLabel("Question 1 of " + questions.size());
        questionNumberLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        timerLabel = new JLabel("Time Left: 30s");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        timerLabel.setForeground(Color.RED);
        timerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        headerPanel.add(questionNumberLabel, BorderLayout.WEST);
        headerPanel.add(timerLabel, BorderLayout.EAST);
        
     
        JPanel questionPanel = new JPanel(new BorderLayout());
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        questionLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        questionPanel.add(questionLabel, BorderLayout.NORTH);
        
     
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        optionButtons = new JRadioButton[4];
        optionGroup = new ButtonGroup();
        
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setFont(new Font("Arial", Font.PLAIN, 14));
            optionGroup.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
            
            final int optionIndex = i;
            optionButtons[i].addActionListener(e -> {
                userAnswers[currentQuestionIndex] = optionIndex;
            });
        }
        
        questionPanel.add(optionsPanel, BorderLayout.CENTER);
        
   
        JPanel buttonPanel = new JPanel(new FlowLayout());
        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");
        submitButton = new JButton("Submit Quiz");
        
        prevButton.addActionListener(e -> showPreviousQuestion());
        nextButton.addActionListener(e -> showNextQuestion());
        submitButton.addActionListener(e -> submitQuiz());
        
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(submitButton);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(questionPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        updateNavigationButtons();
    }
    
    private void loadQuestion(int index) {
        if (index < 0 || index >= questions.size()) return;
        
        currentQuestionIndex = index;
        Question question = questions.get(index);
        
        questionLabel.setText("<html><body style='width: 500px'>" + 
                            (index + 1) + ". " + question.getQuestion() + "</body></html>");
        
        String[] options = question.getOptions();
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText((char)('A' + i) + ". " + options[i]);
        }
        
        optionGroup.clearSelection();
        if (userAnswers[index] != -1) {
            optionButtons[userAnswers[index]].setSelected(true);
        }
        
        questionNumberLabel.setText("Question " + (index + 1) + " of " + questions.size());
        resetTimer();
        updateNavigationButtons();
    }
    
    private void showNextQuestion() {
        saveCurrentAnswer();
        if (currentQuestionIndex < questions.size() - 1) {
            loadQuestion(currentQuestionIndex + 1);
        }
    }
    
    private void showPreviousQuestion() {
        saveCurrentAnswer();
        if (currentQuestionIndex > 0) {
            loadQuestion(currentQuestionIndex - 1);
        }
    }
    
    private void saveCurrentAnswer() {
        for (int i = 0; i < 4; i++) {
            if (optionButtons[i].isSelected()) {
                userAnswers[currentQuestionIndex] = i;
                return;
            }
        }
    }
    
    private void updateNavigationButtons() {
        prevButton.setEnabled(currentQuestionIndex > 0);
        nextButton.setEnabled(currentQuestionIndex < questions.size() - 1);
    }
    
    private void startTimer() {
   
    if (timer == null) {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                
         
                timerLabel.setText("Time Left: " + timeLeft + "s");
                
   
                if (timeLeft <= 10) {
                    timerLabel.setForeground(Color.RED);
                } else {
                    timerLabel.setForeground(Color.BLACK);
                }
                
      
                if (timeLeft <= 0) {
                    timer.stop();
                    autoNextQuestion();
                }
            }
        });
    }
    

    timer.start();
}

private void resetTimer() {

    if (timer != null) {
        timer.stop();
    }

    timeLeft = 30;
    

    timerLabel.setText("Time Left: " + timeLeft + "s");
    timerLabel.setForeground(Color.BLACK);
    

    startTimer();
}


    private void autoNextQuestion() {
        saveCurrentAnswer();
        if (currentQuestionIndex < questions.size() - 1) {
            loadQuestion(currentQuestionIndex + 1);
        } else {
            submitQuiz();
        }
    }
    
    private void submitQuiz() {
        System.out.println("=== SUBMIT QUIZ STARTED ===");
    
    if (timer != null) {
        timer.stop();
        System.out.println("Timer stopped");
    }
    
    saveCurrentAnswer();
    System.out.println("Current answer saved");
    
    score = 0;
    for (int i = 0; i < questions.size(); i++) {
        if (userAnswers[i] != -1 && questions.get(i).isCorrect(userAnswers[i])) {
            score++;
        }
    }
    System.out.println("Score: " + score + "/" + questions.size());
    
    showResults();
    System.out.println("showResults() called");
    }
    
    private void showResults() {
    
    if (timer != null) {
        timer.stop();
    }
    
    score = 0;
    for (int i = 0; i < questions.size(); i++) {
        if (userAnswers[i] != -1 && questions.get(i).isCorrect(userAnswers[i])) {
            score++;
        }
    }
    
    JDialog resultsDialog = new JDialog(this, "Quiz Results", true);
    resultsDialog.setSize(600, 500);
    resultsDialog.setLocationRelativeTo(this);
    resultsDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    
    JPanel resultsPanel = new JPanel(new BorderLayout(10, 10));
    resultsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
    JLabel titleLabel = new JLabel("Quiz Completed!", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
    titleLabel.setForeground(Color.BLUE);
    
    JLabel scoreLabel = new JLabel("Your Score: " + score + "/" + questions.size(), SwingConstants.CENTER);
    scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
    
    JTextArea resultsArea = new JTextArea();
    resultsArea.setEditable(false);
    resultsArea.setFont(new Font("Arial", Font.PLAIN, 14));
    resultsArea.setBackground(new Color(240, 240, 240));
    
    StringBuilder resultsText = new StringBuilder();
    resultsText.append("Detailed Results:\n");
    resultsText.append("=================\n\n");
    
    for (int i = 0; i < questions.size(); i++) {
        Question q = questions.get(i);
        resultsText.append("Question ").append(i + 1).append(": ").append(q.getQuestion()).append("\n");
        resultsText.append("Your answer: ");
        if (userAnswers[i] == -1) {
            resultsText.append("Not answered");
        } else {
            resultsText.append(q.getOptions()[userAnswers[i]]);
        }
        resultsText.append("\n");
        resultsText.append("Correct answer: ").append(q.getOptions()[q.getCorrectAnswer()]).append("\n");
        
        boolean correct = userAnswers[i] != -1 && q.isCorrect(userAnswers[i]);
        resultsText.append("Status: ").append(correct ? "✓ CORRECT" : "✗ INCORRECT").append("\n");
        resultsText.append("----------------------------------------\n");
    }
    
    resultsArea.setText(resultsText.toString());
    
    JScrollPane scrollPane = new JScrollPane(resultsArea);
    scrollPane.setPreferredSize(new Dimension(550, 300));
    
    JButton closeButton = new JButton("Close Quiz");
    closeButton.setFont(new Font("Arial", Font.BOLD, 14));
    closeButton.setBackground(Color.RED);
    closeButton.setForeground(Color.WHITE);
    closeButton.addActionListener(e -> {
        resultsDialog.dispose();
    });
    
    JButton restartButton = new JButton("Restart Quiz");
    restartButton.setFont(new Font("Arial", Font.BOLD, 14));
    restartButton.setBackground(Color.GREEN);
    restartButton.setForeground(Color.BLACK);
    restartButton.addActionListener(e -> {
        resultsDialog.dispose();
        restartQuiz();
    });
    
    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(restartButton);
    buttonPanel.add(closeButton);
    
    JPanel headerPanel = new JPanel(new BorderLayout());
    headerPanel.add(titleLabel, BorderLayout.NORTH);
    headerPanel.add(scoreLabel, BorderLayout.SOUTH);
    
    resultsPanel.add(headerPanel, BorderLayout.NORTH);
    resultsPanel.add(scrollPane, BorderLayout.CENTER);
    resultsPanel.add(buttonPanel, BorderLayout.SOUTH);
    resultsDialog.add(resultsPanel);
    resultsDialog.setVisible(true);
       }
    private void restartQuiz() {
    currentQuestionIndex = 0;
    score = 0;
    userAnswers = new int[questions.size()];
    for (int i = 0; i < userAnswers.length; i++) {
        userAnswers[i] = -1;
    }
    loadQuestion(0);
    resetTimer();
}
  public static void main(String[] args) {
     SwingUtilities.invokeLater(() -> {
        new QuizSystem().setVisible(true);
    });
 }
}