package com.sece.school.entity;
import java.sql.*;
import java.util.Scanner;

public class Question {
    private int questionId;
    private int examId;
    private String questionText;
    private String questionType;
    private int marks;

    public Question() {

    }

    public Question(int examId, String questionText, String questionType, int marks) {
        this.examId = examId;
        this.questionText = questionText;
        this.questionType = questionType;
        this.marks = marks;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public void addQuestion(int examId, String questionType, String questionText, Scanner sc, Connection connect, int marks) {
        String queryExamName = "SELECT exam_name FROM Exams WHERE exam_id = ?";
        String queryAddQuestion = "INSERT INTO Questions (exam_id, question_type, question_text, marks) VALUES (?, ?, ?, ?)";

        try {

            PreparedStatement psExam = connect.prepareStatement(queryExamName);
            psExam.setInt(1, examId);
            ResultSet rsExam = psExam.executeQuery();

            if (rsExam.next()) {
                String examName = rsExam.getString("exam_name");
                System.out.println("Exam Name: " + examName);
            } else {
                System.out.println("No exam found with the provided exam ID.");
                return;
            }

            PreparedStatement ps = connect.prepareStatement(queryAddQuestion);
            ps.setInt(1, examId);
            ps.setString(2, questionType);
            ps.setString(3, questionText);
            ps.setInt(4, marks);

            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Question added successfully!");
            } else {
                System.out.println("Failed to add question.");
            }

        } catch (SQLException e) {
            System.out.println("Error while adding the question: " + e.getMessage());
        }
    }
}

