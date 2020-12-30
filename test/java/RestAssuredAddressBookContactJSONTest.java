import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class RestAssuredAddressBookContactJSONTest {
    @Before
    public void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;

    }

    public Response getContactsList(){
        Response response = RestAssured.get("/contacts");
        return response;
    }

    @Test
    public void onCallingList_ReturnContactsList(){
        Response response = getContactsList();
        System.out.println("AT FIRST: " + response.asString());
        response.then().body("firstname", Matchers.hasItem("Mark"));

    }




}
