package com.sece.school.entity;
import java.sql.Connection;

import java.time.LocalDate;

public class Teacher {

    private int teacherId;
    private String firstName;
    private String secondName;
    private String email;
    private String department;
    private LocalDate hireDate;
    private String username;
    private String password;


    public Teacher() {
    }

    public Teacher(String firstName, String secondName, String email, String department, LocalDate registrationDate, String username, String password) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.department = department;
        this.hireDate = registrationDate;
        this.username = username;
        this.password = password;
    }


    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate registrationDate) {
        this.hireDate = registrationDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "Teacher{" +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                ", registrationDate=" + hireDate +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
    public void showStudents(Connection connect) {

    }



}
