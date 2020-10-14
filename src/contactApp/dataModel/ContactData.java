package contactApp.dataModel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ContactData {
    private static final String CONTACT_FILE = "contacts.json";

    private ObservableList<Contact> contacts;

    public ContactData() {
        this.contacts = FXCollections.observableArrayList();
    }

    public ObservableList<Contact> getContactsData() {
        return contacts;
    }

    public void addContact(Contact contact){
        contacts.add(contact);
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
    }

    public void updateContact(Contact contact, int index) {
        contacts.set(index, contact);
    }

    public void loadContactsData() throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Contact> dataContacts = objectMapper.readValue(new File(CONTACT_FILE), new TypeReference<>(){});
            contacts.addAll(dataContacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveContactsData() throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Contact contact = new Contact("sinb","sinb","143","Mystery");
            contacts.add(contact);
            Contact contact2 = new Contact("euhna","sinb","143","Mystery");
            contacts.add(contact2);
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(new File(CONTACT_FILE), contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
