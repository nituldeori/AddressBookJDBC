import java.time.LocalDate;
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

    private ContactsData getContactsData(String firstName) {
        return this.contactList.stream()
                .filter(contactsData -> contactsData.firstName.equals(firstName))
                .findFirst()
                .orElse(null);
    }

    public List<ContactsData> readContactsData(InputOutputService ioService){
        if(ioService.equals(InputOutputService.DB_IO))
            this.contactList = addressBookDBService.readData();
        return this.contactList;
    }

    public void updateContactAddress(String firstName, String address) {
        int result = addressBookDBService.updateContactData(firstName,address);
        if(result == 0) return;
        ContactsData contactsData = this.getContactsData(firstName);
        if(contactsData != null) contactsData.address = address;


    }

    public boolean checkContactListInSymcWithDB(String firstName) {
        List<ContactsData> contactsDataList = addressBookDBService.getContactsDataByName(firstName);
        return contactsDataList.get(0).equals(getContactsData(firstName));

    }

    public List<ContactsData> readContactsforDateRange(InputOutputService dbIo, LocalDate startDate, LocalDate endDate) {
        if(dbIo.equals(InputOutputService.DB_IO))
            return addressBookDBService.getContactsForDateRange(startDate, endDate);
        return null;

    }

    public List<ContactsData> readContactsforCity(InputOutputService dbIo, String city) {
        if(dbIo.equals(InputOutputService.DB_IO))
            return addressBookDBService.getContactsForCity(city);
        return null;
    }

    public void addContactToAddressBook(String firstName, String lastName, String address, String city, String state, String zip, String phoneNo, String email, LocalDate start) {
        contactList.add(addressBookDBService.addContactToDB(firstName, lastName, address, city, state, zip, phoneNo, email, start));
    }



}
