package contactApp;

import contactApp.dataModel.Contact;
import contactApp.dataModel.ContactData;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class MainWindowController {
    @FXML private BorderPane mainPanel;
    @FXML private TableView<Contact> contactsTable;
    @FXML private TableColumn<Contact,String> firstNameTableColumn;
    @FXML private TableColumn<Contact,String> lastNameTableColumn;
    @FXML private TableColumn<Contact,String> phoneNumberTableColumn;
    @FXML private TableColumn<Contact,String> notesTableColumn;

    private ContactData data;

    public void initialize() throws IOException {
        data = new ContactData();
        data.loadContactsData();
        contactsTable.setItems(data.getContactsData());
        firstNameTableColumn.setCellValueFactory(contact -> new SimpleStringProperty(contact.getValue().getFirstName()));
        lastNameTableColumn.setCellValueFactory(contact -> new SimpleStringProperty(contact.getValue().getLastName()));
        phoneNumberTableColumn.setCellValueFactory(contact -> new SimpleStringProperty(contact.getValue().getPhoneNumber()));
        notesTableColumn.setCellValueFactory(contact -> new SimpleStringProperty(contact.getValue().getNotes()));
    }

    @FXML
    public void showAddContactDialog() throws IOException {
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Add New Contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("dialogWindow.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            DialogWindowController dialogWindowController = fxmlLoader.getController();
            Contact newContact = dialogWindowController.getNewContact();
            data.addContact(newContact);
            contactsTable.getSelectionModel().select(newContact);
            data.saveContactsData();
        }
    }

    @FXML
    public void showEditContactDialog() throws IOException {
        Contact selectedContact = contactsTable.getSelectionModel().getSelectedItem();
        int index = contactsTable.getSelectionModel().getSelectedIndex();
        if(selectedContact == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Contact Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the contact you want to edit.");
            alert.showAndWait();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Edit Contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("dialogWindow.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        DialogWindowController dialogWindowController = fxmlLoader.getController();
        dialogWindowController.editContact(selectedContact);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            Contact newContact = dialogWindowController.getNewContact();
            data.updateContact(index, newContact);
            contactsTable.getSelectionModel().select(newContact);
            data.saveContactsData();
        }
    }

    @FXML
    public void showDeleteContactDialog() throws IOException {
        Contact selectedContact = contactsTable.getSelectionModel().getSelectedItem();
        int index = contactsTable.getSelectionModel().getSelectedIndex();
        if(selectedContact == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Contact Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the contact you want to delete.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Contact");
        alert.setHeaderText("Delete " +selectedContact.getFirstName()+ " In Your Contact");
        alert.setContentText("Press Ok To Confirm");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            data.removeContact(selectedContact);
            if(index > 0){
                contactsTable.getSelectionModel().select(index);
            } else {
                contactsTable.getSelectionModel().selectFirst();
            }
            data.saveContactsData();
        }
    }

    @FXML
    public void closeApplication(){
        System.exit(0);
    }

}
