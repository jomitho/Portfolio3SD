/*
Chris Langgaard Klokholm 68852
Fredrik Dan Lund 69205
Lasse Nicolas Madsen 69398
Jonathan Thorsteinsson 68884
Selvije Fejzuli 68871
 */
import javafx.application.Platform;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

public class Controller {
    StudentModel model;
    DataView view;

    public Controller(StudentModel model) {
        this.model = model;
        try {
            model.connect();
            model.CreateStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    //Lav liste med de studerende
    public ObservableList<String> getStudent() {
        ArrayList<String> Names = model.SQLStudentNameList();
        return FXCollections.observableList(Names);
    }
    //Lav liste med kurser
    public ObservableList<String> getCourse() {
        ArrayList<String> Course = model.SQLCourseList();
        return FXCollections.observableList(Course);
    }
    //Vis gennemsnitskarakter på de forskellige kurser
    public void HandleCourseAvg (TextArea txtArea){
        txtArea.clear();
        txtArea.appendText("Student name: Grade: \n");
        model.PrepareCourseAvg();
        ArrayList<StudentModel.StudentList> Students = model.FindCourseAvg();
        for(int i =0; i<Students.size();i++){
            txtArea.appendText( Students.get(i).Student+ " "+ Students.get(i).Course+ ": "+ Students.get(i).Grade + "\n");
        }
    }
    //Beregner gennemsnit af karakter
    public void setView(DataView view) {
        this.view = view;
        view.exitBtn.setOnAction(e -> Platform.exit());
        view.findStudentBtn.setOnAction(e -> HandlePrintStudent(view.studentComb.getValue(), view.courseComb.getValue(), view.outPutTxtArea));
        view.avgStudentBtn.setOnAction(e -> HandleStudentAvg(view.outPutTxtArea));
        view.avgCourseBtn.setOnAction(e -> HandleCourseAvg(view.outPutTxtArea));
    }
    //Lav et output på den valgte studerende, og vis karakter
    public void HandlePrintStudent (String Student, String Course, TextArea txtArea){
        txtArea.clear();
        model.PrepareFindStudent();
        ArrayList<StudentModel.StudentList> Students = model.FindStudent(Student, Course);
        for(int i =0; i<Students.size();i++){
            txtArea.appendText( Students.get(i).Student+ " "+ Students.get(i).Course+ ": "+ Students.get(i).Grade + "\n");
        }
    }
    //Vis de studerendes gennemsnitkarakter
    public void HandleStudentAvg (TextArea txtArea){
        txtArea.clear();
        txtArea.appendText("Student name: Grade: \n");
        model.PrepareAvgStudent();
        ArrayList<StudentModel.StudentList> Students = model.FindStudentGradeAvg();
        for(int i =0; i<Students.size();i++){
            txtArea.appendText( Students.get(i).Student+ " "+ Students.get(i).Course+ ": "+ Students.get(i).Grade + "\n");
        }
    }
}

