package contact_rest;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import dto.ContactDTO;
import dto.GetAllContactsDTO;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;

public class RestGetAllContactsTests {

    String token;
    String id = "";

    @BeforeClass
    public  void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .username("qwerty3171@gmail.com")
                .password("Qwerty123!_")
                .build()
                ;
        AuthResponseDTO responseDTO = given()
                .body(requestDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("/user/login/usernamepassword")
                .then()
                .extract()
                .as(AuthResponseDTO.class)
                ;
        token = responseDTO.getToken();
    }

    @Test
    public void getAllContactsPositiveTest(){
        GetAllContactsDTO  getAllContactsDTO = given()
                .header("Authorization", token)
                .when()
                .get("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .as(GetAllContactsDTO.class)
                ;
        List <ContactDTO> list = getAllContactsDTO.getContacts();
        for (ContactDTO contact: list) {
            System.out.println(contact.getName());
            System.out.println(contact.getLastName());
            System.out.println(contact.getPhone());
            System.out.println("============================");
        }
    }
}
