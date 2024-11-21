package com.sece.school.entity;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Student {
    private int StudentId;
    private String firstName;
    private String SecondName;
    private String Dob;
    private String Email;
    private LocalDate Enrollement_Date;
    private String userName;
    private String Password;

    public  Student()
    {

    }
    public Student(String firstName, String secondName, String dob, String email, LocalDate enrollement_Date, String userName, String password) {
        this.firstName = firstName;
        this.SecondName = secondName;
        this. Dob = dob;
        this.Email = email;
        this.Enrollement_Date = enrollement_Date;
        this.userName = userName;
        this.Password = password;
    }

    public int getStudentId() {
        return StudentId;
    }

    public void setStudentId(int studentId) {
        StudentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return SecondName;
    }

    public void setSecondName(String secondName) {
        SecondName = secondName;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public LocalDate getEnrollement_Date() {
        return Enrollement_Date;
    }

    public void setEnrollement_Date(LocalDate enrollement_Date) {
        Enrollement_Date = enrollement_Date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return "Student{" +
                ", firstName='" + firstName + '\'' +
                ", SecondName='" + SecondName + '\'' +
                ", Dob='" + Dob + '\'' +
                ", Email='" + Email + '\'' +
                ", Enrollement_Date=" + Enrollement_Date +
                ", userName='" + userName + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
    public void viewExam(Scanner sc, Connection connect) {
        String query = "select * from exams";

        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ResultSet rs = ps.executeQuery();


            if (!rs.isBeforeFirst()) {
                System.out.println("No exams found.");
                return;
            }


            System.out.println("Available Exams:");
            while (rs.next()) {

                int examId = rs.getInt("exam_id");
                String examName = rs.getString("exam_name");
                String subject = rs.getString("subject");
                Date examDate = rs.getDate("exam_date");
                int duration = rs.getInt("duration");
                LocalDate formattedDate = examDate.toLocalDate();


                System.out.println("Exam ID: " + examId);
                System.out.println("Exam Name: " + examName);
                System.out.println("Subject: " + subject);
                System.out.println("Exam Date: " + formattedDate);
                System.out.println("Duration: " + duration + " minutes");
                System.out.println("--------------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching exam details: " + e.getMessage());
        }
    }

    public void viewGrade(Scanner sc, Connection connect, int studentId) {

        String query = "SELECT g.exam_id, g.grade, g.marks_obtained, s.first_name, s.last_name " +
                "FROM grades g " +
                "JOIN students s ON g.student_id = s.student_id " +
                "WHERE g.student_id = ?";

        try {
            PreparedStatement ps = connect.prepareStatement(query,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();


            if (!rs.isBeforeFirst()) {
                System.out.println("No grades found.");
                return;
            } else {

                rs.next();
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                System.out.println("Student: " + firstName + " " + lastName);
                System.out.println("Your Grades Are:");


                rs.beforeFirst();


                while (rs.next()) {
                    int examId = rs.getInt("exam_id");
                    String grade = rs.getString("grade");
                    int marksObtained = rs.getInt("marks_obtained");

                    System.out.println("Exam ID: " + examId);
                    System.out.println("Grade: " + grade);
                    System.out.println("Marks Obtained: " + marksObtained);
                    System.out.println("--------------------------------------------");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching grade details: " + e.getMessage());
        }
    }




}
