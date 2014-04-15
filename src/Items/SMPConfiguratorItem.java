package Items;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

/**
 * Created by maksim on 15.04.14.
 */
public class SMPConfiguratorItem extends AnchorPane {

    public Rectangle rectangle;
    public AnchorPane panel;

    public SMPConfiguratorItem() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/SMPConfiguratorItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    void initialize() {
        Rectangle r = new Rectangle(0, 0, 250, 250);
        r.setFill(Color.BLUE);
        panel.getChildren().add(r);
    }
}
