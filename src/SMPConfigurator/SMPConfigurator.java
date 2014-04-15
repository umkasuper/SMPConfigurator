package SMPConfigurator;

import Items.SMPConfiguratorItem;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.lang.System.*;

public class SMPConfigurator {
    @FXML
    public MenuItem close;

    @FXML
    public TreeView ats;

    @FXML
    public AnchorPane configurationPanel;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private Stage dialogStage;

    @FXML
    void initialize() {
        TreeItem item = new TreeItem<String>(resources.getString("root_item.text"));
        item.getChildren().add(new TreeItem<String>("Item3"));

        item.setExpanded(true);
        ats.setRoot(item);


        //init_tree_menu_popup();

        ats.setCellFactory(new Callback<TreeView<String>,TreeCell<String>>(){
            @Override
            public TreeCell<String> call(TreeView<String> p) {
                return new TextFieldTreeCellImpl();
            }
        });


        SMPConfiguratorItem[] slot = new SMPConfiguratorItem[1];
        for (int i = 0; i < 1; i++) {

            slot[i] = new SMPConfiguratorItem(i*40, 30);
            if ((i % 5) == 0)
                slot[i].setTitle("Hello");
            //configurationPanel.getChildren().add(slot[i]);
        }
        configurationPanel.getChildren().addAll(slot);
    }

    @FXML
    public void OnClose(ActionEvent actionEvent) {
        out.print("On close");

    }


    /**
     * Устанавливает stage для этого диалога.
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    void OnTreeViewContextMenuRequested()
    {

    }
/*
    @FXML
    void OnTreeViewClick(MouseEvent event) {
        out.println(event.toString());
        if ((event.getButton() == MouseButton.SECONDARY))
        {
            try {
                LabeledText item = (LabeledText) event.getTarget();
                if (resources.getString("root_item.text") == item.getText()) {
                    //ats.getContextMenu().show(ats, event.getScreenX(), event.getScreenY());
                    ContextMenu menu = init_tree_menu_popup();
                    menu.show(item, event.getScreenX(), event.getScreenY());
                    out.println("pressed on root item");
                } else
                    out.println("pressed on parent item");

            }

            catch(ClassCastException ex)
            {
            }
        }
    }
*/


    class TreeMouseEventDispatcher implements EventDispatcher {
        private final EventDispatcher originalDispatcher;

        public TreeMouseEventDispatcher(EventDispatcher originalDispatcher) {
            this.originalDispatcher = originalDispatcher;
        }

        @Override
        public Event dispatchEvent(Event event, EventDispatchChain tail) {
            out.println(event.toString());
            if (event instanceof MouseEvent) {
                if (((MouseEvent) event).getButton() == MouseButton.PRIMARY
                        && ((MouseEvent) event).getClickCount() >= 1) {

                    if (!event.isConsumed()) {
                        out.println("dispatchEvent");
                        if (event.getTarget() instanceof StackPane) {
                            ObservableList<String> styleClassList =  ((StackPane)event.getTarget()).getStyleClass();
                            for (String styleClass : styleClassList) {
                                if (styleClass == "arrow" || styleClass == "tree-disclosure-node")
                                    event.consume();
                            }
                        }
                    }


                }
            }
            return originalDispatcher.dispatchEvent(event, tail);
        }
    }

    private final class TextFieldTreeCellImpl extends TreeCell<String> {

        private TextField textField;
        private ContextMenu moduleMenu = new ContextMenu();
        private ContextMenu rootMenu = new ContextMenu();

        // создает контекстное меню для листьев (модулей)
        private void CreateModuleMenu() {
            MenuItem contextMenuItemChangeNumber = new MenuItem(resources.getString("context.menu.tree.change.module.number"));
            contextMenuItemChangeNumber.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    out.println("selected change module number");
                }
            });

            MenuItem contextMenuItemChangeName = new MenuItem(resources.getString("context.menu.tree.change.module.name"));
            contextMenuItemChangeName.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    out.println("selected change module name");
                    MultipleSelectionModel<TreeItem <String> > selection = ats.getSelectionModel();
                    TreeItem<String> item = selection.getSelectedItem();
                    ats.edit(item);

                }
            });

            MenuItem contextMenuItemDeleteModule = new MenuItem(resources.getString("context.menu.tree.delete.module"));
            contextMenuItemDeleteModule.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    out.println("selected delete module");
                }
            });
            moduleMenu.getItems().addAll(contextMenuItemChangeNumber, contextMenuItemChangeName, contextMenuItemDeleteModule);
        }

        // создает контекстное меню для корня (атс)
        private void CreateRootMenu() {
            MenuItem contextMenuItemChangeName = new MenuItem(resources.getString("context.menu.tree.change.name"));
            contextMenuItemChangeName.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    out.println("selected change ats name");
                }
            });

            MenuItem contextMenuItemAddModule = new MenuItem(resources.getString("context.menu.tree.add.module"));
            contextMenuItemAddModule.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    out.println("selected add module " + e.getSource().toString());
                    rootMenu.hide();
                    try {
                        ResourceBundle SMPConfiguratorBundle = ResourceBundle.getBundle("SMPConfigurator.languages.CreateModule", new Locale("en", "US"));

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/SMPConfiguratorCreateModule.fxml"), SMPConfiguratorBundle);
                        AnchorPane page = (AnchorPane) loader.load();
                        Stage dialog = new Stage();
                        dialog.setTitle(resources.getString("context.menu.tree.add.module"));
                        dialog.initModality(Modality.WINDOW_MODAL);
                        dialog.initOwner(dialogStage);
                        Scene scene = new Scene(page);
                        dialog.setScene(scene);

                        SMPConfiguratorCreateModule configuratorCreateModule = loader.getController();
                        configuratorCreateModule.setDialogStage(dialog);

                        dialog.showAndWait();

                        if (configuratorCreateModule.isOk())
                        {
                            // добавляем в конфигурацию новый модуль
                        }

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });

            rootMenu.getItems().addAll(contextMenuItemChangeName, contextMenuItemAddModule);
        }


        public TextFieldTreeCellImpl() {
            CreateModuleMenu();
            CreateRootMenu();
        }

        @Override
        public void startEdit() {
            super.startEdit();

            if (textField == null) {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();

        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            //setText((String) getItem());
            setText(getString());
            setGraphic(getTreeItem().getGraphic());
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (item != null && !empty) {
                EventDispatcher originalDispatcher = getEventDispatcher();
                setEventDispatcher(new TreeMouseEventDispatcher(originalDispatcher));
            }

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(getTreeItem().getGraphic());

                    if (getTreeItem().isLeaf()) { // лист дерева
                        setContextMenu(moduleMenu);
                    } else { // узел дерева
                        setContextMenu(rootMenu);
                    }
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setOnKeyReleased(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(textField.getText());
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });

        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

}
