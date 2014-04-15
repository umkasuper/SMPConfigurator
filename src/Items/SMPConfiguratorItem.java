package Items;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

/**
 * Created by maksim on 15.04.14.
 */
public class SMPConfiguratorItem extends AnchorPane {

    @FXML
    public Rectangle rectangle;

    @FXML
    public AnchorPane panel;

    @FXML
    public Label name;

    double x;
    double y;

    public SMPConfiguratorItem(double x, double y) {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/SMPConfiguratorItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.setLayoutX(x);
        this.setLayoutY(y);

        name.setVisible(false);
    }

    @FXML
    void initialize() {
        //Rectangle r = new Rectangle(0, 0, 250, 250);
        //r.setFill(Color.BLUE);
        //panel.getChildren().add(r);

    }

    /**
     * устанавливает заголовок компоненты
     * @param title заголовок
     */
    public void setTitle(String title)
    {
        name.setVisible(!title.isEmpty());
        name.setText(title);
    }

}
