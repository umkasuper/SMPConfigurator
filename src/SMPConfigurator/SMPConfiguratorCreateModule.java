package SMPConfigurator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by maksim on 14.04.14.
 */
public class SMPConfiguratorCreateModule {

    @FXML
    public Button cancel;
    @FXML
    public Button add;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    boolean isOk = false;

    private Stage dialogStage;

    @FXML
    void initialize() {

    }

    /**
     * Устанавливает stage для этого диалога.
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public SMPConfiguratorCreateModule()
    {

    }

    /**
     * Возвращает состояние диалогового кона
     * @return
     */
    public boolean isOk()
    {
        return isOk;
    }

    public void action(ActionEvent actionEvent) {
        dialogStage.close();
        isOk = (actionEvent.getSource() == add);
    }

}
