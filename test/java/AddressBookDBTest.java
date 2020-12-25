import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

public class AddressBookDBTest {
    @Test
    public void givenAddressBookInDB_WhenRetrieved_ShouldMatchContactCount() {
        AddressBookService addressBookService = new AddressBookService();
        List<ContactsData> employeePayrollData = addressBookService.readContactsData(AddressBookService.InputOutputService.DB_IO);
        Assert.assertEquals(2, employeePayrollData.size());
    }

    @Test
    public void givenNewAddressForContact_WhenUpdated_ShouldSyncWithDB() {
        AddressBookService addressBookService = new AddressBookService();
        List<ContactsData> employeePayrollData = addressBookService.readContactsData(AddressBookService.InputOutputService.DB_IO);
        addressBookService.updateContactAddress("Hayato", "Bermuda");
        boolean result = addressBookService.checkContactListInSymcWithDB("Hayato");
        Assert.assertTrue(result);
    }

    @Test
    public void givenDateRange_WhenRetrieved_ShouldMatchContactsCount() {
        AddressBookService addressBookService = new AddressBookService();
        LocalDate startDate = LocalDate.of(2018, 01, 01);
        LocalDate endDate = LocalDate.now();
        List<ContactsData> employeePayrollData = addressBookService.readContactsforDateRange(AddressBookService.InputOutputService.DB_IO, startDate, endDate);
        Assert.assertEquals(2, employeePayrollData.size());
    }

}
