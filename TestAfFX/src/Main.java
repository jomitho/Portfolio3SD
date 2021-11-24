/*
Chris Langgaard Klokholm 68852
Fredrik Dan Lund 69205
Lasse Nicolas Madsen 69398
Jonathan Thorsteinsson 68884
Selvije Fejzuli 68871
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public void start(Stage primaryStage) throws Exception{
        String url ="jdbc:sqlite:/Users/chrisklokholm/OneDrive - Roskilde Universitet/Igangværende Kurser/Software Development/SD IntelliJ/TestAfFX/identifier.sqlite";
        StudentModel controle = new StudentModel(url);
        Controller control = new Controller(controle);
        DataView view = new DataView(control);
        control.setView(view);
        primaryStage.setTitle("Portfolio3 jdbc");
        primaryStage.setScene(new Scene(view.asParent(), 600,500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

