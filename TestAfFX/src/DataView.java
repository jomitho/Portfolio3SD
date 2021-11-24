/*
Chris Langgaard Klokholm 68852
Fredrik Dan Lund 69205
Lasse Nicolas Madsen 69398
Jonathan Thorsteinsson 68884
Selvije Fejzuli 68871
 */
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class DataView {
    Controller control;

    //Opret alle knapper samt tekstfelter der skal bruges i guien
    private GridPane DataView;
    Label studentLabel = new Label("Student:");
    ComboBox<String> studentComb = new ComboBox<String>();
    Label courseLabel = new Label("Course:");
    ComboBox<String> courseComb = new ComboBox<String>();
    Button findStudentBtn = new Button("Select Student");
    Button avgCourseBtn = new Button("Average Course Grade");
    Button avgStudentBtn = new Button("Average Student Grade");
    TextArea outPutTxtArea = new TextArea();
    Button exitBtn = new Button("Exit");

    public DataView(Controller control){
        this.control=control;
        Configure();
    }
    //Placer knapper samt tekstfelter i det oprettede Grid
    private void Configure(){
        //opretter grid
        DataView =new GridPane();
        DataView.setMinSize(500,500);
        DataView.setPadding(new Insets(5,5,5,5));
        DataView.setVgap(5);
        DataView.setHgap(1);

        //placerer knapper/tekst
        DataView.add(studentLabel, 1, 1);
        DataView.add(studentComb, 15,1);
        DataView.add(courseLabel, 1,3);
        DataView.add(courseComb, 15, 3);
        DataView.add(findStudentBtn, 15, 6);
        DataView.add(avgCourseBtn, 32,6);
        DataView.add(avgStudentBtn, 34,6);
        DataView.add(outPutTxtArea,2,14,34,14);
        DataView.add(exitBtn, 40, 30);

        //Indsæt data i studentComb
        ObservableList<String> studentList = control.getStudent();
        studentComb.setItems(studentList);
        studentComb.getSelectionModel().selectFirst();

        //indsæt data i courseComb
        ObservableList<String> courseList = control.getCourse();
        courseComb.setItems(courseList);
        courseComb.getSelectionModel().selectFirst();
    }

    public Parent asParent(){
        return DataView;
    }

}
