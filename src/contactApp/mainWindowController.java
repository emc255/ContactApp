package contactApp;

import contactApp.dataModel.Contact;
import contactApp.dataModel.ContactData;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class mainWindowController {
    @FXML private BorderPane mainPanel;
    @FXML private TableView<Contact> contactsTable;

    private ContactData data;

    public void initialize() throws IOException {
        data = new ContactData();
        //data.saveContactsData();
        data.loadContactsData();
        System.out.println(data.getContactsData().get(0).getFirstName());
    }
}
