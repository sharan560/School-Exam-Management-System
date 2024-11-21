package com.sece.school.entity;

import java.sql.*;
import java.util.Scanner;

public class Grade {
    private int gradeId;
    private int examId;
    private int studentId;
    private int marksObtained;
    private char grade;
    private String dateAssigned;

    enum GradeEnum {
        A, B, C, D, E, F;

        public static boolean isValid(String grade) {
            for (GradeEnum g : GradeEnum.values()) {
                if (g.name().equals(grade)) {
                    return true;
                }
            }
            return false;
        }


        public static GradeEnum fromChar(char grade) {
            return GradeEnum.valueOf(String.valueOf(grade));
        }
    }

    public Grade() {
    }

    public Grade(int examId, int studentId, int marksObtained, char grade, String dateAssigned) {
        this.examId = examId;
        this.studentId = studentId;
        this.marksObtained = marksObtained;
        this.grade = grade;
        this.dateAssigned = dateAssigned;
    }


    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(int marksObtained) {
        this.marksObtained = marksObtained;
    }

    public char getGrade() {
        return grade;
    }

    public void setGrade(char grade) {
        this.grade = grade;
    }

    public String getDateAssigned() {
        return dateAssigned;
    }

    public void setDateAssigned(String dateAssigned) {
        this.dateAssigned = dateAssigned;
    }


    public void assignGrades(Connection connect, int examId) {
        Scanner sc = new Scanner(System.in);
        String query = "SELECT * FROM students";

        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("No Records Found");
                return;
            }


            while (rs.next()) {
                System.out.println("Student ID: " + rs.getInt("student_id"));
                System.out.println("First Name: " + rs.getString("first_name"));
                System.out.println("Last Name: " + rs.getString("last_name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("DOB: " + rs.getString("dob"));
                System.out.println("Enrollment Date: " + rs.getDate("enrollment_date"));
                System.out.println("Username: " + rs.getString("username"));
                System.out.println("-----------------------------------");
            }

            System.out.print("Enter the Student ID to assign grades: ");
            int studentId = sc.nextInt();
            sc.nextLine();

            while (true) {
                System.out.print("Enter marks obtained: ");
                int marksObtained = sc.nextInt();
                sc.nextLine();

                System.out.print("Enter grade (A, B, C, D, E, F): ");
                String gradeInput = sc.nextLine().toUpperCase();


                if (!GradeEnum.isValid(gradeInput)) {
                    System.out.println("Invalid grade entered. Please enter a valid grade (A, B, C, D, E, F).");
                    continue;
                }


                String insertOrUpdateQuery = "INSERT INTO Grades (student_id, exam_id, marks_obtained, grade) VALUES (?, ?, ?, ?)";
                PreparedStatement Ps = connect.prepareStatement(insertOrUpdateQuery);
                Ps.setInt(1, studentId);
                Ps.setInt(2, examId);
                Ps.setInt(3, marksObtained);
                Ps.setString(4, gradeInput);

                int rowsAffected = Ps.executeUpdate();
                Ps.close();

                if (rowsAffected > 0) {
                    System.out.println("Grades assigned successfully for Student ID: " + studentId);
                } else {
                    System.out.println("Failed to assign grades.");
                }
                break;
            }


        } catch (SQLException e) {
            System.out.println("Error while assigning grades: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    public void viewGrade(Connection connect) {
        String query = "SELECT g.student_id, g.exam_id, g.marks_obtained, g.grade, s.first_name, s.last_name " +
                "FROM Grades g " +
                "JOIN Students s ON g.student_id = s.student_id";

        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("Grades table is empty.");
                return;
            }

            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                int examId = rs.getInt("exam_id");
                int marksObtained = rs.getInt("marks_obtained");
                String grade = rs.getString("grade");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");

                System.out.println("Student Name: " + firstName + " " + lastName);
                System.out.println("Student ID: " + studentId);
                System.out.println("Exam ID: " + examId);
                System.out.println("Marks Obtained: " + marksObtained);
                System.out.println("Grade: " + grade);
                System.out.println("-----------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching grades: " + e.getMessage());
        }
    }
}
