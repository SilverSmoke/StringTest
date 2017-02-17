package checks;

import checks.model.StringOfCheck;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/interfaceUser.fxml"));
        primaryStage.setTitle("Семейный бюджет");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public boolean showEditFrame(StringOfCheck stringOfCheck){

        try{

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/editFrame.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактор чека");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            dialogStage.showAndWait();

            /*primaryStage.setTitle("Семейный бюджет");
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.show();*/
            return true;

        }catch (IOException e){
            e.printStackTrace();
            return false;
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
