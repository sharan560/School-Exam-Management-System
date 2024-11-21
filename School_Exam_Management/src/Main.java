
import com.sece.school.entity.Exam;
import com.sece.school.entity.Grade;
import com.sece.school.entity.Student;
import com.sece.school.entity.Question;
import com.sece.school.entity.Teacher;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    enum DepEnum {
        MATHS, SCIENCE, ENGLISH,SS,TAMIL;

        public static boolean isValid(String department) {
            for (DepEnum dep : DepEnum.values()) {
                if (dep.name().equals(department)) {
                    return true;
                }
            }
            return false;
        }
    }

    private static final String url = "jdbc:mysql://localhost:3306/school_exam_management";
    private static final String user = "root";
    private static final String password = "root";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Connection connect = DriverManager.getConnection(url, user, password);

            while (true) {
                System.out.println("\nWelcome to Exam Management System");
                System.out.println("1. Create Account");
                System.out.println("2. Login Account");
                System.out.println("0. Exit The Application");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 0:
                        System.out.println("Exiting the application. Goodbye!");
                        return;
                    case 1:
                        System.out.println("Select Account Type:");
                        System.out.println("1. Teacher Account");
                        System.out.println("2. Student Account");
                        int accountType = sc.nextInt();
                        sc.nextLine();

                        if (accountType == 1) {
                            createTeacherAccount(connect, sc);
                        } else if (accountType == 2) {
                            createStudentAccount(connect, sc);
                        } else {
                            System.out.println("Invalid option. Please choose 1 or 2.");
                        }
                        break;
                    case 2:
                        System.out.println("Select Account Type:");
                        System.out.println("1. Teacher Account");
                        System.out.println("2. Student Account");
                        int accountType1 = sc.nextInt();
                        sc.nextLine();

                        if (accountType1 == 1) {
                            LoginTeacherAccount(connect, sc);
                        } else if (accountType1 == 2) {
                            LoginStudentAccount(connect, sc);
                        } else {
                            System.out.println("Invalid option. Please choose 1 or 2.");
                        }
                        break;
                    default:
                        System.out.println("Invalid choice, please enter 0, 1, or 2.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }



    private static void LoginStudentAccount(Connection connect, Scanner sc) {
        Student student = new Student();

        System.out.println("Enter username");
        String username = sc.nextLine();

        System.out.println("Enter password");
        String password = sc.nextLine();

        String query = "SELECT * FROM students WHERE username = ? AND password = ?";

        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                student.setStudentId(rs.getInt("student_id"));
                student.setFirstName(rs.getString("first_name"));
                student.setSecondName(rs.getString("last_name"));
                student.setEmail(rs.getString("email"));
                student.setDob(rs.getString("dob"));
                student.setEnrollement_Date(rs.getDate("Enrollment_date").toLocalDate());

                System.out.println("Login successful! Welcome " + student.getFirstName());
                createStudentfun(student.getStudentId(),sc,connect,student);
            } else {
                System.out.println("Invalid username or password. Please try again.");
            }
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
    }

    private static void createStudentfun(int studentId, Scanner sc, Connection connect,Student student) {
        while (true) {
            System.out.println("1.View Exam");
            System.out.println("2.View Grades");
            System.out.println("0.Go To MainMenu...");

            int n = sc.nextInt();
            sc.nextLine();
            switch (n) {
                case 0:
                    return;
                case 1:
                    student.viewExam(sc, connect);
                    break;
                case 2:
                    student.viewGrade(sc, connect, studentId);
                    break;
                default:
                    System.out.println("Invalid Choice");

            }
        }

    }

    private static void LoginTeacherAccount(Connection connect, Scanner sc) {
        Teacher teacher=new Teacher();
        System.out.println("Enter userName");
        String username=sc.nextLine();
        System.out.println("Enter password");
        String password=sc.nextLine();

        String query="Select * from teachers where username=? and password=?";
        try{
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                teacher.setTeacherId(rs.getInt("teacher_id"));
                teacher.setFirstName(rs.getString("first_name"));
                teacher.setSecondName(rs.getString("last_name"));
                teacher.setEmail(rs.getString("email"));
                teacher.setDepartment(rs.getString("department"));
                teacher.setHireDate(rs.getDate("hire_date").toLocalDate());

                System.out.println("Login successful! Welcome " + teacher.getFirstName());
                Teacherfun(teacher.getTeacherId(),sc,connect,teacher);
            } else {
                System.out.println("Invalid username or password. Please try again.");
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static void Teacherfun(int teacherId, Scanner sc, Connection connect,Teacher teacher) {
        while(true) {
            System.out.println("1.Add Exam");
            System.out.println("2.Set Questions For Exam");
            System.out.println("3.Assign grades");
            System.out.println("4.View grades");
            System.out.println("0.Go To MainMenu...");
            Student student=new Student();
            int n = sc.nextInt();
            sc.nextLine();
            switch (n) {
                case 0:
                    return;
                case 1:
                    Exam exam = new Exam();
                    exam.Create_Exam(sc, connect, teacherId);
                    break;
                case 2:
                    while(true) {
                        student.viewExam(sc, connect);

                        System.out.print("Enter the ExamId: ");
                        int examId = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Enter The Question type");
                        String questionType = sc.nextLine();
                        System.out.println("Enter The Question Text");
                        String questionText = sc.nextLine();
                        System.out.println("Enter The marks");
                        int marks = sc.nextInt();
                        sc.nextLine();
                        Question question = new Question(examId, questionText, questionType, marks);
                        question.addQuestion(examId, questionType, questionText, sc, connect, marks);
                        System.out.println("Do You want to add More Question if yes type 1 else type 0  ");
                        int n1=sc.nextInt();
                        if(n1==0)
                        {
                            return;
                        }
                        break;
                    }
                case 3:
                    student.viewExam(sc, connect);
                    System.out.print("Enter the ExamId: ");
                    int examId1 = sc.nextInt();
                    sc.nextLine();

                    Grade grade = new Grade();
                    grade.assignGrades(connect, examId1);
                    break;


                case 4:
                    Grade grade1=new Grade();
                    grade1.viewGrade(connect);
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        }
    }


    private static void createTeacherAccount(Connection connect, Scanner sc) {
        System.out.println("Enter the following details to create a Teacher account:");

        System.out.print("First Name: ");
        String firstName = sc.nextLine();

        System.out.print("Second Name: ");
        String secondName = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Department (maths, science, english): ");
        String department = sc.nextLine().toUpperCase();


        if (!DepEnum.isValid(department)) {
            System.out.println("Invalid department entered. Please enter a valid department (maths, science, english).");
            return;
        }

        LocalDate hireDate = LocalDate.now();

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        Teacher teacher = new Teacher(firstName, secondName, email, department, hireDate, username, password);
        System.out.println("The entered details are:\n" + teacher);

        String query = "INSERT INTO teachers (first_name, last_name, email, department, hire_date, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, teacher.getFirstName());
            preparedStatement.setString(2, teacher.getSecondName());
            preparedStatement.setString(3, teacher.getEmail());
            preparedStatement.setString(4, teacher.getDepartment());
            preparedStatement.setDate(5, Date.valueOf(teacher.getHireDate()));
            preparedStatement.setString(6, teacher.getUsername());
            preparedStatement.setString(7, teacher.getPassword());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Teacher account created successfully!");
            }
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int generatedTeacherId = rs.getInt(1);
                teacher.setTeacherId(generatedTeacherId);
                System.out.println("Generated Teacher ID: " + generatedTeacherId);
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("Email address already exists")) {
                System.out.println("Error: The email address is already in use. Please choose another one.");
            } else {
                System.out.println("Error while creating teacher account: " + e.getMessage());
            }
        }
    }




    private static void createStudentAccount(Connection connect, Scanner sc) {
        System.out.println("Enter the following details to create a Student  account:");
        System.out.print("First Name: ");
        String firstName = sc.nextLine();
        System.out.print("Second Name: ");
        String secondName = sc.nextLine();
        System.out.print("Dob ");
        String dob = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        LocalDate EnrollementDate =  LocalDate.now();
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        Student student=new Student(firstName,secondName,dob,email,EnrollementDate,username,password);
        System.out.println("the details Are:"+student.toString());
        String query="insert into students (first_name, last_name, dob,username,password,email, enrollment_date) VALUES (?, ?, ?, ?, ?,?,?)";
        try{
            PreparedStatement ps=connect.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, student.getFirstName());
            ps.setString(2,student.getSecondName());
            ps.setString(3,student.getDob());
            ps.setString(4,student.getUserName());
            ps.setString(5,student.getPassword());
            ps.setString(6,student.getEmail());
            ps.setDate(7,Date.valueOf(student.getEnrollement_Date()));

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Student account created successfully!");
            }
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int generatedStudetId = rs.getInt(1);
                student.setStudentId(generatedStudetId);
                System.out.println("Your Student ID: " + generatedStudetId);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Error: The username '" + username + "' already exists. Please choose another one.");
        } catch (SQLException e) {
            System.out.println("Error while creating student account: " + e.getMessage());

        }

    }
}



