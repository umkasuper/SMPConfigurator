package SMPConfigurator;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.System.*;

public class SMPConfigurator {
    @FXML
    public MenuItem close;

    @FXML
    public TreeView ats;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    }

    @FXML
    public void OnClose(ActionEvent actionEvent) {
        out.print("On close");

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

                    Stage myDialog = new Stage();
                    myDialog.initModality(Modality.WINDOW_MODAL);
                    //Window node = ((Node)e.getSource()).getScene().getWindow();
                    //myDialog.initOwner(((Node)e.getSource()).getScene().getWindow());

                    Scene myDialogScene = null;
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("view/SMPConfiguratorCreateModule.fxml"), resources);
                        myDialogScene = new Scene(root, 100, 100);
                        myDialog.setScene(myDialogScene);
//                        myDialog.show();
                        myDialog.showAndWait();
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
