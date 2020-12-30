import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
        response.then().body("id",Matchers.hasItems(1,2,3));
        response.then().body("firstname", Matchers.hasItem("Mark"));

    }

    @Test
    public void givenContactFirstName_OnUpdate_ShouldReturnUpdatedEmployee(){
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"firstname\": \"Hayato\", \"lastname\": \"Dangal\", \"address\": \"Sadiya\", \"city\": \"Tinsukia\", \"state\": \"Assam\", \"zip\":\"781016\", \"phoneno\":\"6666\", \"email\":\"hayato@gmail.com\"}")
                .when()
                .put("/contacts/2");
        String respAsStr = response.asString();
        response.then().body("id", Matchers.any(Integer.class));
        response.then().body("firstname", Matchers.is("Hayato"));
        response.then().body("address", Matchers.is("Sadiya"));
    }






}
