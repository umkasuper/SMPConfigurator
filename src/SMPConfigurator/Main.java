package SMPConfigurator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ResourceBundle SMPConfiguratorBundle = ResourceBundle.getBundle("SMPConfigurator.languages.app", new Locale("en", "US"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/SMPConfigurator.fxml"), SMPConfiguratorBundle);

        AnchorPane root = (AnchorPane)loader.load();

        primaryStage.setTitle(SMPConfiguratorBundle.getString("SMPConfigurator.title"));
        primaryStage.setScene(new Scene(root));

        SMPConfigurator controller = loader.getController();
        controller.setDialogStage(primaryStage);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
