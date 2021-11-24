/*
Chris Langgaard Klokholm 68852
Fredrik Dan Lund 69205
Lasse Nicolas Madsen 69398
Jonathan Thorsteinsson 68884
Selvije Fejzuli 68871
 */
import java.util.ArrayList;
import java.sql.*;
import static java.sql.DriverManager.getConnection;

public class StudentModel {
    Connection conn = null;
    String url;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    StudentModel(String url) {
        this.url = url;
    }

    public void connect() throws SQLException {
        conn = getConnection(url);
    }

    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public void CreateStatement() throws SQLException {
        this.stmt = conn.createStatement();

    }

    //SQL Query der finder samtlige navne og danner en liste med disse
    public ArrayList<String> SQLStudentNameList() {
        ArrayList<String> Names = new ArrayList<>();
        String sql = "Select firstName From Student;";
        try {
            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next()) {
                String name = rs.getString("firstName");
                Names.add(name);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        rs = null;
        return Names;
    }
    //Lav en liste med kursusnavn samt semesteret
    public ArrayList<String> SQLCourseList() {
        ArrayList<String> Courses = new ArrayList<>();
        String sql = "Select courseName, semester From Course;";
        try {
            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next()) {
                String course = rs.getString("courseName") + " " + rs.getString("semester");
                Courses.add(course);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        rs = null;
        return Courses;
    }
    //Dette opretter preparedstsmt(pstmt) for at oprette liste over fornavne
    public void PrepareFindStudent() {
        String sql = "SELECT Student.firstName, Course.courseName, StudentInfo.grade " +
                "FROM StudentInfo JOIN Student ON StudentInfo.studentID = Student.studentID JOIN Course on Course.courseID = StudentInfo.courseID WHERE Student.firstName = ? AND Course.courseName=? AND Course.semester=?;";
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //Sætter studentens navn og karakter i en arraylist til senere brug
    public ArrayList<StudentList> FindStudent(String Student, String Course) {
        ArrayList<StudentList> findStudent = new ArrayList<>();
        String name = Course.substring(0, Course.indexOf(' '));
        String semester = Course.substring(Course.indexOf(' ') + 1);
        try {
            pstmt.setString(1, Student);
            pstmt.setString(2, name);
            pstmt.setString(3, semester);
            rs = pstmt.executeQuery();
            if (rs == null) {
                System.out.println("No records fetched.");
            }
            while (rs != null && rs.next()) {
                findStudent.add(new StudentList(rs.getString(1), rs.getString(2), rs.getDouble(3)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return findStudent;
    }
    //udarbejder (pstmt) elevernes navne og karaktergennemsnit
    public void PrepareAvgStudent() {
        String sql = "SELECT Student.firstName, Student.lastName, avg(StudentInfo.grade)\n" +
                "FROM StudentInfo JOIN Student ON StudentInfo.studentID = Student.studentID Group By Student.firstName";
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //Lav findStudent metode
    private ArrayList<StudentList> getStudentLists() {
        ArrayList<StudentList> findStudent = new ArrayList<>();
        try {
            rs = pstmt.executeQuery();
            if (rs == null) {
                System.out.println("No records fetched.");
            }
            while (rs != null && rs.next()) {
                findStudent.add(new StudentList(rs.getString(1), rs.getString(2), rs.getDouble(3)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return findStudent;
    }
    // indsætter elevernes navne og karaktergennemsnit i en array for senere brug
    public ArrayList<StudentList> FindStudentGradeAvg() {
        return getStudentLists();
    }
    //Pstmt, henter kursus og karaktergennemsnit
    public void PrepareCourseAvg() {
        String sql = "SELECT Course.courseName, Course.semester, avg(StudentInfo.grade)\n" +
                "FROM StudentInfo JOIN Course ON StudentInfo.courseID = Course.courseID Group By Course.courseID";
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //indtaster kursus og karaktergennemsnit i en array til senere brug
    public ArrayList<StudentList> FindCourseAvg() {
        return getStudentLists();
    }
    //student list object
    class StudentList {
        String Student;
        String Course;
        Double Grade;
        public StudentList(String Student, String Course, Double Grade) {
            this.Student = Student;
            this.Course = Course;
            this.Grade = Grade;
        }
    }
}

