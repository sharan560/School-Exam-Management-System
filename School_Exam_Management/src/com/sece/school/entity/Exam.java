package com.sece.school.entity;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Exam {
    private int examId;
    private  String examName;
    private String Subject;
    private LocalDate date ;
    private int teacherId;
    private int duration;

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }
    public Exam(){

    }

    public Exam(String examName, String subject, LocalDate date, int teacherId, int duration) {
        this.examName = examName;
        this.Subject = subject;
        this.date = date;
        this.teacherId = teacherId;
        this.duration = duration;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "examId=" + examId +
                ", examName='" + examName + '\'' +
                ", Subject='" + Subject + '\'' +
                ", date=" + date +
                ", teacherId='" + teacherId + '\'' +
                ", duration=" + duration +
                '}';
    }
    public void Create_Exam(Scanner sc, Connection connect, int teacherId) {
        System.out.println("Enter Exam Details:");


        System.out.print("Name of Exam: ");
        String name = sc.nextLine();

        System.out.print("Subject of Exam: ");
        String subject = sc.nextLine();

        System.out.print("Duration of Exam (in minutes): ");
        int duration = sc.nextInt();
        sc.nextLine();

        LocalDate examDate = LocalDate.now();


        Exam exam = new Exam(name, subject, examDate, teacherId, duration);
        System.out.println("The entered exam details are: " + exam);


        String query = "INSERT INTO exams (exam_name, subject, exam_date, teacher_id, duration) VALUES (?, ?, ?, ?, ?)";
        try {

            PreparedStatement ps = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, exam.getExamName());
            ps.setString(2, exam.getSubject());
            ps.setDate(3, Date.valueOf(exam.getDate()));
            ps.setInt(4, teacherId);
            ps.setInt(5, exam.getDuration());


            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Exam created successfully!");
            }
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int generatedExamId = rs.getInt(1);
                exam.setExamId(generatedExamId);
                System.out.println("ExamID is " + generatedExamId);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
