package SMPConfigurator;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

import static java.lang.System.*;

public class SMPConfigurator {
    public MenuItem close;

    public void OnClose(ActionEvent actionEvent) {
        out.print("On close");
    }
}
