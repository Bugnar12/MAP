package gui.programList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(MainGUI.class.getResource("ProgramList.fxml"));
        Parent programListRoot = loader1.load();
        Scene programListScene = new Scene(programListRoot, 1140, 950);
        ProgramList programList = loader1.getController();
        primaryStage.setTitle("Program List");
        primaryStage.setScene(programListScene);
        primaryStage.show();

        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(MainGUI.class.getResource("ControllerGUI.fxml"));
        Parent controllerGUIRoot = loader2.load();
        Scene controllerGUIScene = new Scene(controllerGUIRoot, 1340, 950);
        ControllerGUI controllerGUI = loader2.getController();
        programList.setControllerView(controllerGUI);
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Controller GUI");
        secondaryStage.setScene(controllerGUIScene);
        secondaryStage.show();

        //Close both windows when one of them is closed
        primaryStage.setOnCloseRequest(event -> {
            primaryStage.close();
            secondaryStage.close();
        });
        secondaryStage.setOnCloseRequest(event -> {
            primaryStage.close();
            secondaryStage.close();
        });

    }
}
