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

    /**
     * кнопка отмена
     */
    @FXML
    public Button cancel;

    @FXML
    public Button add;

    /**
     * языковые ресурсы
     */
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    /**
     * возвращаемый параметр успешности закрытия окна
     */
    boolean isOk = false;

    /**
     * само окно
     */
    private Stage dialogStage;

    /**
     * Метод вызывается после загрузки fxml  с описанием
     */
    @FXML
    void initialize() {

    }

    /**
     * Устанавливает stage для этого диалога.
     * @param dialogStage связаное окно
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public SMPConfiguratorCreateModule()
    {

    }

    /**
     * Возвращает состояние диалогового кона
     * @return true если нажаты add и false если нажата cancel
     */
    public boolean isOk()
    {
        return isOk;
    }

    /**
     * обработчик сообщений от кнопок add и cancel
     * @param actionEvent сообщение
     */
    public void action(ActionEvent actionEvent) {
        dialogStage.close();
        isOk = (actionEvent.getSource() == add);
    }

}
