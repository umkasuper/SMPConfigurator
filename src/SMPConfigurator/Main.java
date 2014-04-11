package SMPConfigurator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ResourceBundle SMPConfiguratorBundle = ResourceBundle.getBundle("SMPConfigurator.Bundle", new Locale("en", "US"));

        Parent root = FXMLLoader.load(getClass().getResource("view/SMPConfigurator.fxml"), SMPConfiguratorBundle);
        primaryStage.setTitle(SMPConfiguratorBundle.getString("SMPConfigurator.title"));
        primaryStage.setScene(new Scene(root, 1080, 800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
