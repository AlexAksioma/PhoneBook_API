package contact_rest;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import dto.ContactDTO;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static com.jayway.restassured.RestAssured.given;

public class RestEditContactTests {

    String token;
    String id;

    @BeforeClass
    public  void preConditionClass() {
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

    @BeforeMethod
    public  void preCondition(){
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
        int i = new Random().nextInt(1000)+1000;
        ContactDTO contact = ContactDTO.builder()
                .name("Alex"+i)
                .lastName("LastName"+i)
                .email("email"+i+"@mail.com")
                .phone("1231231"+i)
                .address("Haifa")
                .description("its my contact")
                .build();

        String message = given()
                .header("Authorization", token)
                .body(contact)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .extract()
                .path("message")
                ;
        id = message.substring(message.lastIndexOf(" ")+1);
        System.out.println(id);
    }

    @Test
    public void editPositiveTest_byID(){
        int i = new Random().nextInt(1000)+1000;
        ContactDTO contact = ContactDTO.builder()
                .id(id)
                .name("AlexEdit"+i)
                .lastName("LastNameEdit"+i)
                .email("email"+i+"@mail.com")
                .phone("1111231"+i)
                .address("Edit Haifa")
                .description("its my contact")
                .build();
        given()
                .body(contact)
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(200)
        ;
    }
}
