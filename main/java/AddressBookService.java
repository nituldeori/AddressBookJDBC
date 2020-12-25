import java.util.List;

public class AddressBookService {
    public enum IOService{CONSOLE_IO,FILE_IO,REST_IO, DB_IO}
    private List<ContactsData> contactList;
    private AddressBookDBService addressBookDBService;
    public enum InputOutputService{CONSOLE_IO,FILE_IO,REST_IO, DB_IO}

    public AddressBookService(){
        addressBookDBService = AddressBookDBService.getInstance();
    }

    public AddressBookService(List<ContactsData> contactList){
        this();
        this.contactList=contactList;

    }

    public List<ContactsData> readContactsData(InputOutputService ioService){
        if(ioService.equals(InputOutputService.DB_IO))
            this.contactList = addressBookDBService.readData();
        return this.contactList;
    }



}
