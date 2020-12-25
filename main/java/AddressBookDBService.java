import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {
    private int connectionCounter = 0;
    private PreparedStatement employeePayrollDataStatement;
    private static AddressBookDBService addressBookDBService;
    private AddressBookDBService(){

    }

    public static AddressBookDBService getInstance(){
        if(addressBookDBService == null)
            addressBookDBService = new AddressBookDBService();
        return addressBookDBService;
    }

    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/address_book?useSSL=false";
        String userName = "root";
        String password = "Myheart@18/6";
        Connection con;
        System.out.println("Processing Thread: "+Thread.currentThread().getName()+
                " Connecting to database with id:"+connectionCounter);
        con = DriverManager.getConnection(jdbcURL, userName, password);
        System.out.println("Processing Thread: "+Thread.currentThread().getName()+
                " Id: "+connectionCounter+ " Connection is successful!!!"+con);
        return con;

    }


    public List<ContactsData> readData() {
        String sql = "SELECT * FROM contacts";
        List<ContactsData> contactsDataList =new ArrayList<>();
        try {
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()){
                int id = result.getInt("id");
                String firstName = result.getString("firstname");
                String lastName = result.getString("lastName");
                String address = result.getString("address");
                String city = result.getString("city");
                String state = result.getString("state");
                String zip = result.getString("zip");
                String phoneNo = result.getString("phoneno");
                String email = result.getString("email");
                LocalDate start = result.getDate("start").toLocalDate();
                contactsDataList.add(new ContactsData(id, firstName, lastName, address, city, state, zip, phoneNo, email, start));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactsDataList;
    }
}
