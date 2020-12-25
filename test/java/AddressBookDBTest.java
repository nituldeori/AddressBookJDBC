import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class AddressBookDBTest {
    @Test
    public void givenAddressBookInDB_WhenRetrieved_ShouldMatchContactCount() {
        AddressBookService addressBookService = new AddressBookService();
        List<ContactsData> employeePayrollData = addressBookService.readContactsData(AddressBookService.InputOutputService.DB_IO);
        Assert.assertEquals(2, employeePayrollData.size());
    }

}
