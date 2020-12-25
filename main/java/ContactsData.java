import java.time.LocalDate;
import java.util.Objects;

public class ContactsData {
    public int id;
    public String firstName;
    public String lastName;
    public String address;
    public String city;
    public String state;
    public String zip;
    public String phoneNo;
    public String email;
    public LocalDate start;

    public ContactsData(int id, String firstName, String lastName, String address, String city, String state,
                        String zip, String phoneNo, String email, LocalDate start){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNo = phoneNo;
        this.email = email;
        this.start = start;
    }

    @Override
    public int hashCode(){
        return Objects.hash(id, firstName, lastName, address, city, state, zip, phoneNo,
                            email, start);
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        ContactsData that = (ContactsData) o;
        return id == that.id && firstName.equals(that.firstName) && lastName.equals(that.lastName) && address.equals(that.address)
               && city.equals(that.city) && state.equals(that.state) && zip.equals(that.zip) && phoneNo.equals(that.phoneNo)
               && email.equals(that.email);
    }
}
