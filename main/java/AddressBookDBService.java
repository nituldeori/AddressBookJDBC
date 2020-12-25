import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {
    private int connectionCounter = 0;
    private PreparedStatement addressbookDataStatement;
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

    public int updateContactData(String firstName, String address) {
        return this.updateContactDataUsingStatement(firstName, address);

    }

    private int updateContactDataUsingStatement(String firstName, String address) {
        String sql = String.format("update contacts set address = '%s' where firstname = '%s';",address, firstName );
        try(Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public List<ContactsData> getContactsDataByName(String firstName) {
        List<ContactsData> contactsDataList = null;
        if(this.addressbookDataStatement == null)
            this.prepareStatementForContactsData();
        try{
            addressbookDataStatement.setString(1,firstName);
            ResultSet resultSet = addressbookDataStatement.executeQuery();
            contactsDataList = this.getContactsData(resultSet);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return contactsDataList;
    }

    private void prepareStatementForContactsData() {
        try{
            Connection connection = this.getConnection();
            String sql = "SELECT * FROM contacts WHERE firstname = ?";
            addressbookDataStatement = connection.prepareStatement(sql);
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    private List<ContactsData> getContactsData(ResultSet resultSet) {
        List<ContactsData> contactsDataList = new ArrayList<>();
        try{
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastName");
                String address = resultSet.getString("address");
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");
                String zip = resultSet.getString("zip");
                String phoneNo = resultSet.getString("phoneno");
                String email = resultSet.getString("email");
                LocalDate start = resultSet.getDate("start").toLocalDate();
                contactsDataList.add(new ContactsData(id, firstName, lastName, address, city, state, zip, phoneNo, email, start));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return contactsDataList;


    }

    public List<ContactsData> getContactsForDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = String.format("SELECT * FROM contacts WHERE START BETWEEN '%s' AND '%s';",
                Date.valueOf(startDate), Date.valueOf(endDate));
        return this.getContactsDataUsingDB(sql);

    }

    private List<ContactsData> getContactsDataUsingDB(String sql) {
        List<ContactsData> contactsDataList = new ArrayList<>();
        try (Connection connection=this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            contactsDataList = this.getContactsData(resultSet);

        } catch (SQLException e){
            e.printStackTrace();
        }
        return contactsDataList;

    }

    public List<ContactsData> getContactsForCity(String city) {
        String sql = String.format("SELECT * FROM contacts WHERE city = '%s';",
                city);
        return this.getContactsDataUsingDB(sql);
    }
}

